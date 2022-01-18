package io.github.haykam821.columns.data;

import io.github.haykam821.columns.data.provider.ColumnsBlockLootTableProvider;
import io.github.haykam821.columns.data.provider.ColumnsBlockTagProvider;
import io.github.haykam821.columns.data.provider.ColumnsItemTagProvider;
import io.github.haykam821.columns.data.provider.ColumnsModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

public class ColumnsDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
		dataGenerator.addProvider(ColumnsBlockLootTableProvider::new);

		FabricTagProvider.BlockTagProvider blockTags = new ColumnsBlockTagProvider(dataGenerator);
		dataGenerator.addProvider(blockTags);

		dataGenerator.addProvider(new ColumnsItemTagProvider(dataGenerator, blockTags));

		dataGenerator.addProvider(ColumnsModelProvider::new);
	}
}
