package io.github.haykam821.columns.data.provider;

import java.util.function.Consumer;

import io.github.haykam821.columns.block.ColumnTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipesProvider;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.RecipesProvider;
import net.minecraft.data.server.recipe.CraftingRecipeJsonFactory;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.data.server.recipe.SingleItemRecipeJsonFactory;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ColumnsRecipeProvider extends FabricRecipesProvider {
	public ColumnsRecipeProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}

	@Override
	protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
		for (ColumnTypes columnType : ColumnTypes.values()) {
			ColumnsRecipeProvider.offerColumnRecipe(exporter, columnType.block, columnType.base);
			ColumnsRecipeProvider.offerColumnStonecuttingRecipe(exporter, columnType.block, columnType.base);
		}

		ColumnsRecipeProvider.offerCustomColumnStonecuttingRecipe(exporter, ColumnTypes.DEEPSLATE_BRICK.block, Blocks.COBBLED_DEEPSLATE);
		ColumnsRecipeProvider.offerCustomColumnStonecuttingRecipe(exporter, ColumnTypes.DEEPSLATE_BRICK.block, Blocks.POLISHED_DEEPSLATE);

		ColumnsRecipeProvider.offerCustomColumnStonecuttingRecipe(exporter, ColumnTypes.DEEPSLATE_TILE.block, Blocks.COBBLED_DEEPSLATE);
		ColumnsRecipeProvider.offerCustomColumnStonecuttingRecipe(exporter, ColumnTypes.DEEPSLATE_TILE.block, Blocks.DEEPSLATE_BRICKS);
		ColumnsRecipeProvider.offerCustomColumnStonecuttingRecipe(exporter, ColumnTypes.DEEPSLATE_TILE.block, Blocks.POLISHED_DEEPSLATE);

		ColumnsRecipeProvider.offerCustomColumnStonecuttingRecipe(exporter, ColumnTypes.POLISHED_BLACKSTONE_BRICK.block, Blocks.BLACKSTONE);
		ColumnsRecipeProvider.offerCustomColumnStonecuttingRecipe(exporter, ColumnTypes.POLISHED_BLACKSTONE_BRICK.block, Blocks.POLISHED_BLACKSTONE);

		ColumnsRecipeProvider.offerCustomColumnStonecuttingRecipe(exporter, ColumnTypes.POLISHED_BLACKSTONE.block, Blocks.BLACKSTONE);

		ColumnsRecipeProvider.offerCustomColumnStonecuttingRecipe(exporter, ColumnTypes.POLISHED_DEEPSLATE.block, Blocks.COBBLED_DEEPSLATE);

		ColumnsRecipeProvider.offerCustomColumnStonecuttingRecipe(exporter, ColumnTypes.STONE_BRICK.block, Blocks.STONE);
	}

	public static void offerColumnRecipe(Consumer<RecipeJsonProvider> exporter, Block block, Block base) {
		ColumnsRecipeProvider.offerCraftingTo(exporter, ColumnsRecipeProvider.getColumnRecipe(block, Ingredient.ofItems(base))
			.criterion(RecipesProvider.hasItem(base), RecipesProvider.conditionsFromItem(base)));
	}

	public static ShapedRecipeJsonFactory getColumnRecipe(ItemConvertible output, Ingredient input) {
		return ShapedRecipeJsonFactory.create(output, 6)
			.input('#', input)
			.pattern("###")
			.pattern(" # ")
			.pattern("###");
	}

	public static void offerColumnStonecuttingRecipe(Consumer<RecipeJsonProvider> exporter, Block block, Block base) {
		Identifier blockId = Registry.ITEM.getId(block.asItem());
		Identifier recipeId = new Identifier(blockId.getNamespace(), blockId.getPath() + "_from_stonecutting");

		ColumnsRecipeProvider.offerCustomColumnStonecuttingRecipe(exporter, recipeId, block, base);
	}

	public static void offerCustomColumnStonecuttingRecipe(Consumer<RecipeJsonProvider> exporter, Block block, Block base) {
		Identifier baseId = Registry.ITEM.getId(base.asItem());
		Identifier blockId = Registry.ITEM.getId(block.asItem());

		Identifier recipeId = new Identifier(blockId.getNamespace(), blockId.getPath() + "_from_" + baseId.getPath() + "_stonecutting");
		ColumnsRecipeProvider.offerCustomColumnStonecuttingRecipe(exporter, recipeId, block, base);
	}

	private static void offerCustomColumnStonecuttingRecipe(Consumer<RecipeJsonProvider> exporter, Identifier recipeId, Block block, Block base) {
		ColumnsRecipeProvider.offerSingleItemTo(exporter, recipeId, SingleItemRecipeJsonFactory.createStonecutting(Ingredient.ofItems(base), block, 1)
			.criterion(RecipesProvider.hasItem(base), RecipesProvider.conditionsFromItem(base)));
	}

	private static void offerCraftingTo(Consumer<RecipeJsonProvider> exporter, ShapedRecipeJsonFactory factory) {
		Identifier recipeId = CraftingRecipeJsonFactory.getItemId(factory.getOutputItem());
		ColumnsRecipeProvider.offerShapedTo(exporter, recipeId, factory);
	}

	private static void offerShapedTo(Consumer<RecipeJsonProvider> exporter, Identifier recipeId, ShapedRecipeJsonFactory factory) {
		factory.validate(recipeId);

		Identifier advancementId = ColumnsRecipeProvider.getAdvancementId(recipeId);
		factory.builder
			.parent(new Identifier("recipes/root"))
			.criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
			.rewards(AdvancementRewards.Builder.recipe(recipeId))
			.criteriaMerger(CriterionMerger.OR);

		String group = factory.group == null ? "" : factory.group;
		exporter.accept(new ShapedRecipeJsonFactory.ShapedRecipeJsonProvider(recipeId, factory.getOutputItem(), factory.outputCount, group, factory.pattern, factory.inputs, factory.builder, advancementId));
	}

	private static void offerSingleItemTo(Consumer<RecipeJsonProvider> exporter, Identifier recipeId, SingleItemRecipeJsonFactory factory) {
		factory.validate(recipeId);

		Identifier advancementId = ColumnsRecipeProvider.getAdvancementId(recipeId);
		factory.builder
			.parent(new Identifier("recipes/root"))
			.criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
			.rewards(AdvancementRewards.Builder.recipe(recipeId))
			.criteriaMerger(CriterionMerger.OR);

		String group = factory.group == null ? "" : factory.group;
		exporter.accept(new SingleItemRecipeJsonFactory.SingleItemRecipeJsonProvider(recipeId, factory.serializer, group, factory.input, factory.getOutputItem(), factory.count, factory.builder, advancementId));
	}

	private static Identifier getAdvancementId(Identifier recipeId) {
		return new Identifier(recipeId.getNamespace(), "recipes/" + recipeId.getPath());
	}
}
