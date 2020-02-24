package fr.tetelie.practice.fightpass.palliate.palliates.free;

import fr.tetelie.practice.fightpass.FightPassType;
import fr.tetelie.practice.fightpass.palliate.Palliate;

public class FreePalliate2 extends Palliate {

    @Override
    public int number() {
        return 1;
    }

    @Override
    public String reward_name() {
        return "Death Effect";
    }

    @Override
    public void earn() {

    }

    @Override
    public String[] reward_lore() {
        return new String[]{"Smoke Death effect"};
    }

    @Override
    public FightPassType type() {
        return FightPassType.FREE;
    }
}
