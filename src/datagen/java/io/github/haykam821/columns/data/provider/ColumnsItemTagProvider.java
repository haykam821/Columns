package io.github.haykam821.columns.data.provider;

import java.util.concurrent.CompletableFuture;

import io.github.haykam821.columns.Main;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;

public class ColumnsItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
	public ColumnsItemTagProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registries, FabricTagsProvider.BlockTagsProvider blockTags) {
		super(dataOutput, registries, blockTags);
	}

	@Override
	protected void addTags(Provider lookup) {
		this.copy(Main.COLUMNS_BLOCK_TAG, Main.COLUMNS_ITEM_TAG);
	}
}
