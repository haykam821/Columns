package io.github.haykam821.columns.data;

import io.github.haykam821.columns.data.provider.ColumnsBlockLootTableProvider;
import io.github.haykam821.columns.data.provider.ColumnsBlockTagProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ColumnsDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
		dataGenerator.addProvider(ColumnsBlockLootTableProvider::new);
		dataGenerator.addProvider(ColumnsBlockTagProvider::new);
	}
}
