package org.digitalpear.cookiejukes.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ShriekParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.JukeboxSongPlayer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.digitalpear.cookiejukes.init.CJBlocks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JukeboxSongPlayer.class)
public class JukeboxSongPlayerMixin {
    @Shadow
    @Final
    private BlockPos blockPos;

    @Inject(method = "spawnMusicParticles", at = @At(value = "HEAD"), cancellable = true)
    private static void changeParticle(LevelAccessor level, BlockPos blockPos, CallbackInfo ci){
        if (level instanceof ServerLevel serverLevel){
            if (serverLevel.getBlockState(blockPos).is(CJBlocks.SCULK_JAW)) {
                Vec3 pos = Vec3.atBottomCenterOf(blockPos).add(0.0F, 1.2F, 0.0F);
                serverLevel.sendParticles(new ShriekParticleOption(10), pos.x(), pos.y(), pos.z(), 0, 0, 0.0F, 0.0F, 1.0F);
                ci.cancel();
            }
        }
    }

}
