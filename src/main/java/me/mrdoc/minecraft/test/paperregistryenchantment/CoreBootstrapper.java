package me.mrdoc.minecraft.test.paperregistryenchantment;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.event.RegistryEvents;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import me.mrdoc.minecraft.test.paperregistryenchantment.ench.AbstractCustomEnchantment;
import me.mrdoc.minecraft.test.paperregistryenchantment.ench.CustomEnchantmentHandler;
import org.jetbrains.annotations.NotNull;
import static me.mrdoc.minecraft.test.paperregistryenchantment.CustomEnchantmentManager.CUSTOM_ENCHANTMENTS;

public class CoreBootstrapper implements PluginBootstrap {

    @Override
    public void bootstrap(@NotNull BootstrapContext context) {
        System.out.println("CARGANDO MAIN BOOTSTRAP");
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
        System.out.println("FIN MAIN BOOTSTRAP");
    }

}
