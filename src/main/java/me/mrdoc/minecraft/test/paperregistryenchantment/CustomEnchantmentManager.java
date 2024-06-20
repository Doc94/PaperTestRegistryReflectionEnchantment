package me.mrdoc.minecraft.test.paperregistryenchantment;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.event.RegistryEvents;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import me.mrdoc.minecraft.test.paperregistryenchantment.ench.AbstractCustomEnchantment;
import me.mrdoc.minecraft.test.paperregistryenchantment.ench.CustomEnchantmentHandler;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public class CustomEnchantmentManager {

    public static final String ENCHANTMENT_PREFIX = "doctest:";
    public static HashMap<Key, AbstractCustomEnchantment> CUSTOM_ENCHANTMENTS = new HashMap<>();

    static void loadAllCustomEnchantments(@NotNull BootstrapContext context) {
        System.out.println("Loading custom enchantments");
        HashSet<Class<?>> reflectionCustomEnchantments = ReflectionUtils.getClasses(CustomEnchantmentManager.class.getPackageName(), CustomEnchantmentHandler.class);

        reflectionCustomEnchantments.forEach(aClass -> {
            try {
                AbstractCustomEnchantment abstractCustomEnchantment = ((AbstractCustomEnchantment) aClass.getConstructor().newInstance());
                CUSTOM_ENCHANTMENTS.put(abstractCustomEnchantment.getKey(), abstractCustomEnchantment);

                // Registry
                context.getLifecycleManager().registerEventHandler(RegistryEvents.ENCHANTMENT.freeze().newHandler(registryFreezeEvent -> {
                    registryFreezeEvent.registry().register(
                            TypedKey.create(RegistryKey.ENCHANTMENT, abstractCustomEnchantment.getKey()),
                            abstractCustomEnchantment.generateConsumerEREB(registryFreezeEvent)
                    );
                }));
                LoggerUtils.info("Se cargo " + abstractCustomEnchantment.getName() + " como encantamiento custom.");
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                LoggerUtils.warn("No se pudo cargar [%s] como encantamiento custom".formatted(aClass.getSimpleName()), e);
            }
        });
        LoggerUtils.info("Se cargaron " + CustomEnchantmentManager.CUSTOM_ENCHANTMENTS.size() + " encantamientos custom.");
    }

    public static AbstractCustomEnchantment getEnchantment(String name) {
        return CUSTOM_ENCHANTMENTS.get(Key.key(ENCHANTMENT_PREFIX.concat(name.toLowerCase())));
    }

}
