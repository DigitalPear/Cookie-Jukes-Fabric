package org.digitalpear.cookiejukes.common.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.ShapedCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplays;
import net.minecraft.world.level.Level;
import org.digitalpear.cookiejukes.init.CJItems;
import org.digitalpear.cookiejukes.init.data.CJItemTags;
import org.joml.Vector2i;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DiscDuplicationRecipe extends CustomRecipe {
    public static final MapCodec<DiscDuplicationRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec((i) -> i.group(
            Ingredient.CODEC.fieldOf("cookie").forGetter((o) -> o.cookie),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter((o) -> o.result)
    ).apply(i, DiscDuplicationRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, DiscDuplicationRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, (o) -> o.cookie,
            ItemStackTemplate.STREAM_CODEC, (o) -> o.result,
            DiscDuplicationRecipe::new);

    public static final RecipeSerializer<DiscDuplicationRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final Ingredient cookie;
    private final ItemStackTemplate result;

    private DiscDuplicationRecipe(Ingredient cookie, ItemStackTemplate result){
        this.cookie = cookie;
        this.result = result;
    }

    public static DiscDuplicationRecipe create(Ingredient cookie, ItemStackTemplate result){
        return new DiscDuplicationRecipe(cookie, result);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.isEmpty() || input.size() < 9) {
            return false;
        }
        List<Vector2i> COOKIE_POSITIONS = List.of(
                new Vector2i(0, 0), new Vector2i(1, 0), new Vector2i(2, 0),
                new Vector2i(0, 1), new Vector2i(2, 1),
                new Vector2i(0, 2), new Vector2i(1, 2), new Vector2i(2, 2)
        );
        return input.getItem(1, 1).is(CJItemTags.DUPLICATABLE_DISCS) && COOKIE_POSITIONS.stream().allMatch(pos -> cookie.test(input.getItem(pos.x, pos.y)));
    }

    @Override
    public @NonNull ItemStack assemble(CraftingInput input) {
        ItemStack stack = input.getItem(1, 1);
        ItemStack output = this.result.apply(DataComponentPatch.builder()
                .set(DataComponents.JUKEBOX_PLAYABLE, Objects.requireNonNull(stack.get(DataComponents.JUKEBOX_PLAYABLE)))
                .build());
        output.setCount(4);
        return output;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> result = NonNullList.withSize(input.size(), ItemStack.EMPTY);

        for(int slot = 0; slot < result.size(); ++slot) {
            ItemStack item = input.getItem(slot);
            if (item.is(ConventionalItemTags.MUSIC_DISCS)){
                result.set(slot, item.copy());
            }
            else{
                ItemStackTemplate remainder = item.getCraftingRemainder();
                result.set(slot, remainder != null ? remainder.create() : ItemStack.EMPTY);
            }
        }
        return result;
    }


    public static List<SlotDisplay> getRecipes() {
        List<SlotDisplay> recipes = new ArrayList<>();
        List<Holder<Item>> items = new ArrayList<>();
        BuiltInRegistries.ITEM.getTagOrEmpty(CJItemTags.DUPLICATABLE_DISCS).forEach(items::add);

        ItemStack stack = CJItems.COOKIE_DISC.getDefaultInstance();
        recipes.set(0, new SlotDisplay.ItemSlotDisplay(Items.COOKIE));
        recipes.set(1, new SlotDisplay.ItemSlotDisplay(Items.COOKIE));
        recipes.set(2, new SlotDisplay.ItemSlotDisplay(Items.COOKIE));
        recipes.set(3, new SlotDisplay.ItemSlotDisplay(Items.COOKIE));
        recipes.set(4, new SlotDisplay.TagSlotDisplay(HolderSet.direct(items)));
        recipes.set(5, new SlotDisplay.ItemSlotDisplay(Items.COOKIE));
        recipes.set(6, new SlotDisplay.ItemSlotDisplay(Items.COOKIE));
        recipes.set(7, new SlotDisplay.ItemSlotDisplay(Items.COOKIE));
        recipes.set(8, new SlotDisplay.ItemSlotDisplay(Items.COOKIE));
        recipes.set(9, new SlotDisplay.ItemStackSlotDisplay(ItemStackTemplate.fromStack(stack)));
        return recipes;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return SERIALIZER;
    }
}
