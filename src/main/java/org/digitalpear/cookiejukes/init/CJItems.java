package org.digitalpear.cookiejukes.init;

import net.emeraldx392.item.ModItems;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.component.Weapon;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import org.digitalpear.cookiejukes.Cookiejukes;
import org.digitalpear.cookiejukes.common.items.SingleUseMaceItem;
import org.digitalpear.cookiejukes.init.data.CompatChecks;

import java.util.ArrayList;
import java.util.List;

public class CJItems {
    public static class Ids {
        private static ResourceKey<Item> create(final String name) {
            return ResourceKey.create(Registries.ITEM, Cookiejukes.id(name));
        }
        public static final ResourceKey<Item> COOKIE_DISC = create("cookie_disc");
        public static final ResourceKey<Item> SWEET_BERRY_COOKIE_DISC = create("sweet_berry_cookie_disc");
        public static final ResourceKey<Item> HONEY_COOKIE_DISC = create("honey_cookie_disc");
        public static final ResourceKey<Item> RESIN_COOKIE_DISC = create("resin_cookie_disc");
        public static final ResourceKey<Item> PUFFERFISH_MACE = create("pufferfish_mace");
    }
    static Consumable FAST_FOOD = Consumables.defaultFood().consumeSeconds(0.8F).build();

    public static final Item COOKIE_DISC = registerCookie(Ids.COOKIE_DISC);
    public static final Item SWEET_BERRY_COOKIE_DISC = registerCookie(Ids.SWEET_BERRY_COOKIE_DISC, FAST_FOOD);
    public static final Item HONEY_COOKIE_DISC = registerCookie(Ids.HONEY_COOKIE_DISC, FAST_FOOD);
    public static final Item RESIN_COOKIE_DISC = registerCookie(Ids.RESIN_COOKIE_DISC);

    public static final Item PUFFERFISH_MACE = registerMaceItem(Ids.PUFFERFISH_MACE,
            new MobEffectInstance(MobEffects.POISON, 20 * 4, 3), new Item.Properties()
            .rarity(Rarity.EPIC)
            .durability(500)
            .component(DataComponents.TOOL, MaceItem.createToolProperties())
            .repairable(Items.BREEZE_ROD).attributes(MaceItem.createAttributes())
            .enchantable(15)
            .component(DataComponents.WEAPON, new Weapon(1)));

    private static Item registerMaceItem(ResourceKey<Item> id, MobEffectInstance effect, Item.Properties properties) {
        return Items.registerItem(id, p -> new SingleUseMaceItem(effect, p), properties);
    }
    private static Item registerCookie(ResourceKey<Item> id, Consumable consumable) {
        return Items.registerItem(id, new Item.Properties().food(Foods.COOKIE, consumable).compostable(ContextIntProviders.COMPOSTABLE_MEDIUM_HIGH));
    }
    private static Item registerCookie(ResourceKey<Item> name) {
        return registerCookie(name, Consumables.DEFAULT_FOOD);
    }

    public static void init() {
        if (CompatChecks.isFDLoaded()){
            insertCookieInCreativeTab(HONEY_COOKIE_DISC);
            insertCookieInCreativeTab(SWEET_BERRY_COOKIE_DISC);
        }
        if (CompatChecks.isRCLoaded()){
            insertCookieInCreativeTab(RESIN_COOKIE_DISC);
        }
        insertCookieInCreativeTab(COOKIE_DISC);
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> {
            entries.insertAfter(Items.SCULK_CATALYST, CJBlocks.SCULK_JAW);
        });
    }

    public static void insertCookieInCreativeTab(Item cookie){
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(entries -> {

            entries.insertAfter(Items.COOKIE, new ItemStack(cookie));
        });
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            List<ItemStack> stacks = new ArrayList<>();
            entries.getContext().holders().lookup(Registries.JUKEBOX_SONG).ifPresent(entry -> entry.listElements().forEach(stack -> {
                ItemStack disc = new ItemStack(cookie);
                disc.set(DataComponents.JUKEBOX_PLAYABLE, new JukeboxPlayable(stack));
                stacks.add(disc);
            }));
            entries.insertAfter(Items.MUSIC_DISC_BOUNCE, stacks);
        });
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(entries -> {
            entries.insertAfter(Items.MACE, PUFFERFISH_MACE);
        });
    }
}
