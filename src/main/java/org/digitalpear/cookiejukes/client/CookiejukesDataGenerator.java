package org.digitalpear.cookiejukes.client;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.digitalpear.cookiejukes.client.datagens.*;
import org.digitalpear.cookiejukes.client.datagens.CJRecipeProvider;

public class CookiejukesDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(CJRecipeProvider::new);
        pack.addProvider(CJModelProvider::new);
        pack.addProvider(CJLanguageProvider::new);
        pack.addProvider(CJAdvancementProvider::new);

        pack.addProvider(CJBlockTagProvider::new);
        pack.addProvider(CJItemTagProvider::new);
    }
}
