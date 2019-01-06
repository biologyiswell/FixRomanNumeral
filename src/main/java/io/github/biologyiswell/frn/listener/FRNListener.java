package io.github.biologyiswell.frn.listener;

import io.github.biologyiswell.frn.FRNPlugin;
import io.github.biologyiswell.frn.FRNUtil;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author biologyiswell
 * @since 1.0
 */
public class FRNListener implements Listener {

	/**
	 * This method initialize the all events that contains in the listener that will be registered.
	 * @since 1.0
	 */
	public static void init() {
		Bukkit.getPluginManager().registerEvents(new FRNListener(), FRNPlugin.getInstance());
	}

	@EventHandler
	public void onEnchantItem(final EnchantItemEvent event) {
		final ItemStack item = event.getItem();

		// @Note Check if the item has the hide enchants flag, then is not necessary to fix the roman numeral to this
		// item, because the enchants is not shown. Is necessary check the NMS Tag, because when the fix roman numeral
		// is supported to the item, the item receive the "HIDE_ENCHANTS" flag, but how this is modified by this plugin
		// then is necessary put a metadata key. Instead of, any item after the roman numeral support will be changed
		// again because has the "HIDE_ENCHANTS" flag.
		//
		// @Unusable If the player wants fix the enchantment roman numeral level, then it's okay that the current item
		// doesn't has the "HIDE_ENCHANTS" item flag.
		// if (item.getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS) && (!meta.hasLore() || meta.getLore().stream()
		// 		.filter(lore -> lore.startsWith(FRNData.MODIFIED_BY_FRN_KEY)).findAny().orElse(null) == null)) return;

		item.setItemMeta(FRNUtil.fixRomanNumeral(item, event.getEnchantsToAdd()));
	}
}