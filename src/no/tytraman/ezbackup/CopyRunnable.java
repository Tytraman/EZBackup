package no.tytraman.ezbackup;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CopyRunnable extends BukkitRunnable {

    private CommandSender sender = null;

    public CopyRunnable() {

    }
    public CopyRunnable(CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public void run() {
        backup();
    }

    private void copyFiles(File source, File output) {
        String absolute = source.getAbsoluteFile().toString();
        if(source.isDirectory() && (!absolute.equals(Main.path.toString()) && !absolute.equals(Main.root + File.separator + "logs"))) {
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

    private void backup() {
        try {
            Files.createDirectory(Main.path);
        } catch(FileAlreadyExistsException ignore) {}
          catch (IOException e) {
            e.printStackTrace();
        }
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd-MM-YYYY HH-mm-ss");
        File output = new File(Main.path + File.separator + formater.format(LocalDateTime.now()));
        System.out.println("[" + Main.INSTANCE.getDescription().getPrefix() + "] Lancement d'un backup...");
        copyFiles(Main.root.toFile(), output);
        System.out.println("[" + Main.INSTANCE.getDescription().getPrefix() + "] Backup effectué avec succès!");
        if(sender != null) {
            sender.sendMessage(ChatColor.GOLD + "[" + Main.INSTANCE.getDescription().getPrefix() + "] Backup effectué avec succès!");
        }
    }


}
