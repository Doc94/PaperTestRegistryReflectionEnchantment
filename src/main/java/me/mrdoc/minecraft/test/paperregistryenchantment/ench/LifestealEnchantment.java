package me.mrdoc.minecraft.test.paperregistryenchantment.ench;

import io.papermc.paper.registry.data.EnchantmentRegistryEntry;
import io.papermc.paper.registry.event.RegistryFreezeEvent;
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys;
import java.util.function.Consumer;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.enchantments.Enchantment;
import me.mrdoc.minecraft.test.paperregistryenchantment.CustomEnchantmentManager;

@CustomEnchantmentHandler
public class LifestealEnchantment extends AbstractCustomEnchantment {

    public static String NAME = "lifesteal";

    public static LifestealEnchantment getInstance() {
        return (LifestealEnchantment) CustomEnchantmentManager.getEnchantment(NAME);
    }

    public LifestealEnchantment() {
        super(NAME);
    }

    @Override
    public Consumer<EnchantmentRegistryEntry.Builder> generateConsumerEREB(RegistryFreezeEvent<Enchantment, EnchantmentRegistryEntry. Builder> registryFreezeEvent) {
        return builder -> {
            builder.description(Component.text("Lifesteal", NamedTextColor.GREEN));
            builder.supportedItems(registryFreezeEvent.getOrCreateTag(ItemTypeTagKeys.create(Key.key("tools"))));
        };
    }
}
