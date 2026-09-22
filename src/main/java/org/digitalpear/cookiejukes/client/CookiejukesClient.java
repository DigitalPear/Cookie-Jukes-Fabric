package org.digitalpear.cookiejukes.client;

import cc.cassian.rrv.client.recipe.ClientRecipeManager;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.world.item.crafting.RecipeType;

public class CookiejukesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ClientRecipeManager.INSTANCE.getRecipesForType(RecipeType.CRAFTING).forEach(craftingRecipeRecipeHolder -> {

        });
    }
}
