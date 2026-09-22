package org.digitalpear.cookiejukes.client.datagens;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import org.digitalpear.cookiejukes.init.CJBlocks;

import java.util.concurrent.CompletableFuture;

public class CJBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
    public CJBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(BlockTags.MINEABLE_WITH_HOE).add(CJBlocks.Ids.SCULK_JAW.block());
    }
}
