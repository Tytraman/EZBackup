package no.tytraman.ezbackup;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CopyRunnable extends BukkitRunnable {

    private final CommandSender sender;
    private final byte[] BUFFER = new byte[4096];

    public CopyRunnable(CommandSender sender) {
        this.sender = sender;
    }

    @Override
    public void run() {
        backup();
        deleteOldestFiles();
    }

    private void backup() {
        try {
            Files.createDirectory(Main.path);
        } catch(FileAlreadyExistsException ignore) {}
          catch (IOException e) {
            e.printStackTrace();
        }
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        File output = new File(Main.path + File.separator + formater.format(LocalDateTime.now()) + ".zip");
        System.out.println("[" + Main.INSTANCE.getDescription().getPrefix() + "] Lancement d'un backup...");
        try(ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(output)))) {
            zip.setMethod(ZipOutputStream.DEFLATED);
            zip.setLevel(9);
            zipRecurcively(Main.root.toFile(), zip);
            System.out.println("[" + Main.INSTANCE.getDescription().getPrefix() + "] Backup effectué avec succès!");
            if(sender != null) {
                sender.sendMessage(ChatColor.GOLD + "[" + Main.INSTANCE.getDescription().getPrefix() + "] Backup effectué avec succès!");
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void zipRecurcively(File folder, ZipOutputStream zos) throws IOException {
        String absolute = folder.getAbsoluteFile().toString();
        if(folder.isDirectory() && (!absolute.equals(Main.path.toString()) && !absolute.equals(Main.root + File.separator + "logs"))) {
            for(String f : folder.list()) {
                zipRecurcively(new File(folder, f), zos);
            }
        }else if(folder.isFile()){
            writeToZip(folder, zos);
        }
    }


    private void deleteOldestFiles() {
        int maxBackups = Main.INSTANCE.getConfig().getInt("max-backups");
        System.out.println("[" + Main.INSTANCE.getDescription().getPrefix() + "] Max backups: " + maxBackups);
        File[] saves = Main.path.toFile().listFiles();
        System.out.println("[" + Main.INSTANCE.getDescription().getPrefix() + "] Backups en tout: " + saves.length);
        if(saves.length > maxBackups) {
            int numbers = saves.length - maxBackups;
            System.out.println("[" + Main.INSTANCE.getDescription().getPrefix() + "] Nombre de backups à supprimer: " + numbers);
            for(int i = 0; i < numbers; i++) {
                long oldest = Long.MAX_VALUE;
                File fOldest = null;
                for(File f : saves) {
                    if(f.lastModified() < oldest) {
                        oldest = f.lastModified();
                        fOldest = f;
                    }
                }
                if(fOldest != null) {
                    System.out.println("[" + Main.INSTANCE.getDescription().getPrefix() + "] Backup à supprimer: " + fOldest.getName());
                    deleteRecursively(fOldest);
                }
                saves = Main.path.toFile().listFiles();
            }
            if(numbers > 1) {
                System.out.println("[" + Main.INSTANCE.getDescription().getPrefix() + "] Les " + numbers + " backups les plus anciens ont été supprimés.");
            }else {
                System.out.println("[" + Main.INSTANCE.getDescription().getPrefix() + "] Le backup le plus ancien a été supprimé.");
            }
        }
    }

    private void deleteRecursively(File folder) {
        if(folder.isDirectory()) {
            for(String str : folder.list()) {
                deleteRecursively(new File(folder, str));
            }
        }
        try {
            Files.delete(folder.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToZip(File file, ZipOutputStream zos) throws IOException {
        BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
        ZipEntry entry = new ZipEntry(file.getAbsolutePath().replace(Main.root.toString(), ""));
        zos.putNextEntry(entry);
        int length;
        while((length = reader.read(BUFFER)) >= 0) {
            zos.write(BUFFER, 0, length);
        }
        zos.closeEntry();
        reader.close();
    }


}
