package fr.tetelie.practice.command;

import fr.tetelie.practice.duel.DuelManager;
import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.player.PlayerSatus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AcceptCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        if(args.length == 2)
        {
            Player player = (Player) sender;
            PlayerManager playerManager = PlayerManager.getPlayerManagers().get(player.getUniqueId());
            if(playerManager.getPlayerSatus() == PlayerSatus.FREE) {
                switch (args[0]) {
                    case "versus_duel":
                        if (DuelManager.getDuelByReciever(player.getUniqueId()) != null) {
                            if (player.getUniqueId() == DuelManager.getDuelBySender(DuelManager.getDuelByReciever(player.getUniqueId()).getSender()).getReciever()) {
                                if(PlayerManager.getPlayerManagers().get(DuelManager.getDuelByReciever(player.getUniqueId()).getSender()).getPlayerSatus() == PlayerSatus.FREE)
                                {
                                    DuelManager.getDuelByReciever(player.getUniqueId()).create();
                                }else{
                                    sender.sendMessage("§cThis player is currently busy!");
                                }
                            } else {
                                sender.sendMessage("§cThis duel request is no longer valid!");
                            }
                        } else {
                            sender.sendMessage("§cThis duel has expired or the sender is offline!");
                        }
                        break;
                    case "party_duel":

                        break;
                    default:
                        sender.sendMessage("§cInvalid type!");
                        break;
                }
            }
        }
        return false;
    }
}
