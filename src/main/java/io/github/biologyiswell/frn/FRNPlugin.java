package io.github.biologyiswell.frn;

import io.github.biologyiswell.frn.command.FRNCommand;
import io.github.biologyiswell.frn.listener.FRNListener;

import lombok.Getter;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author biologyiswell
 * @since 1.0
 */
public class FRNPlugin extends JavaPlugin {

	// @Note Instance that is used by another classes to can be access the main class of plugin.
	// That also represents a provider for JavaPlugin method access.
	@Getter private static FRNPlugin instance;

	@Override
	public void onEnable() {
		instance = this;

		// ... Initialize data ...
		FRNData.init();

		// ... Initialize listeners ...
		FRNListener.init();

		// ... Initialize commands ...
		FRNCommand.init();
	}

	@Override
	public void onDisable() {

	}
}