package io.github.haykam821.columns;

import io.github.haykam821.columns.block.ColumnTypes;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class Main implements ModInitializer {
	public static final String MOD_ID = "columns";

	private static final Identifier COLUMNS_ID = new Identifier(MOD_ID, "columns");

	public static final TagKey<Block> COLUMNS_BLOCK_TAG = TagKey.of(RegistryKeys.BLOCK, COLUMNS_ID);
	public static final TagKey<Item> COLUMNS_ITEM_TAG = TagKey.of(RegistryKeys.ITEM, COLUMNS_ID);

	@Override
	public void onInitialize() {
		ColumnTypes.register();
	}
}