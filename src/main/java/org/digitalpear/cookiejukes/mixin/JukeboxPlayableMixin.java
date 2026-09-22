package org.digitalpear.cookiejukes.mixin;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.JukeboxPlayable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.digitalpear.cookiejukes.init.CJBlocks;
import org.digitalpear.cookiejukes.init.CJItems;
import org.digitalpear.cookiejukes.init.data.CJItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(JukeboxPlayable.class)
public class JukeboxPlayableMixin {

    @Redirect(method = "tryInsertIntoJukebox", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z"))
    private static boolean changeBlockCheck(BlockState instance, Object o){
        return (instance.is(Blocks.JUKEBOX) || instance.is(CJBlocks.SCULK_JAW)) && !instance.getValue(JukeboxBlock.HAS_RECORD);
    }

    @Inject(method = "tryInsertIntoJukebox", at = @At("HEAD"), cancellable = true)
    private static void disallowWrongDisc(Level level, BlockPos pos, ItemStack toInsert, Player player, CallbackInfoReturnable<InteractionResult> cir){
        BlockState blockState = level.getBlockState(pos);
        if (toInsert.is(ConventionalItemTags.COOKIE_FOODS)){
            if (blockState.is(Blocks.JUKEBOX)){
                cir.setReturnValue(InteractionResult.PASS);
                cir.cancel();
            }
        }
        else {
            if (blockState.is(CJBlocks.SCULK_JAW)){
                cir.setReturnValue(InteractionResult.PASS);
                cir.cancel();
            }
        }
    }
}
