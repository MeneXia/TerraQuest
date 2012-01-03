package me.menexia.terraquest;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import me.menexia.terraquest.commands.AcceptCommand;
import me.menexia.terraquest.commands.QuestCommand;
import me.mini.Mini;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TerraQuest extends JavaPlugin {
	public Map<Player, String> inputWait = new HashMap<Player, String>();
	public Map<Player, Integer> idCheck = new HashMap<Player, Integer>();
	public Set<Integer> noRepeat = new HashSet<Integer>();
	public final Logger logger = Logger.getLogger("Minecraft");
	public Mini database;
	private File directory;
	private AcceptCommand ac;
	private QuestCommand qc;

	@Override
	public void onDisable() {
		this.logger.info("[TerraQuest] version " + this.getDescription().getVersion() + " is now disabled!");
	}
	
	@Override
	public void onEnable() {
		directory = getDataFolder();
		database = new Mini(directory.getPath(), "quests.mini");
		PluginManager pm = this.getServer().getPluginManager();
		
		ac = new AcceptCommand(this);
		qc = new QuestCommand(this);
		
		getCommand("accept").setExecutor(ac);
		getCommand("quest").setExecutor(qc);
		
		pm.registerEvent(Event.Type.PLAYER_CHAT, new TQPlayerListener(this), Event.Priority.Normal, this);
		this.logger.info("[TerraQuest] version " + this.getDescription().getVersion() + " is now enabled!" );
	}
	
	public boolean inWaiting(Player player) {
		return inputWait.containsKey(player);
	}
	
	public String getStage(Player player) {
		return inputWait.get(player);
	}
	
	public void endCommand(Player player) {
		inputWait.remove(player);
		idCheck.remove(player);
	}

}
