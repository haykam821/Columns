package io.github.haykam821.columns.data.provider;

import java.util.Optional;

import io.github.haykam821.columns.Main;
import io.github.haykam821.columns.block.ColumnBlock;
import io.github.haykam821.columns.block.ColumnTypes;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.ConditionBuilder;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ColumnsModelProvider extends FabricModelProvider {
	public static final ModelTemplate COLUMN_CENTER = createModel("column_center", "_center", TextureSlot.ALL);
	public static final ModelTemplate COLUMN_END = createModel("column_end", "_end", TextureSlot.ALL);
	public static final ModelTemplate COLUMN_INVENTORY = createModel("column_inventory", "_inventory", TextureSlot.ALL);

	public ColumnsModelProvider(FabricPackOutput dataOutput) {
		super(dataOutput);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators modelGenerator) {
		for (ColumnTypes columnType : ColumnTypes.values()) {
			ColumnsModelProvider.registerColumn(modelGenerator, columnType.block, columnType.base);
		}
	}

	@Override
	public void generateItemModels(ItemModelGenerators modelGenerator) {
		return;
	}

	public static void registerColumn(BlockModelGenerators modelGenerator, Block block, Block base) {
		TextureMapping textures = getTextures(base);

		Identifier centerId = COLUMN_CENTER.create(block, textures, modelGenerator.modelOutput);
		Identifier endId = COLUMN_END.create(block, textures, modelGenerator.modelOutput);

		modelGenerator.blockStateOutput.accept(MultiPartGenerator.multiPart(block)
			.with(createVariant(centerId))
			.with(new ConditionBuilder().term(ColumnBlock.DOWN, true), createVariant(endId))
			.with(new ConditionBuilder().term(ColumnBlock.UP, true), createVariantRotated(endId)));

		Identifier inventoryId = COLUMN_INVENTORY.create(block, textures, modelGenerator.modelOutput);
		modelGenerator.registerSimpleItemModel(block, inventoryId);
	}

	private static TextureMapping getTextures(Block base) {
		if (base == Blocks.SANDSTONE || base == Blocks.RED_SANDSTONE) {
			return TextureMapping.cube(TextureMapping.getBlockTexture(base, "_top"));
		}
		return TextureMapping.cube(base);
	}

	private static MultiVariant createVariant(Identifier modelId) {
		return new MultiVariant(WeightedList.of(new Variant(modelId)));
	}

	private static MultiVariant createVariantRotated(Identifier modelId) {
		return createVariant(modelId)
			.with(BlockModelGenerators.X_ROT_180)
			.with(BlockModelGenerators.UV_LOCK);
	}

	private static ModelTemplate createModel(String parent, String variant, TextureSlot... requiredTextures) {
		return new ModelTemplate(Optional.of(Main.id("block/" + parent)), Optional.of(variant), requiredTextures);
	}
}
