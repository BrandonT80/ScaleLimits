package bct.loadupstudios.ScaleLimits;

import java.io.File;
import java.io.FileWriter;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin implements Listener
{
	FileConfiguration config = this.getConfig();
	java.util.logging.Logger logger = this.getLogger();
	MyListener eventManager = new MyListener(config, logger);
	ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
	String folder = this.getDataFolder().getPath();
	File configFile = new File(folder,"config.yml");
	
	//MyListener eventManager = new MyListener(tagClass, config);
	@Override
    public void onEnable() 
	{
		//int startVariable = Bukkit.getMonsterSpawnLimit();
		saveDefaultConfig();
		logger.log(Level.INFO, "Starting ScaleLimitsPPM (Per Player Mobs)");
		checkConfig(config);
		getServer().getPluginManager().registerEvents(eventManager, this);
		
    }
    @Override
    public void onDisable() 
    {
    	//saveConfig();
    }
    
    @Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
    	if(command.getName().equalsIgnoreCase("scalelimits"))
		{
    		if(sender.hasPermission("scalelimits.check"))
			{
    			sender.sendMessage(eventManager.getLimits());
			}
		}
    	return true;
	}
    
    public boolean checkConfig(FileConfiguration conf)
    {
    	//logger.log(Level.INFO, "Checking Config");
    	if(conf.getString("configversion") != null && conf.getString("configversion").compareTo("2.0") != 0)
    	{
    		logger.log(Level.INFO, "Fixing Config...");
    		rewriteConfig(conf);
    		return false;
    	}
    	//logger.log(Level.INFO, "Config is proper");	
    	return true;
    }
    
    public void rewriteConfig(FileConfiguration conf)
	{
		try
		{
			String configVersion = "2.0";
			String maxWorldMonsters = "100";
			String maxWorldPerPlayer = "10";
			String maxNetherMonsters = "100";
			String maxNetherPerPlayer = "10";
			String maxEndMonsters = "100";
			String maxEndPerPlayer = "10";
			String enforceWorld = "false";
			String enforceNether = "false";
			String enforceEnd = "false";
			String worldName = "world";
			String netherWorldName = "world_nether";
			String endWorldName = "world_the_end";
			
			if(conf.contains("maxmonsters"))
			{
				maxWorldMonsters = maxNetherMonsters = maxEndMonsters = conf.getString("maxmonsters");
			}
			if(conf.contains("maxperplayer"))
			{
				maxWorldPerPlayer = maxNetherPerPlayer = maxEndPerPlayer = conf.getString("maxperplayer");
			}
			if(conf.contains("maxWorldMonsters"))
			{
				maxWorldMonsters = config.getString("maxWorldMonsters");
			}
			if(conf.contains("maxWorldPerPlayer"))
			{
				maxWorldPerPlayer = conf.getString("maxWorldPerPlayer");
			}
			if(conf.contains("maxNetherMonsters"))
			{
				maxNetherMonsters = config.getString("maxNetherMonsters");
			}
			if(conf.contains("maxNetherPerPlayer"))
			{
				maxNetherPerPlayer = conf.getString("maxNetherPerPlayer");
			}
			if(conf.contains("maxEndMonsters"))
			{
				maxEndMonsters = config.getString("maxEndMonsters");
			}
			if(conf.contains("maxEndPerPlayer"))
			{
				maxEndPerPlayer = conf.getString("maxEndPerPlayer");
			}
			if(conf.contains("enforceworld"))
			{
				enforceWorld = conf.getString("enforceworld");
			}
			if(conf.contains("enforceNether"))
			{
				enforceNether = conf.getString("enforcenether");
			}
			if(conf.contains("enforceEnd"))
			{
				enforceEnd = conf.getString("enforceend");
			}
			if(conf.contains("worldname"))
			{
				worldName = conf.getString("worldname");
			}
			if(conf.contains("netherworldname"))
			{
				netherWorldName = conf.getString("netherworldname");
			}
			if(conf.contains("endworldname"))
			{
				endWorldName = conf.getString("endworldname");
			}
			
			File tempFile2 = new File(folder,"tempFile2.yml");
			
			//Store Config Values
			
			//Load a fake config for purpose of modifications
			tempFile2.createNewFile();
			config.load(tempFile2);
			FileWriter writer = new FileWriter(configFile);
			
			writer.write("#Plugin only works with Paper as it modifies values for Paper's Per Player Mobs.\r\n");
			writer.write("configversion: " + configVersion + "\r\n");
			writer.write("maxWorldMonsters: " + maxWorldMonsters + "\r\n");
			writer.write("maxWorldPerPlayer: " + maxWorldPerPlayer + "\r\n");
			writer.write("maxNetherMonsters: " + maxNetherMonsters + "\r\n");
			writer.write("maxNetherPerPlayer: " + maxNetherPerPlayer + "\r\n");
			writer.write("maxEndMonsters: " + maxEndMonsters + "\r\n");
			writer.write("maxEndPerPlayer: " + maxEndPerPlayer + "\r\n");
			writer.write("enforceworld: " + enforceWorld + "\r\n");
			writer.write("enforcenether: " + enforceNether + "\r\n");
			writer.write("enforceend: " + enforceEnd + "\r\n");
			writer.write("worldname: " + worldName + "\r\n");
			writer.write("netherworldname: " + netherWorldName + "\r\n");
			writer.write("endworldname: " + endWorldName + "\r\n");
			
			writer.close();
			config.load(configFile);
			tempFile2.delete();
		}
		catch(Exception e)
		{
			logger.log(Level.INFO, "Failed to rewrite config file\n" + e);
		}
		
	}
}