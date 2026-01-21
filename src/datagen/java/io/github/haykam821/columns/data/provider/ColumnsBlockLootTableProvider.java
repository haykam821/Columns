package io.github.haykam821.columns.data.provider;

import java.util.concurrent.CompletableFuture;

import io.github.haykam821.columns.block.ColumnTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;

public class ColumnsBlockLootTableProvider extends FabricBlockLootSubProvider {
	public ColumnsBlockLootTableProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registries) {
		super(dataOutput, registries);
	}

	@Override
	public void generate() {
		for (ColumnTypes columnType : ColumnTypes.values()) {
			this.dropSelf(columnType.block);
		}

		this.map.forEach((id, lootTable) -> {
			lootTable.setRandomSequence(id.identifier());
		});
	}
}
