package fr.tetelie.practice.match;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.arena.ArenaManager;
import fr.tetelie.practice.fight.FightType;
import fr.tetelie.practice.inventory.Kit;
import fr.tetelie.practice.ladder.Ladder;
import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.player.PlayerSatus;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
public class MatchManager {

    private UUID uuid1;
    private UUID uuid2;
    private @Setter MatchStatus matchStatus = MatchStatus.PLAYING;
    private FightType fightType;
    private ArenaManager arena;
    private Ladder ladder;

    public MatchManager(MatchType matchType, UUID uuid1, UUID uuid2, FightType fightType, String ladderDisplayName){
        this.uuid1 = uuid1;
        this.uuid2 = uuid2;
        this.fightType = fightType;
        Player player1 = Bukkit.getPlayer(uuid1);
        Player player2 = Bukkit.getPlayer(uuid2);
        PlayerManager playerManager1 = PlayerManager.getPlayerManagers().get(uuid1);
        PlayerManager playerManager2 = PlayerManager.getPlayerManagers().get(uuid2);
        Ladder ladder = Ladder.getLadder(ladderDisplayName);
        this.ladder = ladder;
        ArenaManager arena = ArenaManager.getRandomArena(ladder.arenaType());
        this.arena = arena;
        playerManager1.leaveQueue();
        Bukkit.broadcastMessage("Create new match: matchtype: " + matchType.toString() +" fighttype:"+fightType.toString()+" ladder:"+ladder.name()+" arena:" + arena.getName());
        playerManager1.setPlayerSatus(PlayerSatus.FIGHT);
        playerManager2.setPlayerSatus(PlayerSatus.FIGHT);
        player1.teleport(arena.getLoc1());
        player2.teleport(arena.getLoc2());
        if(ladder instanceof Kit)
        {
            Kit kit = (Kit) ladder;
            playerManager1.sendKit(kit);
            playerManager2.sendKit(kit);
        }
        Practice.getInstance().fight.get(ladderDisplayName).getFightPlayer().get(fightType).add(this);
        playerManager1.setCurrentFight(this);
        playerManager2.setCurrentFight(this);
    }

    public void end(MatchEndCause cause, Player player, PlayerManager playerManager)
    {
        UUID uuid = this.getUuid2() == player.getUniqueId() ? this.getUuid1() : this.getUuid2();
        Player player2 = Bukkit.getPlayer(uuid);
        PlayerManager playerManager2 = PlayerManager.getPlayerManagers().get(uuid);
        player2.getInventory().clear();
        this.setMatchStatus(MatchStatus.FINISHING);
        if(cause == MatchEndCause.KILL) {
            player.teleport(player2.getLocation());
            if(player.getKiller() != null) sendGlobalMessage("§6"+player.getName()+" has been slain by " + player.getKiller().getName(), player, player2);
        } else {
            player2.sendMessage("§6Your opponent has leave the server!");
        }
        Bukkit.getScheduler().runTaskLater(Practice.getInstance(), (Runnable) new Runnable() {
            public void run() {
                destroy();
                playerManager2.reset(player2);
                playerManager2.teleport(player2, Practice.getInstance().spawn);
                playerManager2.sendKit(Practice.getInstance().spawnKit);
                playerManager2.setPlayerSatus(PlayerSatus.FREE);
                if(cause == MatchEndCause.KILL)
                {
                    playerManager.reset(player);
                    playerManager.teleport(player, Practice.getInstance().spawn);
                    playerManager.sendKit(Practice.getInstance().spawnKit);
                    playerManager.setPlayerSatus(PlayerSatus.FREE);
                }
            }
        }, 60);
    }

    private void destroy()
    {
        if(Practice.getInstance().fight.get(ladder.displayName()).getFightPlayer().get(fightType).contains(this)) Practice.getInstance().fight.get(ladder.displayName()).getFightPlayer().get(fightType).remove(this);
    }

    public void sendGlobalMessage(String message, Player... players) { for(Player player : players) player.sendMessage(message); }

}
