package fr.tetelie.practice.fightpass.palliate.palliates.free;

import fr.tetelie.practice.fightpass.FightPassType;
import fr.tetelie.practice.fightpass.palliate.Palliate;

public class FreePalliate1 extends Palliate {

    @Override
    public int number() {
        return 1;
    }

    @Override
    public void earn() {

    }

    @Override
    public String[] reward_lore() {
        return new String[]{"Red Opponent tag color"};
    }

    @Override
    public FightPassType type() {
        return FightPassType.FREE;
    }

    @Override
    public String reward_name() {
        return "Opponent tag color";
    }
}
