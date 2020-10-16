package no.tytraman.ezbackup;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class cmdTab implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>();
        if(args.length == 1) {
            for(String str : new String[]{"now", "number", "maxbackups", "lastbackup"}) {
                if(str.startsWith(args[0].toLowerCase())) {
                    list.add(str);
                }
            }
        }
        Collections.sort(list);
        return list;
    }
}
