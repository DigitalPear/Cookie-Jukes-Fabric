package org.digitalpear.cookiejukes.client.datagens;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.advancements.*;
import net.minecraft.advancements.predicates.BlockPredicate;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.predicates.LocationPredicate;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.advancements.triggers.ItemUsedOnLocationTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import org.digitalpear.cookiejukes.Cookiejukes;
import org.digitalpear.cookiejukes.init.CJBlocks;
import org.digitalpear.cookiejukes.init.CJItems;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class CJAdvancementProvider extends FabricAdvancementProvider {
    public CJAdvancementProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider registries, Consumer<AdvancementHolder> consumer) {
        HolderLookup<Block> blocks = registries.lookupOrThrow(Registries.BLOCK);
        HolderLookup<Item> items = registries.lookupOrThrow(Registries.ITEM);
        AdvancementHolder makeCookieDisc = Advancement.Builder.advancement()
                .parent(Identifier.withDefaultNamespace("adventure/kill_a_mob"))
                .display(new DisplayInfo(new ItemStackTemplate(CJItems.COOKIE_DISC.asItem()),
                        Component.translatable("advancements.adventure.create_cookie_disc.title"),
                        Component.translatable("advancements.adventure.create_cookie_disc.description"),
                        Optional.empty(),
                        AdvancementType.TASK,
                        true, true, false
                ))
                .addCriterion("make_cookie", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items, CJItems.COOKIE_DISC)))
                .save(consumer, Cookiejukes.id("adventure/create_cookie_disc"))
                ;
        AdvancementHolder feedJawCookie = Advancement.Builder.advancement()
                .parent(makeCookieDisc)
                .addCriterion("use_sculk_jaw", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blocks, CJBlocks.SCULK_JAW)).setBiomes(HolderSet.direct(registries.lookupOrThrow(Registries.BIOME).getOrThrow(Biomes.DEEP_DARK))), ItemPredicate.Builder.item().of(items, ConventionalItemTags.COOKIE_FOODS)))
                .display(new DisplayInfo(new ItemStackTemplate(CJItems.COOKIE_DISC.asItem()),
                        Component.translatable("advancements.adventure.get_cookie_disc.title"),
                        Component.translatable("advancements.adventure.get_cookie_disc.description"),
                        Optional.empty(),
                        AdvancementType.GOAL,
                        true, true, false
                ))
                .rewards(new AdvancementRewards(12, HolderSet.empty(), List.of(), Optional.empty()))
                .save(consumer, Cookiejukes.id("adventure/feed_sculk_jaw"));

        consumer.accept(makeCookieDisc);
        consumer.accept(feedJawCookie);
    }
}
