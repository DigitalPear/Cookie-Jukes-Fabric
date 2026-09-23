package org.digitalpear.cookiejukes.client.datagens;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import org.digitalpear.cookiejukes.init.CJBlocks;

import java.util.concurrent.CompletableFuture;

public class CJBlockLootProvider extends FabricBlockLootSubProvider {

    public CJBlockLootProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture);
    }

    @Override
    public void generate() {
        dropWhenSilkTouch(CJBlocks.SCULK_JAW);
    }
}
