package fr.tetelie.practice;

import co.aikar.idb.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.tetelie.practice.command.PracticeCommand;
import fr.tetelie.practice.event.JoinEvent;
import fr.tetelie.practice.event.QuitEvent;
import fr.tetelie.practice.inventory.Kit;
import fr.tetelie.practice.inventory.inventories.SpawnInventory;
import fr.tetelie.practice.mysql.PracticeDB;
import fr.tetelie.practice.util.LocationHelper;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public @Getter class Practice extends JavaPlugin {

    @Getter static Practice instance;

    public Connection connection;
    public String prefix;
    public PracticeDB practiceDB = new PracticeDB();

    public File locationFile;
    public YamlConfiguration locationConfig;

    private String configPath;

    // Locations
    public LocationHelper spawn = new LocationHelper("spawn");

    // Kits
    public Kit spawnKit = new SpawnInventory();

    @Override
    public void onEnable() {
        instance = this;
        prefix = "[" + this.getName() + "] ";
        registerResource();
        registerEvent();
        registerCommand();
        registerFile();
        registerLocation();
        // Database start
        setupHikariCP();
        setupDatabase();
        // Database end
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onDisable() {
        // save locations
        LocationHelper.getAll().forEach(locationHelper -> locationHelper.save());
        try {
            locationConfig.save(locationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerResource() {
        configPath = getDataFolder() + "\\hikari.properties";
        saveResource("hikari.properties", false);

        saveResource("locations.yml", false);
    }

    private void registerCommand() {
        getCommand("practice").setExecutor(new PracticeCommand());
    }

    private void registerEvent() {
        Arrays.asList(
                new JoinEvent(),
                new QuitEvent()
        ).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }


    private void registerLocation() {
        for (LocationHelper locationHelper : LocationHelper.getAll()) {
            String message = locationHelper.load() ? prefix + "§eThe location §6§r" + locationHelper.getName() + " §r§eis successfully registered!" : prefix + "§cThe location §4§r" + locationHelper.getName() + " §r§cis not registered!";
            this.getServer().getConsoleSender().sendMessage(message);
        }
    }

    private void registerFile() {
        locationFile = new File(getDataFolder() + "\\locations.yml");
        locationConfig = YamlConfiguration.loadConfiguration(locationFile);
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
