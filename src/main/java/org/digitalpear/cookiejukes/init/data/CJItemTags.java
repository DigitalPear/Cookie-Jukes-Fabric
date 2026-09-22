package org.digitalpear.cookiejukes.init.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.digitalpear.cookiejukes.Cookiejukes;

public class CJItemTags {
    public static final TagKey<Item> SCULK_JAW_FOOD = bind("sculk_jaw_food");
    public static final TagKey<Item> COOKIE_DISCS = bind("cookie_discs");
    public static final TagKey<Item> DUPLICATABLE_DISCS = bind("duplicatable_discs");

    private static TagKey<Item> bind(final String name) {
        return TagKey.create(Registries.ITEM, Cookiejukes.id(name));
    }
}
