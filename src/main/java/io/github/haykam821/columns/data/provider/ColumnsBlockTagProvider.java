package io.github.haykam821.columns.data.provider;

import io.github.haykam821.columns.Main;
import io.github.haykam821.columns.block.ColumnTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.tag.BlockTags;

public class ColumnsBlockTagProvider extends FabricTagProvider.BlockTagProvider {
	public ColumnsBlockTagProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}

	@Override
	protected void generateTags() {
		FabricTagBuilder<Block> builder = this.getOrCreateTagBuilder(Main.COLUMNS_BLOCK_TAG);
		for (ColumnTypes columnType : ColumnTypes.values()) {
			builder.add(columnType.block);
		}

		this.getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).addTag(Main.COLUMNS_BLOCK_TAG);
	}
}
