package org.digitalpear.cookiejukes.init;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityType;
import net.minecraft.core.registries.Registries;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.digitalpear.cookiejukes.Cookiejukes;
import org.digitalpear.cookiejukes.common.blocks.SculkJawBlock;

import java.util.function.Function;

public class CJBlocks {
    public static class Ids {
        public static final BlockItemId SCULK_JAW = createWithItem("sculk_jaw");

        private static BlockItemId createWithItem(final String name) {
            return BlockItemId.create(Cookiejukes.id(name), Cookiejukes.id(name));
        }
        private static BlockItemId createWithItem(final String block, final String item) {
            return BlockItemId.create(Cookiejukes.id(block), Cookiejukes.id(item));
        }
        private static ResourceKey<Block> create(final String name) {
            return ResourceKey.create(Registries.BLOCK, Cookiejukes.id(name));
        }
    }
    public static Block register(final ResourceKey<Block> id, final Function<BlockBehaviour.Properties, Block> factory, final BlockBehaviour.Properties properties) {
        return Blocks.register(id, factory, properties);
    }
    public static Block register(final BlockItemId id, final Function<BlockBehaviour.Properties, Block> factory, final BlockBehaviour.Properties properties) {
        return register(id.block(), factory, properties);
    }

    public static Block registerWithItem(final BlockItemId id, final Function<BlockBehaviour.Properties, Block> factory, final BlockBehaviour.Properties properties) {
        Block block = register(id.block(), factory, properties);
        Items.registerBlock(id, block);
        return block;
    }

    public static final Block SCULK_JAW = registerWithItem(Ids.SCULK_JAW, SculkJawBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SCULK_CATALYST));

    public static void init(){
        var entity = (FabricBlockEntityType) BlockEntityTypes.JUKEBOX;
        entity.addValidBlock(SCULK_JAW);
    }
}
