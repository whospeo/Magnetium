package com.whospeo.magnetium.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class HammerItem extends net.minecraft.world.item.Item {
    public HammerItem(net.minecraft.world.item.ToolMaterial material, float attackDamage, float attackSpeed, Properties properties) {
        super(properties.tool(material, net.minecraft.tags.BlockTags.MINEABLE_WITH_PICKAXE, attackDamage, attackSpeed, 3));
    }

    public static List<BlockPos> getBlocksToBeDestroyed(int range, BlockPos initialBlockPos, ServerPlayer player) {
        List<BlockPos> positions = new ArrayList<>();
        net.minecraft.world.phys.HitResult hit = player.pick(20,0,false);
        if (hit.getType() == net.minecraft.world.phys.HitResult.Type.BLOCK) {
            net.minecraft.world.phys.BlockHitResult blockHit = (net.minecraft.world.phys.BlockHitResult) hit;

            if (blockHit.getDirection() == Direction.DOWN || blockHit.getDirection() == Direction.UP) {
                for(int x = -range; x <= range; x++) {
                    for(int y = -range; y <= range; y++) {
                        positions.add(new BlockPos(initialBlockPos.getX() + x, initialBlockPos.getY(), initialBlockPos.getZ() + y));
                    }
                }
            }

            if (blockHit.getDirection() == Direction.NORTH || blockHit.getDirection() == Direction.SOUTH) {
                for(int x = -range; x <= range; x++) {
                    for(int y = -range; y <= range; y++) {
                        positions.add(new BlockPos(initialBlockPos.getX() + x, initialBlockPos.getY() + y, initialBlockPos.getZ()));
                    }
                }
            }

            if (blockHit.getDirection() == Direction.EAST || blockHit.getDirection() == Direction.WEST) {
                for(int x = -range; x <= range; x++) {
                    for(int y = -range; y <= range; y++) {
                        positions.add(new BlockPos(initialBlockPos.getX(), initialBlockPos.getY() + y, initialBlockPos.getZ() + x));
                    }
                }
            }
        }

        return positions;
    }
}
