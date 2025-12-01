package com.whospeo.magnetium.util;

import com.whospeo.magnetium.Magnetium;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {

    public static class Items {

        public static final TagKey<Item> MAGNETIC_ITEMS = createTag("magnetic_item");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(Magnetium.MOD_ID, name));
        }
    }
}
