package com.whospeo.magnetium.util;

import com.whospeo.magnetium.item.ModItems;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class HammerTickHandler {

    private static final double COLLECTION_RADIUS = 15.0;
    private static final int COLLECTION_COOLDOWN = 5;
    private static final double ATTRACTION_SPEED = 0.3;

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(HammerTickHandler::onServerTick);
    }

    private static void onServerTick(MinecraftServer server) {
        if (server.getTicks() % COLLECTION_COOLDOWN == 0) {
            server.getPlayerManager().getPlayerList().forEach(HammerTickHandler::checkAndCollectItems);
        }
    }

    private static void checkAndCollectItems(PlayerEntity player) {
        if (hasMagneticHammer(player)) {
            collectNearbyItems(player);
        }
    }

    private static boolean hasMagneticHammer(PlayerEntity player) {
        ItemStack mainHand = player.getMainHandStack();
        ItemStack offHand = player.getOffHandStack();

        return mainHand.getItem() == ModItems.MAGNETIC_HAMMER ||
                offHand.getItem() == ModItems.MAGNETIC_HAMMER;
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
                itemEntity.isAlive() && !itemEntity.cannotPickup() &&
                        itemEntity.getOwner() != player
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
}