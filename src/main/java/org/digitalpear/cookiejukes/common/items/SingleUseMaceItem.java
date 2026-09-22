package org.digitalpear.cookiejukes.common.items;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MaceItem;

public class SingleUseMaceItem extends MaceItem {
    private final MobEffectInstance instance;

    public SingleUseMaceItem(MobEffectInstance instance, Properties properties) {
        super(properties);
        this.instance = instance;
    }

    @Override
    public void postHurtEnemy(ItemStack itemStack, LivingEntity mob, LivingEntity attacker) {
        if (canSmashAttack(attacker)) {
            mob.addEffect(new MobEffectInstance(instance));
            ItemStack stack = attacker.getMainHandItem();
            ItemStack maceStack = stack.transmuteCopy(Items.MACE);
            attacker.setItemSlot(EquipmentSlot.MAINHAND, maceStack);
            attacker.level().playLocalSound(attacker, SoundEvents.PUFFER_FISH_DEATH, SoundSource.PLAYERS, 1.0f, 1.0f);
        }
        super.postHurtEnemy(itemStack, mob, attacker);
    }
}
