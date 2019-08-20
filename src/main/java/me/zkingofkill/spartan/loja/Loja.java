package me.zkingofkill.spartan.loja;

import fr.minuskube.inv.InventoryManager;
import me.zkingofkill.spartan.loja.data.Cache;
import me.zkingofkill.spartan.loja.engines.Config;
import me.zkingofkill.spartan.loja.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Loja extends JavaPlugin {
    private static Loja loja;
    private Cache cache;
    private InventoryManager inventoryManager;
    private Economy econ = null;
    private Permission perms = null;
    private Utils utils;

    @Override
    public void onEnable() {
        loja = this;
        inventoryManager = new InventoryManager(this);
        inventoryManager.init();

        if(!new File(getDataFolder(), "config.yml").exists()){
            saveDefaultConfig();
        }
        cache = new Cache();
        new Config().init();
        getCommand("loja").setExecutor(new me.zkingofkill.spartan.loja.commands.Loja());

        setupEconomy();
        utils = new Utils();

    }

    @Override
    public void onDisable() {

    }

    public static Loja getInstance() {
        return loja;
    }

    public Cache getCache() {
        return cache;
    }

    public Economy getEcon() {
        return econ;
    }

    public Utils getUtils() {
        return utils;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

}

