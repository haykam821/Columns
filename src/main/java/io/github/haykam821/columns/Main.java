package io.github.haykam821.columns;

import io.github.haykam821.columns.block.ColumnBlock;
import io.github.haykam821.columns.block.ColumnTypes;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class Main implements ModInitializer {
	private static final String MOD_ID = "columns";

	private static final Identifier COLUMNS_ID = Main.id("columns");

	public static final TagKey<Block> COLUMNS_BLOCK_TAG = TagKey.create(Registries.BLOCK, COLUMNS_ID);
	public static final TagKey<Item> COLUMNS_ITEM_TAG = TagKey.create(Registries.ITEM, COLUMNS_ID);

	private static final Identifier COLUMN_ID = Main.id("column");

	@Override
	public void onInitialize() {
		Registry.register(BuiltInRegistries.BLOCK_TYPE, COLUMN_ID, ColumnBlock.CODEC);

		ColumnTypes.register();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}