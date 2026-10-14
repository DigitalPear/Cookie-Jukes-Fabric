package org.digitalpear.cookiejukes;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.digitalpear.cookiejukes.init.CJBlocks;
import org.digitalpear.cookiejukes.init.CJItems;
import org.digitalpear.cookiejukes.init.CJRecipeSerializers;
import org.digitalpear.cookiejukes.init.data.CJEvents;

public class Cookiejukes implements ModInitializer {
    public static final String MOD_ID = "cookiejukes";
    public static final Logger LOGGER = LogManager.getLogger();

    public static Identifier id(String id) {
        return Identifier.fromNamespaceAndPath(MOD_ID, id);
    }

    @Override
    public void onInitialize() {
        CJBlocks.init();
        CJItems.init();
        CJRecipeSerializers.init();
        CJEvents.init();
    }
}
