package fr.tetelie.practice.fight;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.match.MatchManager;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class FightManager {

    private Map<FightType, UUID> queuePlayer;
    private Map<FightType, List<MatchManager>> fightPlayer;

    public FightManager(){
        queuePlayer = new HashMap<>();
        fightPlayer = new HashMap<>();

        fightPlayer.put(FightType.NORMAL, new ArrayList<>());
        fightPlayer.put(FightType.COMPETITIVE, new ArrayList<>());
    }

    public int getQueue(FightType fightType)
    {
        return queuePlayer.get(fightType) == null ? 0 : 1;
    }

    public int getFight(FightType fightType)
    {
        if(fightPlayer.get(fightType) == null) return 0;
        return fightPlayer.get(fightType).size()*2;
    }

}
