package io.github.biologyiswell.frn;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author biologyiswell
 * @since 1.0
 */
public final class FRNData {

	/**
	 * This represents the Gson variable, that serialize the Java Objects into JSON Format, and deserialize JSON files
	 * into Java Objects.
	 * @since 1.0
	 */
	public static final Gson GSON = new GsonBuilder()
			.setPrettyPrinting()
			.create();

	/**
	 * This represents the map that contains the all enchantments and your respective translation.
	 * @sincee 1.0
	 */
	public static final Map<Enchantment, String> ENCHANTMENTS = new HashMap<>();

	/**
	 * This method initialize the database and the all configurations.
	 * @since 1.0
	 */
	public static void init() {
		final File dataFolder = FRNPlugin.getInstance().getDataFolder();

		// @Note Check if the data folder not exists, and if the operation of make directory was failed.
		if (!dataFolder.exists() && !dataFolder.mkdirs()) {
			final Logger logger = FRNPlugin.getInstance().getLogger();
			logger.severe("");
			logger.severe("An error occured when initialize the database folder of \"" + FRNPlugin.getInstance()
					.getName() + "\".");
			logger.severe("The plugin will be restarted on 10 seconds.");
			logger.severe("Please, if the problem persists report to an administrator.");
			logger.severe("");

			// @Note Run a task that will disable the plugin on 20 seconds.
			Bukkit.getScheduler().scheduleSyncDelayedTask(FRNPlugin.getInstance(),
					() -> Bukkit.getPluginManager().disablePlugin(FRNPlugin.getInstance()), 20L * 10);
			return;
		}

		final File configFile = new File(dataFolder, "config.json");
		final JsonObject jsonObject;

		// @Note Check if the configuration file not exists, then create the file and write the configuration fields
		// into the fields.
		if (!configFile.exists()) {
			try (final FileWriter writer = new FileWriter(configFile)) {
				jsonObject = new JsonObject();
				jsonObject.addProperty("language", "en_us");

				writer.write(GSON.toJson(jsonObject));
			} catch (IOException e) {
				FRNPlugin.getInstance().getLogger().severe("An error occured when write to configuration file \"" +
						configFile.getName() + "\".");
				e.printStackTrace();
				return;
			}
		} else {
			try (final FileReader reader = new FileReader(configFile)) {
				jsonObject = GSON.fromJson(reader, JsonObject.class);
			} catch (IOException e) {
				FRNPlugin.getInstance().getLogger().severe("An error occured when read the configuration file " +
						"\"" + configFile.getName() + "\".");
				e.printStackTrace();
				return;
			}
		}

		final File languageFolder = new File(dataFolder, "lang");

		if (!languageFolder.exists() && !languageFolder.mkdirs()) {
			final Logger logger = FRNPlugin.getInstance().getLogger();
			logger.severe("");
			logger.severe("An error occured when initialize the language folder of \"" + FRNPlugin.getInstance()
					.getName() + "\".");
			logger.severe("The plugin will be restarted on 10 seconds.");
			logger.severe("Please, if the problem persists report to an administrator.");
			logger.severe("");

			// @Note Run a task that will disable the plugin on 20 seconds.
			Bukkit.getScheduler().scheduleSyncDelayedTask(FRNPlugin.getInstance(),
					() -> Bukkit.getPluginManager().disablePlugin(FRNPlugin.getInstance()), 20L * 10);
			return;
		}

		final File translationFile = new File(languageFolder, jsonObject.get("language").getAsString() + ".json");

		if (!translationFile.exists()) {
			final Logger logger = FRNPlugin.getInstance().getLogger();
			logger.severe("");
			logger.severe("Translation file \"" + translationFile.getName() + "\" not exists.");
			logger.severe("Please, download the plugin again to can get the all translation files that is available.");
			logger.severe("");
			return;
		}

		try (final FileReader reader = new FileReader(translationFile)) {
			final JsonObject translationObject = GSON.fromJson(reader, JsonObject.class);

			// @Note Put the all enchantment and your respective translation into a map.
			for (final Enchantment enchantment : Enchantment.values()) {
				ENCHANTMENTS.put(enchantment, translationObject.get("enchantment.minecraft." + enchantment.getKey().getKey())
						.getAsString());
			}
		} catch (IOException e) {
			FRNPlugin.getInstance().getLogger().severe("An error occured when read the translation file \"" +
					configFile.getName() + "\".");
			e.printStackTrace();
			return;
		}
	}
}