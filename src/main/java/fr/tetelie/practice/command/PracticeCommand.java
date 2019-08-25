package fr.tetelie.practice.command;

import fr.tetelie.practice.util.LocationHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PracticeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(args.length == 0)
        {
            sender.sendMessage(" ");
            sender.sendMessage("§6Correct Usage§7: /practice");
            sender.sendMessage( "   §esetlocation §7<spawn:soon:soon>");
        }else if(args.length == 2)
        {
            if(args[0].equalsIgnoreCase(("setlocation")))
            {
                if(LocationHelper.getLocationHelper(args[1]) != null)
                {
                    Player player = (Player) sender;
                    LocationHelper.getLocationHelper(args[1]).setLocation(player.getLocation());
                    sender.sendMessage("§eYou have successfully set the location §6" + args[1] + "§7.");
                }else{
                    sender.sendMessage("§cSorry, but this location does not exist!");
                }
            }
        }
        return false;
    }
}
