package fr.tetelie.practice.fight;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.match.MatchManager;
import fr.tetelie.practice.player.PlayerManager;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class FightManager {

    private UUID normalQueuePlayer;
    private ArrayList<PlayerManager> rankedQueuePlayer = new ArrayList<>();
    private Map<FightType, List<MatchManager>> fightPlayer;

    public FightManager(){
        fightPlayer = new HashMap<>();

        fightPlayer.put(FightType.NORMAL, new ArrayList<>());
        fightPlayer.put(FightType.COMPETITIVE, new ArrayList<>());
    }

    public int getQueue(FightType fightType)
    {
        if(fightType == FightType.NORMAL) {
            return normalQueuePlayer == null ? 0 : 1;
        }else{
            return getRankedQueuePlayer() == null ? 0 : getRankedQueuePlayer().size();
        }
    }


    public int getFight(FightType fightType)
    {
        if(fightPlayer.get(fightType) == null) return 0;
        return fightPlayer.get(fightType).size()*2;
    }

    public ArrayList<PlayerManager> getRankedQueuePlayer()
    {
        return rankedQueuePlayer;
    }

}
