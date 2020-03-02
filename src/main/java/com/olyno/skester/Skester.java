package com.olyno.skester;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Skester extends JavaPlugin {

    public static Skester instance;
    SkriptAddon addon;

    public void onEnable() {

        // Register Addon
        instance = this;
        addon = Skript.registerAddon(this);
        try {
            addon.loadClasses("com.olyno.skester.skript");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Register Metrics
        Metrics metrics = new Metrics(this);
        metrics.addCustomChart(new Metrics.SimplePie("used_language", () ->
                getConfig().getString("language", "en")));
        metrics.addCustomChart(new Metrics.SimplePie("skript_version", () ->
                Bukkit.getServer().getPluginManager().getPlugin("Skript").getDescription().getVersion()));
        metrics.addCustomChart(new Metrics.SimplePie("skent_version", () ->
                this.getDescription().getVersion()));
        metrics.addCustomChart(new Metrics.DrilldownPie("java_version", () -> {
            Map<String, Map<String, Integer>> map = new HashMap<>();
            String javaVersion = System.getProperty("java.version");
            Map<String, Integer> entry = new HashMap<>();
            entry.put(javaVersion, 1);
            if (javaVersion.startsWith("1.7")) {
                map.put("Java 1.7", entry);
            } else if (javaVersion.startsWith("1.8")) {
                map.put("Java 1.8", entry);
            } else if (javaVersion.startsWith("1.9")) {
                map.put("Java 1.9", entry);
            } else {
                map.put("Other", entry);
            }
            return map;
        }));

        // Register events
        Reflections reflections = new Reflections("com.olyno.skester.skript.events");
        Set<Class<? extends Listener>> events = reflections.getSubTypesOf(Listener.class);
        events.forEach(
                (event) -> {
                    try {
                        Listener evt = event.getDeclaredConstructor().newInstance();
                        getServer().getPluginManager().registerEvents(evt, this);
                    } catch (Exception ignored) {
                    }
                }
        );

    }

}