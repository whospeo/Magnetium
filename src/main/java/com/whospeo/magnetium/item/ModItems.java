package com.whospeo.magnetium.item;

import com.whospeo.magnetium.Magnetium;
import com.whospeo.magnetium.item.custom.HammerItem;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Properties;
import java.util.function.Function;

public class ModItems {

    public static final Item NORTH_POLE = register("north_pole", Item::new, new Item.Properties().rarity(Rarity.RARE));
    public static final Item SOUTH_POLE = register("south_pole", Item::new, new Item.Properties().rarity(Rarity.RARE));

    public static final Item IRON_STICK = register("iron_stick", Item::new, new Item.Properties().rarity(Rarity.COMMON));

    public static final Item MAGNET = register("magnet_tier_1", Item::new, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
    public static final Item MAGNET_TIER_2 = register("magnet_tier_2", Item::new, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
    public static final Item MAGNET_TIER_3 = register("magnet_tier_3", Item::new, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));

    public static final Item MAGNETIC_HAMMER = register("magnetic_hammer", settings -> new HammerItem(ToolMaterial.NETHERITE, 1, -2.8f, settings), new Item.Properties().rarity(Rarity.EPIC));

    public static final double COLLECTION_RADIUS_TIER_1 = 9.0;
    public static final double COLLECTION_RADIUS_TIER_2 = 12.0;
    public static final double COLLECTION_RADIUS_TIER_3 = 15.0;
    public static double COLLECTION_RADIUS = 0;

    private static final double ATTRACTION_SPEED = 0.3;
    private static final int COLLECTION_COOLDOWN = 5;

    private static void checkAndCollectItems(Entity player) {
        if (hasTriggerItem(player)) {
            collectNearbyItems(player);
        }
    }

    private static boolean hasTriggerItem(Entity entity) {

        boolean check = false;
        Player player = (Player) entity;

        if(player.getInventory().hasAnyMatching(stack -> stack.is(MAGNET) && stack.getCount() > 0)) {
            check = true;
            COLLECTION_RADIUS = COLLECTION_RADIUS_TIER_1;
        }
        if(player.getInventory().hasAnyMatching(stack -> stack.is(MAGNET_TIER_2) && stack.getCount() > 0)) {
            check = true;
            COLLECTION_RADIUS = COLLECTION_RADIUS_TIER_2;
        }
        if(player.getInventory().hasAnyMatching(stack -> stack.is(MAGNET_TIER_3) && stack.getCount() > 0)) {
            check = true;
            COLLECTION_RADIUS = COLLECTION_RADIUS_TIER_3;
        }
        return check;
    }

    private static void collectNearbyItems(Entity player) {
        AABB area = new AABB(
                player.getX() - COLLECTION_RADIUS,
                player.getY() - COLLECTION_RADIUS,
                player.getZ() - COLLECTION_RADIUS,
                player.getX() + COLLECTION_RADIUS,
                player.getY() + COLLECTION_RADIUS,
                player.getZ() + COLLECTION_RADIUS
        );

        var items = player.level().getEntitiesOfClass(ItemEntity.class, area, itemEntity ->
                itemEntity.isAlive() && !itemEntity.hasPickUpDelay()
        );

        Vec3 playerPos = player.position();

        for (ItemEntity itemEntity : items) {
            Vec3 direction = playerPos.subtract(itemEntity.position()).normalize();

            itemEntity.push(
                    direction.x * ATTRACTION_SPEED,
                    direction.y * ATTRACTION_SPEED + 0.1,
                    direction.z * ATTRACTION_SPEED
            );

            if (itemEntity.getOwner() == null) {
                itemEntity.setThrower(player);
            }
        }
    }

    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Magnetium.MOD_ID, name));
        T item = itemFactory.apply(settings.setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

    public static void registerModItems() {
        Magnetium.LOGGER.info("[Magnetium] Registering Items");

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (server.getTickCount() % COLLECTION_COOLDOWN == 0) {
                for (ServerPlayer serverPlayerEntity : server.getPlayerList().getPlayers()) {
                    checkAndCollectItems(serverPlayerEntity);
                }
            }
        });
    }
}
