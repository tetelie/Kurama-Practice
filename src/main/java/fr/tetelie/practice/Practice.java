package fr.tetelie.practice;

import co.aikar.idb.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.tetelie.practice.arena.ArenaManager;
import fr.tetelie.practice.command.ArenaCommand;
import fr.tetelie.practice.command.PracticeCommand;
import fr.tetelie.practice.event.ClickEvent;
import fr.tetelie.practice.event.InteractEvent;
import fr.tetelie.practice.event.JoinEvent;
import fr.tetelie.practice.event.QuitEvent;
import fr.tetelie.practice.gui.Gui;
import fr.tetelie.practice.gui.guis.FightGui;
import fr.tetelie.practice.inventory.FightInventory;
import fr.tetelie.practice.inventory.Kit;
import fr.tetelie.practice.inventory.inventories.QueueInventory;
import fr.tetelie.practice.inventory.inventories.SpawnInventory;
import fr.tetelie.practice.ladder.Ladder;
import fr.tetelie.practice.ladder.ladders.NoDebuff;
import fr.tetelie.practice.mysql.PracticeDB;
import fr.tetelie.practice.fight.FightManager;
import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.util.LocationHelper;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Ladder> ladders = Arrays.asList(new NoDebuff());

    public Map<String, FightManager> fight = new HashMap<>();

    // Locations
    public LocationHelper spawn = new LocationHelper("spawn");

    // Kits
    public Kit spawnKit = new SpawnInventory();
    public  Kit queueKit = new QueueInventory();

    // Guis
    public Gui fightGui = new FightGui();

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
        registerThread();
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
    }

    private void registerEvent() {
        Arrays.asList(
                new JoinEvent(),
                new QuitEvent(),
                new InteractEvent(),
                new ClickEvent()
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

}
