package io.github.haykam821.columns.data.provider;

import io.github.haykam821.columns.block.ColumnTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class ColumnsBlockLootTableProvider extends FabricBlockLootTableProvider {
	public ColumnsBlockLootTableProvider(FabricDataOutput dataOutput) {
		super(dataOutput);
	}

	@Override
	public void generate() {
		for (ColumnTypes columnType : ColumnTypes.values()) {
			this.addDrop(columnType.block);
		}
	}
}
