package org.digitalpear.cookiejukes.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.Vec3;
import org.digitalpear.cookiejukes.init.CJBlocks;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JukeboxBlockEntity.class)
public abstract class JukeboxBlockEntityMixin{

    @Inject(method = "preRemoveSideEffects", at = @At("HEAD"), cancellable = true)
    private void doNotPopOff(BlockPos pos, BlockState state, CallbackInfo ci){
        if (state.is(CJBlocks.SCULK_JAW)) {
            ci.cancel();
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private static void spreadSignal(final Level level, final BlockPos blockPos, final BlockState blockState, final JukeboxBlockEntity jukebox, CallbackInfo ci){
        if (level instanceof ServerLevel){
            if (blockState.is(CJBlocks.SCULK_JAW) && jukebox.getSongPlayer().isPlaying()) {
                if (jukebox.getSongPlayer().getTicksSinceSongStarted() %20L == 0){
                    int frequency = 10;
                    if (!jukebox.getTheItem().isEmpty()){
                        frequency = jukebox.getTheItem().get(DataComponents.JUKEBOX_PLAYABLE).song().value().comparatorOutput();
                    }
                    tryResonateVibration(null, jukebox.getLevel().registryAccess().lookupOrThrow(Registries.GAME_EVENT), level, blockPos, frequency);
                }
            }
        }
    }
    @Unique
    private static void tryResonateVibration(final @Nullable Entity sourceEntity, HolderLookup<GameEvent> gameEventHolderLookup, final Level level, final BlockPos pos, final int vibrationFrequency) {
        for(Direction direction : Direction.values()) {
            BlockPos relativePos = pos.relative(direction);
            BlockState blockState = level.getBlockState(relativePos);
            Holder<GameEvent> eventHolder = gameEventHolderLookup.getOrThrow(VibrationSystem.getResonanceEventByFrequency(vibrationFrequency));
            level.gameEvent(eventHolder, Vec3.atBottomCenterOf(relativePos).add(0, 0.9, 0), GameEvent.Context.of(sourceEntity, blockState));
        }
    }
}
