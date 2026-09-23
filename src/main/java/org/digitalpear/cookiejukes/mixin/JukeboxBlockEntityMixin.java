package org.digitalpear.cookiejukes.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.phys.Vec3;
import org.digitalpear.cookiejukes.init.CJBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JukeboxBlockEntity.class)
public abstract class JukeboxBlockEntityMixin {

    @Shadow
    public abstract BlockEntity getContainerBlockEntity();

    @Shadow
    private ItemStack item;

    @Inject(method = "preRemoveSideEffects", at = @At("HEAD"), cancellable = true)
    private void doNotPopOff(BlockPos pos, BlockState state, CallbackInfo ci){
        if (state.is(CJBlocks.SCULK_JAW)) {
            ci.cancel();
        }
    }
}
