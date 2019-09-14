package fr.tetelie.practice.party;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.player.PlayerSatus;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PartyManager {

    private UUID leader;
    private List<UUID> members = new ArrayList<>();

    public PartyManager(PlayerManager leaderm, Player leader)
    {
        this.leader = leader.getUniqueId();
        leaderm.setPlayerSatus(PlayerSatus.PARTY);
        leaderm.sendKit(Practice.getInstance().partyLeaderKit);

        Practice.getInstance().party.put(this.leader, this);
    }

        public void destroy()
        {
            Practice.getInstance().party.remove(leader);
        }

}
