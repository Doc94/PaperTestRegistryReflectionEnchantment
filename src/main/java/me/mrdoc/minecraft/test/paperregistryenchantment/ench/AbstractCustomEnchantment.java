package me.mrdoc.minecraft.test.paperregistryenchantment.ench;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.data.EnchantmentRegistryEntry;
import io.papermc.paper.registry.event.RegistryFreezeEvent;
import java.util.function.Consumer;
import lombok.Getter;
import me.mrdoc.minecraft.test.paperregistryenchantment.CustomEnchantmentManager;
import net.kyori.adventure.key.Key;
import org.bukkit.enchantments.Enchantment;

@Getter
public abstract class AbstractCustomEnchantment {

    private final Key key;
    private final String name;

    public AbstractCustomEnchantment(String name) {
        String keyName = CustomEnchantmentManager.ENCHANTMENT_PREFIX.concat(name);
        this.key = Key.key(keyName);
        this.name = name;
    }

    public Enchantment getEnchantment() {
        return RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT).getOrThrow(this.key);
    }

    public abstract Consumer<EnchantmentRegistryEntry.Builder> generateConsumerEREB(RegistryFreezeEvent<Enchantment, EnchantmentRegistryEntry. Builder> registryFreezeEvent);

}
