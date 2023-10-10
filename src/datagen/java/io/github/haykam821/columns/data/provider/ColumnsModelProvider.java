package io.github.haykam821.columns.data.provider;

import java.util.Optional;

import io.github.haykam821.columns.Main;
import io.github.haykam821.columns.block.ColumnBlock;
import io.github.haykam821.columns.block.ColumnTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.BlockStateVariant;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.MultipartBlockStateSupplier;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.client.VariantSettings;
import net.minecraft.data.client.When;
import net.minecraft.util.Identifier;

public class ColumnsModelProvider extends FabricModelProvider {
	public static final Model COLUMN_CENTER = createModel("column_center", "_center", TextureKey.ALL);
	public static final Model COLUMN_END = createModel("column_end", "_end", TextureKey.ALL);
	public static final Model COLUMN_INVENTORY = createModel("column_inventory", "_inventory", TextureKey.ALL);

	public ColumnsModelProvider(FabricDataOutput dataOutput) {
		super(dataOutput);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator modelGenerator) {
		for (ColumnTypes columnType : ColumnTypes.values()) {
			ColumnsModelProvider.registerColumn(modelGenerator, columnType.block, columnType.base);
		}
	}

	@Override
	public void generateItemModels(ItemModelGenerator modelGenerator) {
		return;
	}

	public static void registerColumn(BlockStateModelGenerator modelGenerator, Block block, Block base) {
		TextureMap textures = getTextures(base);

		Identifier centerId = COLUMN_CENTER.upload(block, textures, modelGenerator.modelCollector);
		Identifier endId = COLUMN_END.upload(block, textures, modelGenerator.modelCollector);

		modelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier.create(block)
			.with(createVariant(centerId))
			.with(When.create().set(ColumnBlock.DOWN, true), createVariant(endId))
			.with(When.create().set(ColumnBlock.UP, true), createVariantRotated(endId)));

		Identifier inventoryId = COLUMN_INVENTORY.upload(block, textures, modelGenerator.modelCollector);
		modelGenerator.registerParentedItemModel(block, inventoryId);
	}

	private static TextureMap getTextures(Block base) {
		if (base == Blocks.SANDSTONE || base == Blocks.RED_SANDSTONE) {
			return TextureMap.all(TextureMap.getSubId(base, "_top"));
		}
		return TextureMap.all(base);
	}

	private static BlockStateVariant createVariant(Identifier modelId) {
		return BlockStateVariant.create().put(VariantSettings.MODEL, modelId);
	}

	private static BlockStateVariant createVariantRotated(Identifier modelId) {
		return createVariant(modelId)
			.put(VariantSettings.X, VariantSettings.Rotation.R180)
			.put(VariantSettings.UVLOCK, true);
	}

	private static Model createModel(String parent, String variant, TextureKey... requiredTextures) {
		return new Model(Optional.of(new Identifier(Main.MOD_ID, "block/" + parent)), Optional.of(variant), requiredTextures);
	}
}
