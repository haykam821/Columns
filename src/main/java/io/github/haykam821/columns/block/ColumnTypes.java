package io.github.haykam821.columns.block;

import io.github.haykam821.columns.Main;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public enum ColumnTypes {
	COBBLESTONE("cobblestone", Blocks.COBBLESTONE, Items.COBBLESTONE_WALL),
	MOSSY_COBBLESTONE("mossy_cobblestone", Blocks.MOSSY_COBBLESTONE, Items.MOSSY_COBBLESTONE_WALL),
	BRICK("brick", Blocks.BRICKS, Items.BRICK_WALL),
	PRISMARINE("prismarine", Blocks.PRISMARINE, Items.PRISMARINE_WALL),
	RED_SANDSTONE("red_sandstone", Blocks.RED_SANDSTONE, Items.RED_SANDSTONE_WALL),
	MOSSY_STONE_BRICK("mossy_stone_brick", Blocks.MOSSY_STONE_BRICKS, Items.MOSSY_STONE_BRICK_WALL),
	GRANITE("granite", Blocks.GRANITE, Items.GRANITE_WALL),
	STONE_BRICK("stone_brick", Blocks.STONE_BRICKS, Items.STONE_BRICK_WALL),
	MUD_BRICK("mud_brick", Blocks.MUD_BRICKS, Items.MUD_BRICK_WALL),
	NETHER_BRICK("nether_brick", Blocks.NETHER_BRICKS, Items.NETHER_BRICK_WALL),
	ANDESITE("andesite", Blocks.ANDESITE, Items.ANDESITE_WALL),
	RED_NETHER_BRICK("red_nether_brick", Blocks.RED_NETHER_BRICKS, Items.RED_NETHER_BRICK_WALL),
	SANDSTONE("sandstone", Blocks.SANDSTONE, Items.SANDSTONE_WALL),
	END_STONE_BRICK("end_stone_brick", Blocks.END_STONE_BRICKS, Items.END_STONE_BRICK_WALL),
	DIORITE("diorite", Blocks.DIORITE, Items.DIORITE_WALL),
	BLACKSTONE("blackstone", Blocks.BLACKSTONE, Items.BLACKSTONE_WALL),
	POLISHED_BLACKSTONE("polished_blackstone", Blocks.POLISHED_BLACKSTONE, Items.POLISHED_BLACKSTONE_WALL),
	POLISHED_BLACKSTONE_BRICK("polished_blackstone_brick", Blocks.POLISHED_BLACKSTONE_BRICKS, Items.POLISHED_BLACKSTONE_BRICK_WALL),
	COBBLED_DEEPSLATE("cobbled_deepslate", Blocks.COBBLED_DEEPSLATE, Items.COBBLED_DEEPSLATE_WALL),
	POLISHED_DEEPSLATE("polished_deepslate", Blocks.POLISHED_DEEPSLATE, Items.POLISHED_DEEPSLATE_WALL),
	DEEPSLATE_BRICK("deepslate_brick", Blocks.DEEPSLATE_BRICKS, Items.DEEPSLATE_BRICK_WALL),
	DEEPSLATE_TILE("deepslate_tile", Blocks.DEEPSLATE_TILES, Items.DEEPSLATE_TILE_WALL);

	public final ColumnBlock block;
	public final BlockItem item;
	public final Block base;
	public final Item wall;

	private ColumnTypes(String type, Block base, Item wall) {
		Identifier id = new Identifier(Main.MOD_ID, type + "_column");
		this.base = base;
		this.wall = wall;

		Block.Settings blockSettings = FabricBlockSettings.copy(base);
		this.block = new ColumnBlock(blockSettings);

		Item.Settings itemSettings = new Item.Settings();
		this.item = new BlockItem(this.block, itemSettings);

		Registry.register(Registries.BLOCK, id, this.block);
		Registry.register(Registries.ITEM, id, this.item);
	}

	public static void register() {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
			for (ColumnTypes type : ColumnTypes.values()) {
				entries.addBefore(type.wall, type.item);
			}
		});
	}
}