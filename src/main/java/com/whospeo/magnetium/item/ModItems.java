package com.whospeo.magnetium.item;

import com.whospeo.magnetium.Magnetium;
import com.whospeo.magnetium.item.custom.HammerItem;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class ModItems {

    public static final Item NORTH_POLE = registerItem("north_pole",
            new Item(new Item.Settings().rarity(Rarity.RARE)));
    public static final Item SOUTH_POLE = registerItem("south_pole",
            new Item(new Item.Settings().rarity(Rarity.RARE)));
    public static final Item IRON_STICK = registerItem("iron_stick",
            new Item(new Item.Settings().rarity(Rarity.COMMON)));

    public static final Item MAGNET = registerItem("magnet_tier_1",
            new Item(new Item.Settings().maxCount(1).maxDamage(0).rarity(Rarity.EPIC)));
    public static final Item MAGNET_TIER_2 = registerItem("magnet_tier_2",
            new Item(new Item.Settings().maxCount(1).maxDamage(0).rarity(Rarity.EPIC)));
    public static final Item MAGNET_TIER_3 = registerItem("magnet_tier_3",
            new Item(new Item.Settings().maxCount(1).maxDamage(0).rarity(Rarity.EPIC)));

    public static final Item MAGNETIC_HAMMER = registerItem("magnetic_hammer",
            new HammerItem(ToolMaterials.NETHERITE, new Item.Settings()
                    .attributeModifiers(PickaxeItem.createAttributeModifiers(ToolMaterials.NETHERITE, 1, -2.8f))));

    public static final double COLLECTION_RADIUS_TIER_1 = 9.0;
    public static final double COLLECTION_RADIUS_TIER_2 = 12.0;
    public static final double COLLECTION_RADIUS_TIER_3 = 15.0;
    public static double COLLECTION_RADIUS = 0;

    private static final double ATTRACTION_SPEED = 0.3;
    private static final int COLLECTION_COOLDOWN = 5;

    private static void checkAndCollectItems(PlayerEntity player) {
        if (hasTriggerItem(player)) {
            collectNearbyItems(player);
        }
    }

    private static boolean hasTriggerItem(PlayerEntity player) {

        boolean check = false;

        if(player.getInventory().containsAny(stack -> stack.getItem() == MAGNET && stack.getCount() > 0)) {
            check = player.getInventory().containsAny(stack ->
                    stack.getItem() == MAGNET && stack.getCount() > 0
            );
            COLLECTION_RADIUS = COLLECTION_RADIUS_TIER_1;
        }
        if(player.getInventory().containsAny(stack -> stack.getItem() == MAGNET_TIER_2 && stack.getCount() > 0)) {
            check = player.getInventory().containsAny(stack ->
                    stack.getItem() == MAGNET_TIER_2 && stack.getCount() > 0
            );
            COLLECTION_RADIUS = COLLECTION_RADIUS_TIER_2;
        }
        if(player.getInventory().containsAny(stack -> stack.getItem() == MAGNET_TIER_3 && stack.getCount() > 0)) {
            check = player.getInventory().containsAny(stack ->
                    stack.getItem() == MAGNET_TIER_3 && stack.getCount() > 0
            );
            COLLECTION_RADIUS = COLLECTION_RADIUS_TIER_3;
        }
        return check;
    }

    private static void collectNearbyItems(PlayerEntity player) {
        Box area = new Box(
                player.getX() - COLLECTION_RADIUS,
                player.getY() - COLLECTION_RADIUS,
                player.getZ() - COLLECTION_RADIUS,
                player.getX() + COLLECTION_RADIUS,
                player.getY() + COLLECTION_RADIUS,
                player.getZ() + COLLECTION_RADIUS
        );

        var items = player.getEntityWorld().getEntitiesByClass(ItemEntity.class, area, itemEntity ->
                itemEntity.isAlive() && !itemEntity.cannotPickup()
        );

        Vec3d playerPos = player.getPos();

        for (ItemEntity itemEntity : items) {

            Vec3d direction = playerPos.subtract(itemEntity.getPos()).normalize();

            itemEntity.addVelocity(
                    direction.x * ATTRACTION_SPEED,
                    direction.y * ATTRACTION_SPEED + 0.1,
                    direction.z * ATTRACTION_SPEED
            );

            if (itemEntity.getOwner() == null) {
                itemEntity.setOwner(player.getUuid());
            }
        }
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Magnetium.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Magnetium.LOGGER.info("Registering Mod Items for " + Magnetium.MOD_ID);

        /*ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(NORTH_POLE);
            entries.add(SOUTH_POLE);
        });*/

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (server.getTicks() % COLLECTION_COOLDOWN == 0) {
                for (ServerPlayerEntity serverPlayerEntity : server.getPlayerManager().getPlayerList()) {
                    checkAndCollectItems(serverPlayerEntity);
                }
            }
        });
    }
}
