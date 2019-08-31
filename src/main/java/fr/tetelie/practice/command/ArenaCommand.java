package fr.tetelie.practice.command;

import fr.tetelie.practice.arena.ArenaManager;
import fr.tetelie.practice.arena.ArenaType;
import net.minecraft.util.org.apache.commons.lang3.EnumUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommand implements CommandExecutor {

    String invalidArguement = "§cInvalid argument!";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(args.length == 0)
        {
            sender.sendMessage(" ");
            sender.sendMessage("§6Correct Usage§7: /arena");
            sender.sendMessage("    §elist");
            sender.sendMessage("    §ecreate§7 <arenaType> <name>");
            sender.sendMessage("    §esetspawn<1/2>§7 <name>");
        }else if(args.length == 1)
        {
            if(args[0].equalsIgnoreCase("list"))
            {
                sender.sendMessage(" ");
                sender.sendMessage("§6Arena List§7:");
                ArenaManager.getAll().forEach(arenaManager -> sender.sendMessage("§7- §e" + arenaManager.getName() + " §7-> §e" + arenaManager.getArenaType().toString()));
            }
            else{
                sender.sendMessage(invalidArguement);
            }
        }else if(args.length == 2)
        {
            if(args[0].equalsIgnoreCase("setspawn1") || args[0].equalsIgnoreCase("setspawn2"))
            {
                if (ArenaManager.getArena(args[1]) != null) {
                    ArenaManager arenaManager = ArenaManager.getArena(args[1]);
                    Player player = (Player) sender;
                    if (args[0].equalsIgnoreCase("setspawn1")) {
                        arenaManager.setLoc1(player.getLocation());
                        sender.sendMessage("§6you have set the spawn §e1 §6of arena §e" + arenaManager.getName());
                    } else if (args[0].equalsIgnoreCase("setspawn2")) {
                        arenaManager.setLoc2(player.getLocation());
                        sender.sendMessage("§6you have set the spawn §e2 §6of arena §e" + arenaManager.getName());
                    }
                }else{
                    sender.sendMessage("§6This arena does'nt exist!");
                }
            } else{
                sender.sendMessage(invalidArguement);
            }
        }else  if(args.length == 3)
        {
            if(args[0].equalsIgnoreCase("create"))
            {
                if(EnumUtils.isValidEnum(ArenaType.class, args[1].toUpperCase()))
                {
                    if(ArenaManager.getArena(args[2]) == null)
                    {
                        new ArenaManager(args[2], ArenaType.valueOf(args[1].toUpperCase()));
                        sender.sendMessage("§6You have create the "+args[1]+" arena §e" + args[2]);
                    }else{
                        sender.sendMessage("§6This arena does'nt exist!");
                    }
                }else{
                    sender.sendMessage("§6This type doesn't exist!");
                }
            }else{
                sender.sendMessage(invalidArguement);
            }
        }
        return false;
    }
}
