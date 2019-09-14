package fr.tetelie.practice;

import co.aikar.idb.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.tetelie.practice.arena.ArenaManager;
import fr.tetelie.practice.command.*;
import fr.tetelie.practice.event.*;
import fr.tetelie.practice.gui.Gui;
import fr.tetelie.practice.gui.GuiMultiPageManager;
import fr.tetelie.practice.gui.guis.DuelGui;
import fr.tetelie.practice.gui.guis.FightGui;
import fr.tetelie.practice.gui.guis.PanelGui;
import fr.tetelie.practice.inventory.FightInventory;
import fr.tetelie.practice.inventory.Kit;
import fr.tetelie.practice.inventory.MatchPreviewInventory;
import fr.tetelie.practice.inventory.inventories.PartyLeaderInventory;
import fr.tetelie.practice.inventory.inventories.QueueInventory;
import fr.tetelie.practice.inventory.inventories.RespawnInventory;
import fr.tetelie.practice.inventory.inventories.SpawnInventory;
import fr.tetelie.practice.ladder.Ladder;
import fr.tetelie.practice.ladder.ladders.Debuff;
import fr.tetelie.practice.ladder.ladders.NoDebuff;
import fr.tetelie.practice.mysql.PracticeDB;
import fr.tetelie.practice.fight.FightManager;
import fr.tetelie.practice.party.PartyManager;
import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.util.ItemBuilder;
import fr.tetelie.practice.util.LocationHelper;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public @Getter class Practice extends JavaPlugin {

    @Getter static Practice instance;

    public Connection connection;
    public String prefix;
    public PracticeDB practiceDB = new PracticeDB();

    public File locationFile;
    public YamlConfiguration locationConfig;

    public File arenaFile;
    public YamlConfiguration arenaConfig;

    private String configPath;

    public List<Ladder> ladders = Arrays.asList(new NoDebuff(), new Debuff());

    public Map<String, FightManager> fight = new HashMap<>();
    public Map<UUID, MatchPreviewInventory> matchPreviewInventoryMap = new HashMap<>();
    public Map<UUID, PartyManager> party = new HashMap<>();

    // Locations
    public LocationHelper spawn = new LocationHelper("spawn");

    // Kits
    public Kit spawnKit = new SpawnInventory();
    public Kit respawnKit = new RespawnInventory();
    public Kit queueKit = new QueueInventory();
    public Kit partyLeaderKit = new PartyLeaderInventory();

    // Guis
    public Gui fightGui = new FightGui();
    public Gui panelGui = new PanelGui();
    public Gui duelGui = new DuelGui();

    // Multiple Gui
    public GuiMultiPageManager spectateGui = new GuiMultiPageManager(new ArrayList<>(), "§6Spectate", new ItemBuilder(Material.COMPASS).setName("§6Spectate current fight(s)").toItemStack(), (byte)15);
    public GuiMultiPageManager eventGui = new GuiMultiPageManager(new ArrayList<>(), "§6Event", new ItemBuilder(Material.NETHER_STAR).setName("§6List of current event(s)").toItemStack(), (byte)0);

    //Thread
    public Thread fightInventory;

    // Inventories
    public Inventory normalFight;
    public Inventory competitiveFight;

    @Override
    public void onEnable() {
        instance = this;
        prefix = "[" + this.getName() + "] ";
        registerResource();
        // Database start
        setupHikariCP();
        setupDatabase();
        // Database end
        registerEvent();
        registerCommand();
        registerFile();
        registerLocation();
        registerArena();
        registerThread();
        sendCreditMessage();
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onDisable() {
        // save locations
        LocationHelper.getAll().forEach(locationHelper -> locationHelper.save());
        // save arena
        ArenaManager.getAll().forEach(arenaManager -> arenaManager.save());
        try {
            locationConfig.save(locationFile);
            arenaConfig.save(arenaFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // stop thread
        fightInventory.stop();
    }

    private void registerResource() {
        configPath = getDataFolder() + "/hikari.properties";
        saveResource("hikari.properties", false);

        saveResource("locations.yml", false);

        saveResource("arena.yml", false);
    }

    private void registerCommand() {
        getCommand("practice").setExecutor(new PracticeCommand());
        getCommand("arena").setExecutor(new ArenaCommand());
        getCommand("inventory").setExecutor(new InventoryCommand());
        getCommand("build").setExecutor(new BuildCommand());
        getCommand("duel").setExecutor(new DuelCommand());
        getCommand("accept").setExecutor(new AcceptCommand());
    }

    private void registerEvent() {
        Arrays.asList(
                new JoinEvent(),
                new QuitEvent(),
                new InteractEvent(),
                new DeathEvent(),
                new DamageEvent(),
                new FoodEvent(),
                new DropEvent(),
                new PickupEvent(),
                new ClickEvent(),
                new BreakBlockEvent(),
                new PlaceBlockEvent()
        ).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }


    private void registerLocation() {
        for (LocationHelper locationHelper : LocationHelper.getAll()) {
            String message = locationHelper.load() ? prefix + "§eThe location §6§r" + locationHelper.getName() + " §r§eis successfully registered!" : prefix + "§cThe location §4§r" + locationHelper.getName() + " §r§cis not registered!";
            this.getServer().getConsoleSender().sendMessage(message);
        }
    }

    private void registerFile() {
        locationFile = new File(getDataFolder() + "/locations.yml");
        arenaFile = new File(getDataFolder() + "/arena.yml");
        locationConfig = YamlConfiguration.loadConfiguration(locationFile);
        arenaConfig = YamlConfiguration.loadConfiguration(arenaFile);
    }

    private void registerArena()
    {
        final ConfigurationSection cs = arenaConfig.getConfigurationSection("arena");
        if (cs != null) {
            for (final String s : cs.getKeys(false)) {
                if (s != null) {
                    final ConfigurationSection cs2 = cs.getConfigurationSection(s);
                    if (cs2 == null) {
                        continue;
                    }

                    ArenaManager arena = new ArenaManager(cs2.getName());
                    arena.load();


                    this.getServer().getConsoleSender().sendMessage(prefix + ChatColor.GOLD + "Registered Arena "+ChatColor.GRAY+"-> Name "+ChatColor.YELLOW+arena.getName() +ChatColor.GRAY +" -> Type "+ChatColor.YELLOW+arena.getArenaType().toString());
                }
            }
        }
    }

    private void registerThread()
    {
        fightInventory = new Thread(new FightInventory());
        fightInventory.start();
    }

    private void setupDatabase() {
        if (connection != null) {
            practiceDB.createPlayerManagerTable();
        } else {
            System.out.println(prefix + "WARNING enter valid database information (" + configPath + ") \n You will not be able to access many features");
        }
    }

    private void setupHikariCP() {
        try {
            HikariConfig config = new HikariConfig(configPath);
            HikariDataSource ds = new HikariDataSource(config);
            String passwd = config.getDataSourceProperties().getProperty("password") == null ? "" : config.getDataSourceProperties().getProperty("password");
            Database db = BukkitDB.createHikariDatabase(this, config.getDataSourceProperties().getProperty("user"), passwd, config.getDataSourceProperties().getProperty("databaseName"), config.getDataSourceProperties().getProperty("serverName") + ":" + config.getDataSourceProperties().getProperty("portNumber"));
            DB.setGlobalDatabase(db);
            connection = ds.getConnection();
        } catch (SQLException e) {
            System.out.println(prefix + "Error could not connect to SQL database.");
            e.printStackTrace();
        }
        System.out.println(prefix + "Successfully connected to the SQL database.");
    }

    private void sendCreditMessage()
    {
        String[] message = new String[]{
                ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "-----------------------------",
                " ",
                ChatColor.GOLD + "Rena Practice Enable ^-^",
                " ",
                ChatColor.GOLD + "Author " + ChatColor.GRAY + "-> " + ChatColor.YELLOW + "tetelie *",
                " ",
                ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "-----------------------------"
        };
        this.getServer().getConsoleSender().sendMessage(message);
    }

}
