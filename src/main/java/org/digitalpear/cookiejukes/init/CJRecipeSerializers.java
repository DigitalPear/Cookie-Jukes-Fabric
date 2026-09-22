package org.digitalpear.cookiejukes.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import org.digitalpear.cookiejukes.Cookiejukes;
import org.digitalpear.cookiejukes.common.crafting.DiscDuplicationRecipe;

public class CJRecipeSerializers {
    public static void init() {
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Cookiejukes.id("disc_duplication"), DiscDuplicationRecipe.SERIALIZER);
    }
}
