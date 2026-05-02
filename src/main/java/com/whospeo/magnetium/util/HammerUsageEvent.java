package com.whospeo.magnetium.util;

import com.whospeo.magnetium.item.custom.HammerItem;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import java.util.HashSet;
import java.util.Set;

public class HammerUsageEvent implements PlayerBlockBreakEvents.Before{

    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    @Override
    public boolean beforeBlockBreak(Level level, Player player, BlockPos blockPos, net.minecraft.world.level.block.state.BlockState blockState, net.minecraft.world.level.block.entity.@org.jspecify.annotations.Nullable BlockEntity blockEntity) {
        net.minecraft.world.item.ItemStack mainHandItem = player.getMainHandItem();

        if(mainHandItem.getItem() instanceof HammerItem hammer && player instanceof ServerPlayer serverPlayer) {
            if(HARVESTED_BLOCKS.contains(blockPos)) {
                return true;
            }

            for(BlockPos position : HammerItem.getBlocksToBeDestroyed(1, blockPos, serverPlayer)) {
                if(blockPos == position || !hammer.isCorrectToolForDrops(mainHandItem, level.getBlockState(position))) {
                    continue;
                }

                HARVESTED_BLOCKS.add(position);
                serverPlayer.gameMode.destroyBlock(position);
                HARVESTED_BLOCKS.remove(position);
            }
        }
        return true;
    }
}
