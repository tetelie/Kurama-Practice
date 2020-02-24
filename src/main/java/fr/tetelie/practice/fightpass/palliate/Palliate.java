package fr.tetelie.practice.fightpass.palliate;

import fr.tetelie.practice.fightpass.FightPassType;

public abstract class Palliate {

    public abstract int number();
    public abstract void earn();
    public abstract String[] reward_lore();
    public abstract String reward_name();
    public abstract FightPassType type();

}
