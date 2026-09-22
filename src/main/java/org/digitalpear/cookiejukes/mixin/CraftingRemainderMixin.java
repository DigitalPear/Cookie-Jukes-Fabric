package org.digitalpear.cookiejukes.mixin;

import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import org.digitalpear.cookiejukes.init.data.CJItemTags;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public class CraftingRemainderMixin implements FabricItem {

    @Override
    public @Nullable ItemStackTemplate getCraftingRemainder(ItemStack stack) {
        if (!stack.isEmpty() && stack.is(CJItemTags.DUPLICATABLE_DISCS)) {
            return ItemStackTemplate.fromNonEmptyStack(stack);
        }
        return FabricItem.super.getCraftingRemainder(stack);
    }
}
