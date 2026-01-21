package io.github.haykam821.columns.data.provider;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import io.github.haykam821.columns.block.ColumnTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements.Strategy;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ColumnsRecipeGenerator extends RecipeProvider {
	protected ColumnsRecipeGenerator(HolderLookup.Provider registries, RecipeOutput exporter) {
		super(registries, exporter);
	}

	@Override
	public void buildRecipes() {
		for (ColumnTypes columnType : ColumnTypes.values()) {
			this.offerColumnRecipe(columnType.block, columnType.base);
			this.offerColumnStonecuttingRecipe(columnType.block, columnType.base);
		}

		this.offerCustomColumnStonecuttingRecipe(ColumnTypes.DEEPSLATE_BRICK.block, Blocks.COBBLED_DEEPSLATE);
		this.offerCustomColumnStonecuttingRecipe(ColumnTypes.DEEPSLATE_BRICK.block, Blocks.POLISHED_DEEPSLATE);

		this.offerCustomColumnStonecuttingRecipe(ColumnTypes.DEEPSLATE_TILE.block, Blocks.COBBLED_DEEPSLATE);
		this.offerCustomColumnStonecuttingRecipe(ColumnTypes.DEEPSLATE_TILE.block, Blocks.DEEPSLATE_BRICKS);
		this.offerCustomColumnStonecuttingRecipe(ColumnTypes.DEEPSLATE_TILE.block, Blocks.POLISHED_DEEPSLATE);

		this.offerCustomColumnStonecuttingRecipe(ColumnTypes.END_STONE_BRICK.block, Blocks.END_STONE);

		this.offerCustomColumnStonecuttingRecipe(ColumnTypes.POLISHED_BLACKSTONE_BRICK.block, Blocks.BLACKSTONE);
		this.offerCustomColumnStonecuttingRecipe(ColumnTypes.POLISHED_BLACKSTONE_BRICK.block, Blocks.POLISHED_BLACKSTONE);

		this.offerCustomColumnStonecuttingRecipe(ColumnTypes.POLISHED_BLACKSTONE.block, Blocks.BLACKSTONE);

		this.offerCustomColumnStonecuttingRecipe(ColumnTypes.POLISHED_DEEPSLATE.block, Blocks.COBBLED_DEEPSLATE);

		this.offerCustomColumnStonecuttingRecipe(ColumnTypes.POLISHED_TUFF.block, Blocks.TUFF);

		this.offerCustomColumnStonecuttingRecipe(ColumnTypes.STONE_BRICK.block, Blocks.STONE);

		this.offerCustomColumnStonecuttingRecipe(ColumnTypes.TUFF_BRICK.block, Blocks.TUFF);
		this.offerCustomColumnStonecuttingRecipe(ColumnTypes.TUFF_BRICK.block, Blocks.POLISHED_TUFF);
	}

	public void offerColumnRecipe(Block block, Block base) {
		this.offerCraftingTo(this.getColumnRecipe(block, Ingredient.of(base))
			.unlockedBy(RecipeProvider.getHasName(base), this.has(base)));
	}

	public ShapedRecipeBuilder getColumnRecipe(ItemLike output, Ingredient input) {
		return this.shaped(RecipeCategory.DECORATIONS, output, 6)
			.define('#', input)
			.pattern("###")
			.pattern(" # ")
			.pattern("###");
	}

	public void offerColumnStonecuttingRecipe(Block block, Block base) {
		Identifier blockId = BuiltInRegistries.ITEM.getKey(block.asItem());

		Identifier recipeId = blockId.withSuffix("_from_stonecutting");
		ResourceKey<Recipe<?>> recipeKey = ResourceKey.create(Registries.RECIPE, recipeId);

		this.offerCustomColumnStonecuttingRecipe(recipeKey, block, base);
	}

	public void offerCustomColumnStonecuttingRecipe(Block block, Block base) {
		Identifier baseId = BuiltInRegistries.ITEM.getKey(base.asItem());
		Identifier blockId = BuiltInRegistries.ITEM.getKey(block.asItem());

		Identifier recipeId = blockId.withPath(path -> path + "_from_" + baseId.getPath() + "_stonecutting");
		ResourceKey<Recipe<?>> recipeKey = ResourceKey.create(Registries.RECIPE, recipeId);

		this.offerCustomColumnStonecuttingRecipe(recipeKey, block, base);
	}

	private void offerCustomColumnStonecuttingRecipe(ResourceKey<Recipe<?>> recipeKey, Block block, Block base) {
		this.offerSingleItemTo(recipeKey, SingleItemRecipeBuilder.stonecutting(Ingredient.of(base), RecipeCategory.DECORATIONS, block, 1)
			.unlockedBy(RecipeProvider.getHasName(base), this.has(base)));
	}

	private void offerCraftingTo(ShapedRecipeBuilder factory) {
		ResourceKey<Recipe<?>> recipeKey = RecipeBuilder.getDefaultRecipeId(factory.result);

		this.offerShapedTo(recipeKey, factory);
	}

	private void offerShapedTo(ResourceKey<Recipe<?>> recipeKey, ShapedRecipeBuilder factory) {
		ShapedRecipePattern rawRecipe = factory.ensureValid(recipeKey);

		Identifier advancementId = ColumnsRecipeGenerator.getAdvancementId(recipeKey);
		Advancement.Builder advancementBuilder = this.output.advancement()
			.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeKey))
			.rewards(AdvancementRewards.Builder.recipe(recipeKey))
			.requirements(Strategy.OR);

		factory.criteria.forEach(advancementBuilder::addCriterion);

		AdvancementHolder advancement = advancementBuilder.build(advancementId);

		String group = Objects.requireNonNullElse(factory.group, "");
		CraftingBookCategory category = RecipeBuilder.determineBookCategory(factory.category);

		ShapedRecipe recipe = new ShapedRecipe(group, category, rawRecipe, factory.result, factory.showNotification);
		this.output.accept(recipeKey, recipe, advancement);
	}

	private void offerSingleItemTo(ResourceKey<Recipe<?>> recipeKey, SingleItemRecipeBuilder factory) {
		factory.ensureValid(recipeKey);

		Identifier advancementId = ColumnsRecipeGenerator.getAdvancementId(recipeKey);
		Advancement.Builder advancementBuilder = this.output.advancement()
			.addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeKey))
			.rewards(AdvancementRewards.Builder.recipe(recipeKey))
			.requirements(Strategy.OR);

		factory.criteria.forEach(advancementBuilder::addCriterion);

		AdvancementHolder advancement = advancementBuilder.build(advancementId);

		String group = Objects.requireNonNullElse(factory.group, "");

		SingleItemRecipe recipe = factory.factory.create(group, factory.ingredient, factory.result);
		this.output.accept(recipeKey, recipe, advancement);
	}

	private static Identifier getAdvancementId(ResourceKey<Recipe<?>> recipeKey) {
		return recipeKey.identifier().withPrefix("recipes/");
	}

	public static class Provider extends FabricRecipeProvider {
		public Provider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registries) {
			super(dataOutput, registries);
		}

		@Override
		protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput exporter) {
			return new ColumnsRecipeGenerator(registries, exporter);
		}

		@Override
		public String getName() {
			return "Recipes";
		}
	}
}
