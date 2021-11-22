package bct.loadupstudios.ScaleLimits;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import java.util.logging.Level;


public class MyListener implements Listener
{
	boolean eventRunning = false; //This is to cancel the event running multiple times
	private FileConfiguration config;
	int currentWorldLimit, currentNetherLimit, currentEndLimit = 10;
	int maxWorldLimit, maxNetherLimit, maxEndLimit =  100;
	int maxWorldPerPlayer, maxNetherPerPlayer, maxEndPerPlayer = 10;
	String worldName = "";
	String netherName = "";
	String endName = "";
	boolean enforceWorld = false;
	boolean enforceNether = false;
	boolean enforceEnd = false;
	java.util.logging.Logger logger;
	
	MyListener(FileConfiguration conf, java.util.logging.Logger logg)
	{
		config = conf;
		maxWorldLimit = config.getInt("maxWorldMonsters");
		maxNetherLimit = config.getInt("maxNetherMonsters");
		maxEndLimit = config.getInt("maxEndMonsters");
		maxWorldPerPlayer = config.getInt("maxWorldPerPlayer");
		maxNetherPerPlayer = config.getInt("maxNetherPerPlayer");
		maxEndPerPlayer = config.getInt("maxEndPerPlayer");
		worldName = config.getString("worldname");
		netherName = config.getString("netherworldname");
		endName = config.getString("endworldname");
		if(config.getString("enforceworld").compareTo("true") == 0)
		{
			enforceWorld = true;
		}
		if(config.getString("enforcenether").compareTo("true") == 0)
		{
			enforceNether = true;
		}
		if(config.getString("enforceend").compareTo("true") == 0)
		{
			enforceEnd = true;
		}
		logger = logg;
	}
	
	public void reload(FileConfiguration conf)
	{
		config = conf;
		maxWorldLimit = config.getInt("maxWorldMonsters");
		maxNetherLimit = config.getInt("maxNetherMonsters");
		maxEndLimit = config.getInt("maxEndMonsters");
		maxWorldPerPlayer = config.getInt("maxWorldPerPlayer");
		maxNetherPerPlayer = config.getInt("maxNetherPerPlayer");
		maxEndPerPlayer = config.getInt("maxEndPerPlayer");
		worldName = config.getString("worldname");
		netherName = config.getString("netherworldname");
		endName = config.getString("endworldname");
		
		if(config.getString("enforceworld").compareTo("true") == 0)
		{
			enforceWorld = true;
		}
		if(config.getString("enforcenether").compareTo("true") == 0)
		{
			enforceNether = true;
		}
		if(config.getString("enforceend").compareTo("true") == 0)
		{
			enforceEnd = true;
		}
	}
	/*@EventHandler
    public void onPlayerClick(EntityDamageByEntityEvent event)
    {
		logger.log(Level.INFO, "Interact!");
    }*/
	
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
		currentWorldLimit = maxWorldLimit/Bukkit.getOnlinePlayers().size();
		currentNetherLimit = maxNetherLimit/Bukkit.getOnlinePlayers().size();
		currentEndLimit = maxEndLimit/Bukkit.getOnlinePlayers().size();
		
		//logger.log(Level.INFO, "Join Change Before Max: " + mobLimitPerPlayer);
		
		if(currentWorldLimit > maxWorldPerPlayer)
		{
			currentWorldLimit = maxWorldPerPlayer;
		}
		if(currentNetherLimit > maxNetherPerPlayer)
		{
			currentNetherLimit = maxNetherPerPlayer;
		}
		if(currentEndLimit > maxEndPerPlayer)
		{
			currentEndLimit = maxEndPerPlayer;
		}
		if(enforceWorld)
		{
			Bukkit.getWorld(worldName).setMonsterSpawnLimit(currentWorldLimit);
		}
		if(enforceNether)
		{
			Bukkit.getWorld(netherName).setMonsterSpawnLimit(currentNetherLimit);
		}
		if(enforceEnd)
		{
			Bukkit.getWorld(endName).setMonsterSpawnLimit(currentEndLimit);
		}
		//logger.log(Level.INFO, "Mobs Per Player: " + Bukkit.getWorld(worldName).getMonsterSpawnLimit());
		//logger.log(Level.INFO, "Join Change Actual (N): " + Bukkit.getWorld(worldName + "_nether").getMonsterSpawnLimit());
    }
	
	@EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
		//mobLimitPerPlayer = globalLimit/Bukkit.getOnlinePlayers().size();
		//logger.log(Level.INFO, "Leave Change Before Max: " + mobLimitPerPlayer);
		
		currentWorldLimit = maxWorldLimit/Bukkit.getOnlinePlayers().size();
		currentNetherLimit = maxNetherLimit/Bukkit.getOnlinePlayers().size();
		currentEndLimit = maxEndLimit/Bukkit.getOnlinePlayers().size();
		
		if(currentWorldLimit > maxWorldPerPlayer)
		{
			currentWorldLimit = maxWorldPerPlayer;
		}
		if(currentNetherLimit > maxNetherPerPlayer)
		{
			currentNetherLimit = maxNetherPerPlayer;
		}
		if(currentEndLimit > maxEndPerPlayer)
		{
			currentEndLimit = maxEndPerPlayer;
		}
		if(enforceWorld)
		{
			Bukkit.getWorld(worldName).setMonsterSpawnLimit(currentWorldLimit);
		}
		if(enforceNether)
		{
			Bukkit.getWorld(netherName).setMonsterSpawnLimit(currentNetherLimit);
		}
		if(enforceEnd)
		{
			Bukkit.getWorld(endName).setMonsterSpawnLimit(currentEndLimit);
		}
		//logger.log(Level.INFO, "Mobs Per Player: " + Bukkit.getWorld(worldName).getMonsterSpawnLimit());
		//logger.log(Level.INFO, "Leave Change Actual (N): " + Bukkit.getWorld(worldName + "_nether").getMonsterSpawnLimit());
    }
	
	public String getLimits()
	{
		return "ScaleLimits:\n"
				+ "World: " + enforceWorld + "\n"
				+ "Nether: " + enforceNether + "\n"
				+ "End: " + enforceEnd + "\n"
				+ "Limits (W): " +  currentWorldLimit + "\n"
				+ "Limits (N): " +  currentNetherLimit + "\n"
				+ "Limits (E): " +  currentEndLimit + "\n";
	}
}
