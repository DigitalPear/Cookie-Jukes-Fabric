package org.digitalpear.cookiejukes.jei;

import mezz.jei.api.gui.ingredient.IRecipeSlotDrawable;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import org.digitalpear.cookiejukes.Cookiejukes;
import org.digitalpear.cookiejukes.common.crafting.DiscDuplicationRecipe;

import java.util.List;
import java.util.Optional;

public class DiscDuplicationCraftingExtension implements ICraftingCategoryExtension<DiscDuplicationRecipe> {


    @Override
    public List<SlotDisplay> getIngredients(RecipeHolder<DiscDuplicationRecipe> recipeHolder) {
        return DiscDuplicationRecipe.getRecipes();
    }

    @Override
    public void onDisplayedIngredientsUpdate(RecipeHolder<DiscDuplicationRecipe> recipeHolder, List<IRecipeSlotDrawable> recipeSlots, IFocusGroup focuses) {
        Optional<ItemStack> outputItemStack = recipeSlots.get(9).getDisplayedIngredient().get().getItemStack();
        Optional<ItemStack> dicsItemStack = recipeSlots.get(4).getDisplayedIngredient().get().getItemStack();
        if (outputItemStack.isPresent() && dicsItemStack.isPresent()) {
            ItemStack outputStack = outputItemStack.get();
            outputStack.set(DataComponents.JUKEBOX_PLAYABLE, dicsItemStack.get().get(DataComponents.JUKEBOX_PLAYABLE));
            Cookiejukes.LOGGER.info(outputStack);
        }
    }

    @Override
    public int getWidth(RecipeHolder<DiscDuplicationRecipe> recipeHolder) {
        return 3;
    }

    @Override
    public int getHeight(RecipeHolder<DiscDuplicationRecipe> recipeHolder) {
        return 3;
    }
}
