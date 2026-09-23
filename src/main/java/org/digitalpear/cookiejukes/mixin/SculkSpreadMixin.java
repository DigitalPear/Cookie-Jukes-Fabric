package org.digitalpear.cookiejukes.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SculkBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.digitalpear.cookiejukes.init.CJBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SculkBlock.class)
public abstract class SculkSpreadMixin {
    @Shadow
    protected abstract BlockState getRandomGrowthState(LevelAccessor level, BlockPos pos, RandomSource random, boolean isWorldGen);

    @Redirect(method = "attemptUseCharge", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/SculkBlock;getRandomGrowthState(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;Z)Lnet/minecraft/world/level/block/state/BlockState;"))
    private BlockState changeBlockOnSpawn(SculkBlock instance, LevelAccessor level, BlockPos pos, RandomSource random, boolean isWorldGen){
        return random.nextFloat() < 0.2 ? CJBlocks.SCULK_JAW.defaultBlockState() : getRandomGrowthState(level, pos, random, isWorldGen);
    }
}
