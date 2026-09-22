package org.digitalpear.cookiejukes.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.digitalpear.cookiejukes.init.CJBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JukeboxBlockEntity.class)
public class JukeboxBlockEntityMixin {

    @Inject(method = "preRemoveSideEffects", at = @At("HEAD"), cancellable = true)
    private void doNotPopOff(BlockPos pos, BlockState state, CallbackInfo ci){
        if (state.is(CJBlocks.SCULK_JAW)) {
            ci.cancel();
        }
    }
}
