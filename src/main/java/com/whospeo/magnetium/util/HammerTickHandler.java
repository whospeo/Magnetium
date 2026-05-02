package com.whospeo.magnetium.util;

import com.whospeo.magnetium.item.ModItems;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class HammerTickHandler {

    private static final double COLLECTION_RADIUS = 15.0;
    private static final int COLLECTION_COOLDOWN = 5;
    private static final double ATTRACTION_SPEED = 0.3;

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(HammerTickHandler::onServerTick);
    }

    private static void onServerTick(MinecraftServer server) {
        if (server.getTickCount() % COLLECTION_COOLDOWN == 0) {
            for (ServerPlayer serverPlayerEntity : server.getPlayerList().getPlayers()) {
                checkAndCollectItems(serverPlayerEntity);
            }
        }
    }

    private static void checkAndCollectItems(Player player) {
        if (hasMagneticHammer(player)) {
            collectNearbyItems(player);
        }
    }

    private static boolean hasMagneticHammer(Player player) {
        net.minecraft.world.item.ItemStack mainHand = player.getMainHandItem();
        net.minecraft.world.item.ItemStack offHand = player.getOffhandItem();

        return mainHand.getItem() == ModItems.MAGNETIC_HAMMER ||
                offHand.getItem() == ModItems.MAGNETIC_HAMMER;
    }

    private static void collectNearbyItems(Player player) {
        AABB area = new AABB(
                player.getX() - COLLECTION_RADIUS,
                player.getY() - COLLECTION_RADIUS,
                player.getZ() - COLLECTION_RADIUS,
                player.getX() + COLLECTION_RADIUS,
                player.getY() + COLLECTION_RADIUS,
                player.getZ() + COLLECTION_RADIUS
        );

        var items = player.level().getEntitiesOfClass(net.minecraft.world.entity.item.ItemEntity.class, area, itemEntity ->
                itemEntity.isAlive() && !itemEntity.hasPickUpDelay()
        );

        Vec3 playerPos = player.position();

        for (net.minecraft.world.entity.item.ItemEntity itemEntity : items) {
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
}