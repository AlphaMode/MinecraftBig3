/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.neoforged.neoforge.debug.entity;

import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(GravityAttributeTest.MODID)
public class GravityAttributeTest {
    public static final boolean ENABLE = false;
    public static final String MODID = "gravity_attribute_test";
    private static Logger logger = LogManager.getLogger(MODID);
    private int ticks;
    private static final UUID REDUCED_GRAVITY_ID = UUID.fromString("DEB06000-7979-4242-8888-00000DEB0600");
    private static final AttributeModifier REDUCED_GRAVITY = (new AttributeModifier(REDUCED_GRAVITY_ID, "Reduced gravity", (double) -0.80, Operation.MULTIPLY_TOTAL));
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    private static final DeferredItem<Item> TEST_ITEM = ITEMS.register("gravity_stick", () -> new ItemGravityStick(new Properties().rarity(Rarity.RARE)));

    public GravityAttributeTest() {
        if (ENABLE) {
            NeoForge.EVENT_BUS.register(this);
            IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
            modEventBus.register(this);
            ITEMS.register(modEventBus);
            modEventBus.addListener(this::addCreative);
        }
    }

    @SubscribeEvent
    public void worldTick(TickEvent.LevelTickEvent event) {
        if (!event.level.isClientSide) {
            if (ticks++ > 60) {
                ticks = 0;
                Level w = event.level;
                List<LivingEntity> list;
                if (w.isClientSide) {
                    ClientLevel cw = (ClientLevel) w;
                    list = new ArrayList<>(100);
                    for (Entity e : cw.entitiesForRendering()) {
                        if (e.isAlive() && e instanceof LivingEntity)
                            list.add((LivingEntity) e);
                    }
                } else {
                    ServerLevel sw = (ServerLevel) w;
                    Stream<LivingEntity> s = StreamSupport.stream(sw.getEntities().getAll().spliterator(), false)
                            .filter(Entity::isAlive)
                            .filter(e -> e instanceof LivingEntity)
                            .map(e -> (LivingEntity) e);
                    list = s.collect(Collectors.toList());
                }

                for (LivingEntity liv : list) {
                    AttributeInstance grav = liv.getAttribute(NeoForgeMod.ENTITY_GRAVITY.value());

                    boolean inPlains = liv.level().getBiome(liv.blockPosition()).is(BiomeTags.IS_FOREST);
                    if (inPlains && !grav.hasModifier(REDUCED_GRAVITY)) {
                        logger.info("Granted low gravity to Entity: {}", liv);
                        grav.addTransientModifier(REDUCED_GRAVITY);
                    } else if (!inPlains && grav.hasModifier(REDUCED_GRAVITY)) {
                        logger.info("Removed low gravity from Entity: {}", liv);
                        grav.removeModifier(REDUCED_GRAVITY_ID);
                    }
                }
            }

        }
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES)
            event.accept(TEST_ITEM);
    }

    public static class ItemGravityStick extends Item {
        private static final UUID GRAVITY_MODIFIER = UUID.fromString("DEB06001-7979-4242-8888-10000DEB0601");

        public ItemGravityStick(Properties properties) {
            super(properties);
        }

        @Override
        public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
            @SuppressWarnings("deprecation")
            Multimap<Attribute, AttributeModifier> multimap = super.getDefaultAttributeModifiers(slot);
            if (slot == EquipmentSlot.MAINHAND)
                multimap.put(NeoForgeMod.ENTITY_GRAVITY.value(), new AttributeModifier(GRAVITY_MODIFIER, "More Gravity", 1.0D, Operation.ADDITION));

            return multimap;
        }
    }
}
