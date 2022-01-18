package io.github.haykam821.columns;

import io.github.haykam821.columns.block.ColumnTypes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class Main implements ModInitializer {
	public static final String MOD_ID = "columns";

	private static final Identifier COLUMNS_ID = new Identifier(MOD_ID, "columns");

	public static final Tag.Identified<Block> COLUMNS_BLOCK_TAG = TagFactory.BLOCK.create(COLUMNS_ID);
	public static final Tag.Identified<Item> COLUMNS_ITEM_TAG = TagFactory.ITEM.create(COLUMNS_ID);

	@Override
	public void onInitialize() {
		ColumnTypes.initialize();
	}
}