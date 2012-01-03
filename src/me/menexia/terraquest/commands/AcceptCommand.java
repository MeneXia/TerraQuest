package me.menexia.terraquest.commands;

import me.menexia.terraquest.TerraQuest;
import me.mini.Arguments;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AcceptCommand implements CommandExecutor {
		private TerraQuest a;
		public AcceptCommand(TerraQuest instance) {
			a = instance;
		}
		
		ChatColor lightgreen = ChatColor.GREEN;
		ChatColor red = ChatColor.RED;
		ChatColor gray = ChatColor.GRAY;
	
	public boolean onCommand(CommandSender sender, Command cmd, String zhf, String[] args) {
		if (cmd.getName().equalsIgnoreCase("accept")) {
			Player player = (Player)sender;
			if (a.database.getArguments(a.inputWait.get(player)) != null) {
				a.database.setArgument(String.valueOf(a.inputWait.get(player)), "status", red + " - Accepted");
				if (a.database.getArguments("onquest") != null) {
					a.database.setArgument("onquest", player.getName(), String.valueOf(a.inputWait.get(player)));
				} else {
					Arguments onquest = new Arguments("onquest");
					onquest.setValue(player.getName(), String.valueOf(a.inputWait.get(player)));
					a.database.addIndex(onquest.getKey(), onquest);
				}
				player.sendMessage(lightgreen + "Quest accepted.");
				player.sendMessage(gray + "Type" + red + " /quest check " + gray + "to check your current quest.");
				a.database.update();
				a.inputWait.remove(player);
				return true;
			}
			player.sendMessage(red + "Check the objectives of a quest first!");
			player.sendMessage(gray + "Use /quest <number>");
			return true;
		}
		return false;
	}
}
