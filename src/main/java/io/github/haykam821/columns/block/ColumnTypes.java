package io.github.haykam821.columns.block;

import io.github.haykam821.columns.Main;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public enum ColumnTypes {
	COBBLESTONE("cobblestone", Blocks.COBBLESTONE),
	MOSSY_COBBLESTONE("mossy_cobblestone", Blocks.MOSSY_COBBLESTONE),
	BRICK("brick", Blocks.BRICKS),
	PRISMARINE("prismarine", Blocks.PRISMARINE),
	RED_SANDSTONE("red_sandstone", Blocks.RED_SANDSTONE),
	MOSSY_STONE_BRICK("mossy_stone_brick", Blocks.MOSSY_STONE_BRICKS),
	GRANITE("granite", Blocks.GRANITE),
	STONE_BRICK("stone_brick", Blocks.STONE_BRICKS),
	NETHER_BRICK("nether_brick", Blocks.NETHER_BRICKS),
	ANDESITE("andesite", Blocks.ANDESITE),
	RED_NETHER_BRICK("red_nether_brick", Blocks.RED_NETHER_BRICKS),
	SANDSTONE("sandstone", Blocks.SANDSTONE),
	END_STONE_BRICK("end_stone_brick", Blocks.END_STONE_BRICKS),
	DIORITE("diorite", Blocks.DIORITE);

	public final ColumnBlock block;
	public final BlockItem item;

	private ColumnTypes(String type, Block base) {
		Identifier id = new Identifier(Main.MOD_ID, type + "_column");

		Block.Settings blockSettings = FabricBlockSettings.copy(base);
		this.block = new ColumnBlock(blockSettings);

		Item.Settings itemSettings = new Item.Settings().group(ItemGroup.DECORATIONS);
		this.item = new BlockItem(this.block, itemSettings);

		Registry.register(Registry.BLOCK, id, this.block);
		Registry.register(Registry.ITEM, id, this.item);
	}

	public static ColumnTypes initialize() {
		return null;
	}
}