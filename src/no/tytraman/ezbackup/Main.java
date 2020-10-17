package no.tytraman.ezbackup;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Main extends JavaPlugin {
    public static Main INSTANCE;
    public static Path root = Paths.get(".").toAbsolutePath().normalize();
    public static Path path = Paths.get(root + File.separator + "ezbackups").toAbsolutePath().normalize();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        INSTANCE = this;
        int TICKS = INSTANCE.getConfig().getInt("ticks");
        boolean startup = INSTANCE.getConfig().getBoolean("backup-on-startup");
        System.out.println("[" + INSTANCE.getDescription().getPrefix() + "] TICKS: " + TICKS);
        getCommand("backup").setExecutor(new cmdBackup());
        getCommand("backup").setTabCompleter(new cmdTab());
        // S'exécute après que le serveur ai fini de se lancer
        new CopyRunnable(null).runTaskTimerAsynchronously(this, startup ? 0 : TICKS, TICKS);
    }

}
