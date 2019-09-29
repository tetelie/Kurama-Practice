package fr.tetelie.practice.player.settings;

import lombok.Getter;

public @Getter enum Settings {

    TIME(0, 3),
    PRIVATE_MESSAGE(1, 4),
    BELOW_NAME(2, 5),
    SCOREBOARD(3, 12),
    DUEL_REQUEST(4, 13),
    REMATCH_ITEM(5, 14),
    OPPONENT_TAG_COLOR(6, 21),
    EXP_BAR(7, 22),
    KILL_EFFECT(8, 23);

    private int id;
    private int slot;

    Settings(int id, int slot)
    {
        this.id = id;
        this.slot = slot;
    }

    public static Settings getSettingsBySlot(int slot)
    {
        for(Settings settings : Settings.values())
        {
            if(settings.slot == slot) return settings;
        }
        return null;
    }

    public static Settings getSettingsById(int id)
    {
        for(Settings settings : Settings.values())
        {
            if(settings.id == id) return settings;
        }
        return null;
    }

}
