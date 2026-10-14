package org.digitalpear.cookiejukes.client.datagens;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import org.digitalpear.cookiejukes.init.CJItems;
import org.digitalpear.cookiejukes.init.data.CJItemTags;

import java.util.concurrent.CompletableFuture;

public class CJItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
    public CJItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture, new CJBlockTagProvider(output, registryLookupFuture));
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(CJItemTags.COOKIE_DISCS)
                .add(CJItems.Ids.COOKIE_DISC)
                .add(CJItems.Ids.HONEY_COOKIE_DISC)
                .add(CJItems.Ids.SWEET_BERRY_COOKIE_DISC)
                .add(CJItems.Ids.RESIN_COOKIE_DISC)
        ;
        tag(CJItemTags.DUPLICATABLE_DISCS).forceAddTag(ConventionalItemTags.MUSIC_DISCS);
        tag(CJItemTags.SCULK_JAW_FOOD).addTag(ConventionalItemTags.COOKIE_FOODS);
        tag(ItemTags.PARROT_POISONOUS_FOOD).addTag(CJItemTags.COOKIE_DISCS);

        tag(ConventionalItemTags.COOKIE_FOODS).addTag(CJItemTags.COOKIE_DISCS);
        tag(ConventionalItemTags.MACE_TOOLS).add(CJItems.Ids.PUFFERFISH_MACE);
    }
}
