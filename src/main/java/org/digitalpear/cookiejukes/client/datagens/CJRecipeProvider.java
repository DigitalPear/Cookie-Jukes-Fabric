package org.digitalpear.cookiejukes.client.datagens;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.impl.resource.conditions.conditions.AllModsLoadedResourceCondition;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.digitalpear.cookiejukes.common.crafting.DiscDuplicationRecipe;
import org.digitalpear.cookiejukes.init.CJItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CJRecipeProvider extends FabricRecipeProvider {


    public CJRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }



    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, BootstrapContext<Recipe<?>> bootstrapContext, BootstrapContext<Advancement> bootstrapContext1) {
        return new RecipeProvider(bootstrapContext, bootstrapContext1) {
            @Override
            public void buildRecipes() {
                blanKCookie(Items.COOKIE, CJItems.COOKIE_DISC);
//                blanKCookie(ModItems.SWEET_BERRY_COOKIE.get(), CJItems.SWEET_BERRY_COOKIE_DISC, new AllModsLoadedResourceCondition(List.of(CompatChecks.FD_ID)));
//                blanKCookie(ModItems.HONEY_COOKIE.get(), CJItems.HONEY_COOKIE_DISC, new AllModsLoadedResourceCondition(List.of(CompatChecks.FD_ID)));

                shapeless(RecipeCategory.COMBAT, CJItems.PUFFERFISH_MACE).requires(Items.MACE).requires(Items.PUFFERFISH)
                        .unlockedBy(getHasName(Items.MACE),has(Items.MACE))
                        .save(output);
            }
            public void blanKCookie(Item cookie, Item disc, ResourceCondition... conditions) {
                ShapedRecipeBuilder.shaped(provider.lookupOrThrow(Registries.ITEM), RecipeCategory.FOOD, disc, 4)
                        .pattern("CCC")
                        .pattern("C C")
                        .pattern("CCC")
                        .define('C', cookie)
                        .unlockedBy(getHasName(cookie), has(cookie))
                        .save(conditions.length > 0 ? withConditions(output, conditions) : output);
                SpecialRecipeBuilder.special(() -> DiscDuplicationRecipe.create(Ingredient.of(cookie), new ItemStackTemplate(disc))).save(conditions.length > 0 ? withConditions(output, conditions) : output, BuiltInRegistries.ITEM.getKey(disc) + "_duplication");
            }
        };
    }

    @Override
    public String getName() {
        return "Recipes";
    }
}
