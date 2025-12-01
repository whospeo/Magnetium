package com.whospeo.magnetium;

import com.whospeo.magnetium.item.ModItemGroups;
import com.whospeo.magnetium.item.ModItems;
import com.whospeo.magnetium.util.HammerTickHandler;
import com.whospeo.magnetium.util.HammerUsageEvent;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Magnetium implements ModInitializer {
	public static final String MOD_ID = "magnetium";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModItems.registerModItems();
        ModItemGroups.registerItemGroups();

        PlayerBlockBreakEvents.BEFORE.register(new HammerUsageEvent());
        HammerTickHandler.register();
	}
}