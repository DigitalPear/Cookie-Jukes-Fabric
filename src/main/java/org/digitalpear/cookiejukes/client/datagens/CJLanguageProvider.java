package org.digitalpear.cookiejukes.client.datagens;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import org.digitalpear.cookiejukes.init.CJBlocks;
import org.digitalpear.cookiejukes.init.CJItems;

import java.util.concurrent.CompletableFuture;

public class CJLanguageProvider extends FabricLanguageProvider {
    public CJLanguageProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(packOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
        translationBuilder.add(CJItems.COOKIE_DISC, "Cookie Disc");
        translationBuilder.add(CJItems.SWEET_BERRY_COOKIE_DISC, "Sweet Berry Cookie Disc");
        translationBuilder.add(CJItems.HONEY_COOKIE_DISC, "Honey Cookie Disc");
        translationBuilder.add(CJItems.RESIN_COOKIE_DISC, "Resin Cookie Disc");

        translationBuilder.add(CJBlocks.SCULK_JAW, "Sculk Jaw");
        translationBuilder.add("advancements.adventure.get_cookie_disc.title", "How the Cookie Crumbles");
        translationBuilder.add("advancements.adventure.get_cookie_disc.description", "Feed a Sculk Jaw a cookie in the Deep Dark.");

        translationBuilder.add(CJItems.PUFFERFISH_MACE, "Pufferfish Mace");
    }
}
