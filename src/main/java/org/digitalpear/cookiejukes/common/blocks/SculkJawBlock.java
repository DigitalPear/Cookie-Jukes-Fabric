package org.digitalpear.cookiejukes.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.digitalpear.cookiejukes.init.data.CJItemTags;
import org.jspecify.annotations.Nullable;

public class SculkJawBlock extends JukeboxBlock {
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
}
