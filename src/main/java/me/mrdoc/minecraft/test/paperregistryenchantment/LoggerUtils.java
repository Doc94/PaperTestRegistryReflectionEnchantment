package me.mrdoc.minecraft.test.paperregistryenchantment;

import net.kyori.adventure.text.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtils {
    private static boolean DEBUG = false;

    public static Logger getLogger() {
        if (Core.getInstance() == null) {
            return LoggerFactory.getLogger("ConterCraftLogger");
        }
        return Core.getInstance().getSLF4JLogger();
    }

    public static void setDebug(boolean status) {
        DEBUG = status;
        debug("Cambiado modo debug a %s".formatted(status));
    }

    public static void debug(String message) {
        if (!DEBUG) {
            return;
        }
        getLogger().info("[DEBUG] [{}] {}", getCallerClass().getSimpleName(), message);
    }

    public static void info(Component message) {
        Core.getInstance().getComponentLogger().info(Component.text("[{}] {}"), getCallerClass().getSimpleName(), message);
    }

    public static void info(String message) {
        getLogger().info("[{}] {}", getCallerClass().getSimpleName(), message);
    }

    public static void info(String message, Throwable throwable) {
        getLogger().info("[%s] %s".formatted(getCallerClass().getSimpleName(), message), throwable);
    }

    public static void warn(String message) {
        getLogger().warn("[{}] {}", getCallerClass().getSimpleName(), message);
    }

    public static void warn(String message, Throwable throwable) {
        getLogger().warn("[%s] %s".formatted(getCallerClass().getSimpleName(), message), throwable);
    }

    public static void error(String message) {
        getLogger().error("[{}] {}", getCallerClass().getSimpleName(), message);
    }

    public static void error(String message, Throwable throwable) {
        getLogger().error("[%s] %s".formatted(getCallerClass().getSimpleName(), message), throwable);
    }

    public static Class<?> getCallerClass() {
        return StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
    }
}
