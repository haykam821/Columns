package io.github.haykam821.columns.data.provider;

import java.util.concurrent.CompletableFuture;

import io.github.haykam821.columns.Main;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

public class ColumnsItemTagProvider extends FabricTagProvider.ItemTagProvider {
	public ColumnsItemTagProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registries, FabricTagProvider.BlockTagProvider blockTags) {
		super(dataOutput, registries, blockTags);
	}

	@Override
	protected void configure(WrapperLookup lookup) {
		this.copy(Main.COLUMNS_BLOCK_TAG, Main.COLUMNS_ITEM_TAG);
	}
}
