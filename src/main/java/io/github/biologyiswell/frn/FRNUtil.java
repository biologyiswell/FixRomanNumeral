package io.github.biologyiswell.frn;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author biologyiswell
 * @since 1.0
 */
public final class FRNUtil {

	FRNUtil() { // package-private
	}

	/**
	 * This method fix the all roman numerals from the enchantments that contains in the item.
	 *
	 * @param item the item that has your enchantments updated with roman numeral support.
	 * @return the item meta with roman numeral support.
	 * @since 1.0
	 */
	public static ItemMeta fixRomanNumeral(final ItemStack item) {
		return item.getEnchantments().isEmpty() ? item.getItemMeta() : fixRomanNumeral(item,
				item.getItemMeta().getEnchants());
	}

	/**
	 * This method fix the all roman numerals from the enchantments that will be added.
	 *
	 * @param item the item that has your enchantments updated.
	 * @param enchantments the enchantments with your respective levels.
	 * @return the item meta with roman numeral support.
	 * @since 1.0
	 */
	public static ItemMeta fixRomanNumeral(final ItemStack item, final Map<Enchantment, Integer> enchantments) {
		final ItemMeta meta = item.getItemMeta();
		final List<String> newLore = new ArrayList<>();

		// @Note Hide the current enchant, that contains the non-fixed roman numeral.
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		enchantments.forEach((ench, level) -> newLore.add(ChatColor.GRAY.toString()
				+ FRNData.ENCHANTMENTS.get(ench) + " " + FRNUtil.translateToRomanNumeral(level)));

		if (meta.hasLore()) {
			final List<String> lore = meta.getLore();

			// @Note Remove the enchantment interceptions.
			for (int i = 0; i < lore.size(); i++) {
				final String loreLine = lore.get(i);

				for (final String newLoreLine : newLore) {
					if (loreLine.startsWith(newLoreLine.substring(0, 5))) {
						lore.remove(i);
					}
				}
			}

			// newLore.add(""); // @Note 1 line space.
			newLore.addAll(lore);
		}

		meta.setLore(newLore);
		return meta;
	}

	/**
	 * This method translate from decimal numeral to roman numeral.
	 *
	 * @param value the value that will be translate.
	 * @return the roman numeral.
	 * @since 1.0
	 */
	public static String translateToRomanNumeral(int value) {
		if (value < 0) throw new IllegalArgumentException("value must be non negative");

		// @Note It's more efficiency use the StringBuilder instead of String concatenation.
		final StringBuilder sb = new StringBuilder();

		while (value != 0) {
			if (value >= 1000) {
				value -= 1000;
				sb.append("M");
				continue;
			}

			if (value >= 500) {
				value -= 500;
				sb.append("D");
				continue;
			}

			if (value >= 100) {
				value -= 100;
				sb.append("C");
				continue;
			}

			if (value >= 50) {
				value -= 50;
				sb.append("L");
				continue;
			}

			if (value >= 10) {
				value -= 10;
				sb.append("X");
				continue;
			}

			if (value >= 5) {
				value -= 5;
				sb.append("V");
				continue;
			}

			if (value >= 1) {
				value -= 1;
				sb.append("I");
				continue;
			}
		}

		return sb.toString();
	}
}