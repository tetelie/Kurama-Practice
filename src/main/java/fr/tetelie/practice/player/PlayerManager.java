package fr.tetelie.practice.player;

import co.aikar.idb.DB;
import fr.tetelie.practice.Practice;
import fr.tetelie.practice.duel.DuelManager;
import fr.tetelie.practice.fight.FightManager;
import fr.tetelie.practice.gui.Gui;
import fr.tetelie.practice.historic.HistoricManager;
import fr.tetelie.practice.inventory.Kit;
import fr.tetelie.practice.fight.FightType;
import fr.tetelie.practice.match.MatchManager;
import fr.tetelie.practice.setting.Setting;
import fr.tetelie.practice.util.LocationHelper;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.sql.Array;
import java.sql.SQLException;
import java.util.*;

public @Getter @Setter class PlayerManager {

    static @Getter Map<UUID, PlayerManager> playerManagers;


    private UUID uuid;
    private String name;
    private int[] settings = new int[9];
    private int[] elos = new int[1];
    private int[] stats = new int[5]; // normal win|lose, competitive win|lose, exp
    private PlayerSatus playerSatus = PlayerSatus.FREE;
    private HistoricManager historic = new HistoricManager("§6§lHistoric §f(Right click)", "§eRena Team");
    private MatchManager currentFight;
    private UUID currentDuelPlayer;
    private String login;

    // Queue
    private String ladder;
    private FightType fightType;

    static
    {
        playerManagers = new HashMap<>();
    }

    public PlayerManager(UUID uuid, String name)
    {
        this.uuid = uuid;
        this.name = name;

        update();

        playerManagers.put(uuid, this);
    }

    private boolean exist()
    {
        return Practice.getInstance().practiceDB.existPlayerManager(uuid);
    }

    private void update(){
        if(!exist()) {
            login = Practice.getInstance().mediumDateFormatEN.format(new Date());
            Practice.getInstance().practiceDB.createPlayerManager(uuid, name, login); // insert new table
        }else{
            Practice.getInstance().practiceDB.updatePlayerManager(name, uuid); // update name in corresponding uuid table
            load(); // load all mysql data
        }
    }

    private void load(){
        try {
            login = DB.getFirstRow("SELECT login FROM player_manager WHERE name=?", name).getString("login");
            settings = getSplitValue(DB.getFirstRow("SELECT settings FROM player_manager WHERE name=?", name).getString("settings"), ":");
            stats = getSplitValue(DB.getFirstRow("SELECT stats FROM player_manager WHERE name=?", name).getString("stats"), ":");
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void save(){
        try{
            String settings = getStringValue(this.settings, ":");
            DB.executeUpdate("UPDATE player_manager SET settings=? WHERE name=?", settings, name);
            String stats = getStringValue(this.stats, ":");
            DB.executeUpdate("UPDATE player_manager SET stats=? WHERE name=?", stats, name);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void setSettings(int index, int value)
    {
        if(this.settings[index] != value) this.settings[index] = value;
    }

    private int[] getSplitValue(String string, String spliter)
    {
        String[] split = string.split(spliter);
        int[] board = new int[split.length];

        for(int i = 0; i <= split.length-1; i++)
        {
            board[i] = Integer.parseInt(split[i]);
        }
        return board;
    }

    private String getStringValue(int[] board, String spliter)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i <= board.length-1; i++)
        {
            stringBuilder.append(board[i]);
            if(i != board.length-1) stringBuilder.append(spliter);
        }
        return stringBuilder.toString();
    }

    public void sendKit(Kit kit)
    {
        Player player = Bukkit.getPlayer(uuid);
        player.getInventory().clear();
        player.getInventory().setContents(kit.content());
        player.getInventory().setArmorContents(kit.armor());
        player.updateInventory();
    }

    public void teleport(Player player, LocationHelper locationHelper)
    {
        if(locationHelper == null || locationHelper.getLocation() == null)
        {
            player.sendMessage("§cYou can't be teleported because this location is not set!");
        }else{
            player.teleport(locationHelper.getLocation());
        }
    }

    public void reset(Player player, GameMode gameMode) {
        player.getInventory().clear();
        player.getInventory().setHelmet(new ItemStack(Material.AIR));
        player.getInventory().setChestplate(new ItemStack(Material.AIR));
        player.getInventory().setLeggings(new ItemStack(Material.AIR));
        player.getInventory().setBoots(new ItemStack(Material.AIR));
        player.updateInventory();
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.setMaximumNoDamageTicks(20);
        player.setNoDamageTicks(20);
        player.setHealthScale(20.0);
        player.setMaxHealth(20.0);
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setFireTicks(0);
        player.setSaturation(10);
        player.setGameMode(gameMode);
        player.setAllowFlight(false);
        player.setFlying(false);
    }

    public void queue(String ladder, FightType fightType)
    {
        this.ladder = ladder;
        this.fightType = fightType;
        Practice.getInstance().fight.get(ladder).getQueuePlayer().put(fightType, uuid);
    }

    public void leaveQueue()
    {
        FightManager fightManager = Practice.getInstance().fight.get(ladder);
        if(fightManager != null && fightManager.getQueuePlayer().containsKey(fightType) && fightManager.getQueuePlayer().get(fightType) != null && fightManager.getQueuePlayer().get(fightType) == this.uuid)
        {
            fightManager.getQueuePlayer().put(fightType, null);
            this.ladder = null;
            this.fightType = null;
        }
    }

    public void removeDuel()
    {
        if(DuelManager.getDuelBySender(uuid) != null) DuelManager.getDuelBySender(uuid).destroy();
        if(DuelManager.getDuelByReciever(uuid) != null) DuelManager.getDuelByReciever(uuid).destroy();
    }

    public void removePreviewInventory()
    {
        if(Practice.getInstance().matchPreviewInventoryMap.containsKey(uuid)) Practice.getInstance().matchPreviewInventoryMap.get(uuid).destroy();
    }

    public void hideAll(Player player)
    {
        /*for(Player p : Bukkit.getServer().getOnlinePlayers())
        {
            player.hidePlayer(p);
        }*/
    }

    public void hideFromAll(Player player)
    {
        /*for(Player p : Bukkit.getServer().getOnlinePlayers())
        {
            p.hidePlayer(player);
        }*/
    }

    public void showAll(Player player)
    {
        /*for(Player p : Bukkit.getServer().getOnlinePlayers())
        {
            player.showPlayer(p);
        }*/
    }

    public void showFromAll(Player player)
    {
        /*for(Player p : Bukkit.getServer().getOnlinePlayers())
        {
            p.showPlayer(player);
        }*/
    }

    public ItemStack changeLore(ItemStack item, String... lore)
    {
        ItemMeta meta = item.getItemMeta();
        meta.getLore().clear();
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    // stats
    public Inventory getStatsGui()
    {
        String normal_win_chance = "-";
        if(stats[0] > 0)
        {
            if(stats[1] == 0)
            {
                normal_win_chance = "100%";
            }else {
                double rate = (double)stats[0]/((double)stats[0]+(double)stats[1]);
                normal_win_chance = rate*100 + "%";
            }
        }

        String competitive_win_chance = "-";
        if(stats[2] > 0)
        {
            if(stats[3] == 0)
            {
                competitive_win_chance = "100%";
            }else {
                if (stats[2] >= stats[3]) {
                    competitive_win_chance = (double)stats[2]/((double)stats[2]+(double)stats[3]) + "%";
                } else {
                    competitive_win_chance = "0%";
                }
            }
        }

        Inventory stats = Gui.clone(Practice.getInstance().statsGui);
        changeLore(stats.getItem(4), "§eExp§7: " + this.stats[4], "§eFirst login§7: " + login);
        changeLore(stats.getItem(10),
                "§eWin§7: " + this.stats[0],
                "§eLose§7: " + this.stats[1],
                "§eWin Chance§7: " + normal_win_chance
                );

        changeLore(stats.getItem(16),
                "§eWin§7: " + this.stats[2],
                "§eLose§7: " + this.stats[3],
                "§eWin Chance§7: " + competitive_win_chance
        );
        changeLore(stats.getItem(22), "§7soon...");
        return stats;
    }

    // settings
    public Inventory getSettingsGui()
    {
        Inventory settings = Gui.clone(Practice.getInstance().settingsGui);
        refreshSettingsLoreGui(settings);
        return settings;
    }

    public void refreshSettingLore(Inventory settingInv, int slot, int setting)
    {
        ItemStack item = settingInv.getItem(slot);
        ItemMeta meta = item.getItemMeta();
        meta.getLore().clear();
        List<String> lore = new ArrayList<>();
        lore.add("§7§m----------------------");
        lore.addAll(Arrays.asList(getSettingLore(setting)));
        lore.add("§7§m----------------------");
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    private void refreshSettingsLoreGui(Inventory settingsInv)
    {
        int setting = 0;
        for(Setting settings : Setting.all)
        {
            refreshSettingLore(settingsInv, settings.slot(), setting);
            setting++;
        }
    }

    private String[] getSettingLore(int id)
    {
        int value = this.settings[id];
        Setting setting = Setting.all[id];
        String[] lore = setting.values();
        lore[value] = "§e\u2192 " + setting.values()[value];
        return lore;
    }

    public void changeSettings(int setting, Player player)
    {
        int currentValue = this.settings[setting];
        int newValue = Setting.all[setting].values().length <= currentValue+1 ? 0 : this.settings[setting] + 1;
        this.settings[setting] = newValue;
        Setting.all[setting].change(player, newValue);
    }

    public void destroy()
    {
        leaveQueue();
        removeDuel();
        save(); // save all local data in database
        removePreviewInventory();
        playerManagers.remove(uuid);
    }

}
