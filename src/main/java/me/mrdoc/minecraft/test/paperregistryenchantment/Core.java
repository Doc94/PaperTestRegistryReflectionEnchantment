package me.mrdoc.minecraft.test.paperregistryenchantment;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

    @Getter
    private static Core instance = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Iniciando Plugin.");
        instance = this;

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Deteniendo Plugin.");
    }
}
