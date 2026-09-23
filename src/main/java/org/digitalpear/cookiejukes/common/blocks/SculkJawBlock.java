package org.digitalpear.cookiejukes.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.digitalpear.cookiejukes.init.data.CJItemTags;
import org.jspecify.annotations.Nullable;

public class SculkJawBlock extends JukeboxBlock implements SculkBehaviour {
    private final IntProvider xpRange = ConstantInt.of(5);

    public SculkJawBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (state.getValue(HAS_RECORD)){
            BlockEntity var7 = level.getBlockEntity(pos);
            if (var7 instanceof JukeboxBlockEntity jukebox) {
                stopSong(jukebox);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public void stopSong(JukeboxBlockEntity jukebox) {
        if (jukebox.getLevel() != null && !jukebox.getLevel().isClientSide()) {
            ItemStack itemBeforePoppingOut = jukebox.getTheItem();
            if (!itemBeforePoppingOut.isEmpty()) {
                jukebox.removeTheItem();
                jukebox.onSongChanged();
            }
        }
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        InteractionResult result = super.useItemOn(itemStack, state, level, pos, player, hand, hitResult);
        if  (result == InteractionResult.SUCCESS) {
            playEatEffects(player, itemStack.getItem(), level, pos);
        } else if ((itemStack.is(CJItemTags.SCULK_JAW_FOOD)) && !state.getValue(HAS_RECORD)) {
            playEatEffects(player, itemStack.getItem(), level, pos);
            if (!player.isCreative()) {
                itemStack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return result;
    }

    public void playEatEffects(@Nullable Player player, Item particleSource, Level level, BlockPos pos) {
        RandomSource random = level.getRandom();
        for (int i = 0; i < level.getRandom().nextInt(4, 7); i++) {
            level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, particleSource), pos.getX() + random.nextFloat(), pos.getY() + 1f, pos.getZ() + random.nextFloat(), 0, 0, 0);
        }
        level.playLocalSound(player, SoundEvents.GENERIC_EAT.value(), SoundSource.BLOCKS, 0.4f, 0.6f);
    }

    protected void spawnAfterBreak(final BlockState state, final ServerLevel level, final BlockPos pos, final ItemStack tool, final boolean dropExperience) {
        super.spawnAfterBreak(state, level, pos, tool, dropExperience);
        if (dropExperience) {
            this.tryDropExperience(level, pos, tool, this.xpRange);
        }
    }

    @Override
    public int attemptUseCharge(SculkSpreader.ChargeCursor cursor, LevelAccessor level, BlockPos originPos, RandomSource random, SculkSpreader spreader, boolean spreadVeins) {
        return 0;
    }
}
