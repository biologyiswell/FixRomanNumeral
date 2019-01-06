package io.github.biologyiswell.frn.command;

import io.github.biologyiswell.frn.FRNPlugin;
import io.github.biologyiswell.frn.FRNUtil;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

// @Note I prefer don't use the implement of CommandExecutor to register the commands, but how that I want do this
// plugin more simplest, then use it.
/**
 * @author biologyiswell
 * @since 1.0
 */
public class FRNCommand implements CommandExecutor {

	/**
	 * This method initialize the all comands that contains in this plugin.
	 * @since 1.0
	 */
	public static void init() {
		FRNPlugin.getInstance().getCommand("frn").setExecutor(new FRNCommand());
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String s, final String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
			return false;
		}

		if (!sender.isOp() && !sender.hasPermission("frn.cmd")) {
			sender.sendMessage(ChatColor.RED + "You don\'t has the permission to execute this command.");
			return false;
		}

		final Player player = (Player) sender;
		final ItemStack item = player.getItemInHand();

		if (item == null || item.getType() == Material.AIR) {
			player.sendMessage(ChatColor.RED + "You must hold an item.");
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
			return false;
		}

		final ItemMeta meta = item.getItemMeta();

		if (meta.getEnchants().isEmpty()) {
			player.sendMessage(ChatColor.RED + "This item doesn\'t has enchants to update.");
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
			return false;
		}

		item.setItemMeta(FRNUtil.fixRomanNumeral(item));
		player.sendMessage(ChatColor.GREEN + "All enchantments from item has been updated.");
		player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
		return false;
	}
}