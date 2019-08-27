package fr.tetelie.practice.fight;

import fr.tetelie.practice.match.MatchManager;
import fr.tetelie.practice.match.MatchType;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter @Setter
public class FightManager {

    private Map<MatchType, UUID> queuePlayer;
    private Map<MatchType, List<MatchManager>> fightPlayer;

    public FightManager(){
        queuePlayer = new HashMap<>();
        fightPlayer = new HashMap<>();
    }

    public int getQueue(MatchType matchType)
    {
        return queuePlayer.get(matchType) == null ? 0 : 1;
    }

    public int getFight(MatchType matchType)
    {
        if(fightPlayer.get(matchType) == null) return 0;
        return fightPlayer.get(matchType).size()*2;
    }

}
