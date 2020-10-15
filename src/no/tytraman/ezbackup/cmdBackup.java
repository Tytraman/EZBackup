package no.tytraman.ezbackup;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
                    default:
                        sender.sendMessage(ChatColor.RED + "Argument inconnu.");
                        break;
                }
            }else {
                sender.sendMessage(ChatColor.RED + "Argument manquant.");
            }
        }else {
            sender.sendMessage(ChatColor.RED + "Tu n'as pas l'autorisation pour faire cette commande.");
        }
        return true;
    }
}
