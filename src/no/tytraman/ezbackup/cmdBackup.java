package no.tytraman.ezbackup;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.text.SimpleDateFormat;

public class cmdBackup implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.isOp()) {
            if(args.length > 0) {
                switch(args[0].toLowerCase()) {
                    case "now":
                        sender.sendMessage(ChatColor.GOLD + "[" + Main.INSTANCE.getDescription().getPrefix() + "] Lancement d'un backup...");
                        new CopyRunnable(sender).runTaskAsynchronously(Main.INSTANCE);
                        break;
                    case "number":
                        int number = Main.path.toFile().list().length;
                        sender.sendMessage(ChatColor.GOLD + "[" +Main.INSTANCE.getDescription().getPrefix() + "] Il y a actuellement " + number + " backup" +
                                (number > 1 ? "s" : "") + ".");
                        break;
                    case "maxbackups":
                        sender.sendMessage(ChatColor.GOLD + "[" + Main.INSTANCE.getDescription().getPrefix() + "] Le nombre de backups max est de " +
                                Main.INSTANCE.getConfig().getInt("max-backups") + ".");
                        break;
                    case "lastbackup":
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                long last = 0L;
                                File latest = null;
                                for(File f : Main.path.toFile().listFiles()) {
                                    if(f.lastModified() > last) {
                                        last = f.lastModified();
                                        latest = f;
                                    }
                                }
                                if(latest != null) {
                                    long time = latest.lastModified();
                                    long now = System.currentTimeMillis();
                                    long diff = now - time;
                                    int x = (int) (diff / 1000);
                                    int seconds = x % 60;
                                    x /= 60;
                                    int minutes = x % 60;
                                    x /= 60;
                                    String remain = String.format("%02dh%02dm%02ds", x, minutes, seconds);
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                    sender.sendMessage(ChatColor.GOLD + "[" + Main.INSTANCE.getDescription().getPrefix() + "] Le dernier backup a été fait le " +
                                            sdf.format(time) + " (il y a " + remain +").");
                                }else {
                                    sender.sendMessage(ChatColor.RED + "[" + Main.INSTANCE.getDescription().getPrefix() + "] Il n'y a pas de backup.");
                                }
                            }
                        }.runTaskAsynchronously(Main.INSTANCE);
                        break;
                    default:
                        sender.sendMessage(ChatColor.RED + "[" + Main.INSTANCE.getDescription().getPrefix() + "] Argument inconnu.");
                        break;
                }
            }else {
                sender.sendMessage(ChatColor.RED + "[" + Main.INSTANCE.getDescription().getPrefix() + "] Argument manquant.");
            }
        }else {
            sender.sendMessage(ChatColor.RED + "Tu n'as pas l'autorisation pour faire cette commande.");
        }
        return true;
    }
}
