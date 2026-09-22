package org.digitalpear.cookiejukes.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.registration.IVanillaCategoryExtensionRegistration;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import org.digitalpear.cookiejukes.Cookiejukes;
import org.digitalpear.cookiejukes.common.crafting.DiscDuplicationRecipe;
import org.digitalpear.cookiejukes.init.CJItems;

@JeiPlugin()
public class CookieJukesJEIPlugin implements IModPlugin {

    @Override
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
        registration.getCraftingCategory().addExtension(DiscDuplicationRecipe.class, new DiscDuplicationCraftingExtension());
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerFromDataComponentTypes(CJItems.COOKIE_DISC, DataComponents.JUKEBOX_PLAYABLE);
        registration.registerFromDataComponentTypes(CJItems.HONEY_COOKIE_DISC, DataComponents.JUKEBOX_PLAYABLE);
        registration.registerFromDataComponentTypes(CJItems.SWEET_BERRY_COOKIE_DISC, DataComponents.JUKEBOX_PLAYABLE);
        registration.registerFromDataComponentTypes(CJItems.RESIN_COOKIE_DISC, DataComponents.JUKEBOX_PLAYABLE);
    }

    @Override
    public Identifier getPluginUid() {
        return Cookiejukes.id("cookie_jukes");
    }
}
