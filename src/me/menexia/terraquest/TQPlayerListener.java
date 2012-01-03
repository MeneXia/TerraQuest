package me.menexia.terraquest;

import me.mini.Arguments;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

public class TQPlayerListener extends PlayerListener {
	public static TerraQuest plugin;
	public TQPlayerListener(TerraQuest instance) {
		plugin = instance;
	}
	
	Arguments questData = new Arguments("questdata");
	ChatColor w = ChatColor.WHITE;
	ChatColor a = ChatColor.AQUA;
	ChatColor g = ChatColor.GREEN;
	
	public void onPlayerChat(PlayerChatEvent event) {
		Player player = event.getPlayer();
		if (plugin.inWaiting(player)) {
			
			if (plugin.getStage(player).equalsIgnoreCase("questname")) {
				event.setCancelled(true);
				player.sendMessage(a + "Quest Name: " + w + event.getMessage());
				
				if (plugin.database.hasIndex("questdata")) {
					int nathalie = plugin.database.getArguments("questdata").getInteger("count");
					plugin.database.setArgument("questdata", "count", String.valueOf(nathalie++));
					plugin.database.update();
				} else {
					questData.setValue("count", String.valueOf(1));
					plugin.database.addIndex(questData.getKey(), questData);
					plugin.database.update();
				}
				int nathalie = plugin.database.getArguments("questdata").getInteger("count");
				Arguments entry = new Arguments(String.valueOf(nathalie));
				entry.setValue("questname", event.getMessage());
				entry.setValue("playername", player.getName());
				entry.setValue("id", String.valueOf(nathalie));
				entry.setValue("status", g + " - Free");
				plugin.database.addIndex(entry.getKey(), entry);
				plugin.database.update();
				plugin.idCheck.put(player, nathalie);
				plugin.inputWait.put(player, "objective");
				player.sendMessage(g + "Input the objectives of your quest:");
				
			} else if (plugin.getStage(player).equalsIgnoreCase("objective")) {
				event.setCancelled(true);
				player.sendMessage(a + "Objectives: " + w + event.getMessage());
				plugin.database.setArgument(String.valueOf(plugin.idCheck.get(player)), "objective", event.getMessage());
				plugin.database.update();
				plugin.inputWait.put(player, "reward");
				player.sendMessage(g + "Input the rewards of your quest:");
				
			} else if (plugin.getStage(player).equalsIgnoreCase("reward")) {
				event.setCancelled(true);
				player.sendMessage(a + "Rewards: " + w + event.getMessage());
				plugin.database.setArgument(String.valueOf(plugin.idCheck.get(player)), "reward", event.getMessage());
				plugin.database.update();
				plugin.endCommand(player);
				player.sendMessage(ChatColor.RED + "Quest creation complete.");
			}
		}
	}
}
