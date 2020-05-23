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
	PRISMARINE("prismarine", Blocks.PRISMARINE);

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