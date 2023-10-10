package io.github.haykam821.columns.data.provider;

import java.util.concurrent.CompletableFuture;

import io.github.haykam821.columns.Main;
import io.github.haykam821.columns.block.ColumnTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;

public class ColumnsBlockTagProvider extends FabricTagProvider.BlockTagProvider {
	public ColumnsBlockTagProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
		super(dataOutput, registries);
	}

	@Override
	protected void configure(WrapperLookup lookup) {
		FabricTagBuilder builder = this.getOrCreateTagBuilder(Main.COLUMNS_BLOCK_TAG);
		for (ColumnTypes columnType : ColumnTypes.values()) {
			builder.add(columnType.block);
		}

		this.getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).addTag(Main.COLUMNS_BLOCK_TAG);
	}
}
