package io.github.haykam821.columns.block;

import io.github.haykam821.columns.Main;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public enum ColumnTypes {
	TUFF("tuff", Blocks.TUFF, Items.TUFF_WALL),
	POLISHED_TUFF("polished_tuff", Blocks.POLISHED_TUFF, Items.POLISHED_TUFF_WALL),
	TUFF_BRICK("tuff_brick", Blocks.TUFF_BRICKS, Items.TUFF_BRICK_WALL),
	RESIN_BRICK("resin_brick", Blocks.RESIN_BRICKS, Items.RESIN_BRICK_WALL),
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
		Identifier id = Main.id(type + "_column");
		this.base = base;
		this.wall = wall;

		BlockBehaviour.Properties blockSettings = BlockBehaviour.Properties.ofFullCopy(base)
			.setId(ResourceKey.create(Registries.BLOCK, id));

		this.block = new ColumnBlock(blockSettings);

		Item.Properties itemSettings = new Item.Properties()
			.setId(ResourceKey.create(Registries.ITEM, id))
			.useBlockDescriptionPrefix();

		this.item = new BlockItem(this.block, itemSettings);

		Registry.register(BuiltInRegistries.BLOCK, id, this.block);
		Registry.register(BuiltInRegistries.ITEM, id, this.item);
	}

	public static void register() {
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries -> {
			for (ColumnTypes type : ColumnTypes.values()) {
				entries.insertBefore(type.wall, type.item);
			}
		});
	}
}