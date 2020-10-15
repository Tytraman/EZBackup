package no.tytraman.ezbackup;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main extends JavaPlugin {
    public static Main INSTANCE;
    public static Path root = Paths.get(".").toAbsolutePath().normalize();
    public static Path path = Paths.get(root + File.separator + "ezbackups").toAbsolutePath().normalize();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        INSTANCE = this;
        int TICKS = INSTANCE.getConfig().getInt("ticks");
        System.out.println("TICKS = " + TICKS);

        /*
        new BukkitRunnable() {
            @Override
            public void run() {
                backup();
            }
        }.runTaskTimerAsynchronously(this, 600, TICKS);
        */
    }

    private static void copyFiles(File source, File output) {
        String absolute = source.getAbsoluteFile().toString();
        if(source.isDirectory() && (!absolute.equals(path.toString()) && !absolute.equals(root + File.separator + "logs"))) {
            output.mkdirs();
            for(String str : source.list()) {
                copyFiles(new File(source, str), new File(output, str));
            }
        }else if(source.isFile()) {
            try {
                Files.copy(source.toPath(), output.toPath());
            } catch(FileSystemException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void backup() {
        try {
            Files.createDirectory(path);
        } catch(FileAlreadyExistsException e) {
            System.out.println("[" + INSTANCE.getDescription().getPrefix() + "] Le dossier ezbackups existe déjà");
        } catch (IOException e) {
            e.printStackTrace();
        }
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd-MM-YYYY HH-mm-ss");
        File output = new File(path + File.separator + formater.format(LocalDateTime.now()));
        System.out.println("[" + INSTANCE.getDescription().getPrefix() + "] Copie des fichiers dans " + output);
        copyFiles(root.toFile(), output);
    }

}
