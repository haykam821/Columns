package io.github.haykam821.columns.data.provider;

import java.util.concurrent.CompletableFuture;

import io.github.haykam821.columns.Main;
import io.github.haykam821.columns.block.ColumnTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

public class ColumnsBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
	public ColumnsBlockTagProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registries) {
		super(dataOutput, registries);
	}

	@Override
	protected void addTags(Provider lookup) {
		TagAppender<Block, Block> builder = this.valueLookupBuilder(Main.COLUMNS_BLOCK_TAG);
		for (ColumnTypes columnType : ColumnTypes.values()) {
			builder.add(columnType.block);
		}

		this.valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE).addTag(Main.COLUMNS_BLOCK_TAG);
	}
}
