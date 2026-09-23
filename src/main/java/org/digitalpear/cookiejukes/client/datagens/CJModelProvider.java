package org.digitalpear.cookiejukes.client.datagens;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JukeboxBlock;
import org.digitalpear.cookiejukes.init.CJBlocks;
import org.digitalpear.cookiejukes.init.CJItems;

public class CJModelProvider extends FabricModelProvider {
    public CJModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        createSculkJaws(blockModelGenerators);
    }
    private void createSculkJaws(BlockModelGenerators blockModelGenerators) {
        Block block = CJBlocks.SCULK_JAW;
        TextureMapping defaultTextureMap = new TextureMapping()
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_top"))
                .put(TextureSlot.DOWN, TextureMapping.getBlockTexture(block, "_bottom"))
                .put(TextureSlot.UP, TextureMapping.getBlockTexture(block, "_top"))
                .put(TextureSlot.NORTH, TextureMapping.getBlockTexture(block, "_side"))
                .put(TextureSlot.SOUTH, TextureMapping.getBlockTexture(block, "_side"))
                .put(TextureSlot.EAST, TextureMapping.getBlockTexture(block, "_east"))
                .put(TextureSlot.WEST, TextureMapping.getBlockTexture(block, "_west"))
                ;
        TextureMapping bloomTextureMap = defaultTextureMap
                .copyAndUpdate(TextureSlot.UP, TextureMapping.getBlockTexture(block, "_top_open"))
                .put(TextureSlot.NORTH, TextureMapping.getBlockTexture(block, "_north_open"))
                .put(TextureSlot.SOUTH, TextureMapping.getBlockTexture(block, "_south_open"))
                .put(TextureSlot.EAST, TextureMapping.getBlockTexture(block, "_east_open"))
                .put(TextureSlot.WEST, TextureMapping.getBlockTexture(block, "_west_open"))
                ;
        Identifier defaultModel = ModelTemplates.CUBE.create(block, defaultTextureMap, blockModelGenerators.modelOutput);
        MultiVariant defaultVariant = BlockModelGenerators.plainVariant(defaultModel);
        MultiVariant bloom = BlockModelGenerators.plainVariant(ModelTemplates.CUBE.createWithSuffix(block, "_open", bloomTextureMap, blockModelGenerators.modelOutput));
        blockModelGenerators.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(JukeboxBlock.HAS_RECORD).generate((pulse) -> pulse ? bloom : defaultVariant)));
        blockModelGenerators.registerSimpleItemModel(block, defaultModel);
    }
    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(CJItems.COOKIE_DISC, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(CJItems.SWEET_BERRY_COOKIE_DISC, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(CJItems.HONEY_COOKIE_DISC, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(CJItems.RESIN_COOKIE_DISC, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(CJItems.PUFFERFISH_MACE, ModelTemplates.FLAT_HANDHELD_ITEM);
    }
}
