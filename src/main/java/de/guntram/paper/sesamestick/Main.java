package de.guntram.paper.sesamestick;

import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Lidded;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    
    private Logger logger;
    private String openName, closeName;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        this.logger = getLogger();
        saveDefaultConfig();
        config = getConfig();
        openName = config.getString("openName", "Sesame Open");
        closeName = config.getString("closeName", "Sesame Close");

        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler    
    public void onUseItem(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK
        &&  event.hasItem()
        &&  event.getItem().getType() == Material.STICK
        && event.getClickedBlock().getState() instanceof Lidded
        ) {
            String name = event.getItem().getItemMeta().getDisplayName();
            Block block = event.getClickedBlock();
            BlockState state = block.getState();
            if (name.equals(openName)) {
                ((Lidded) state).open();
                event.setCancelled(true);
            } else if (name.equals(closeName)) {
                ((Lidded) state).close();
                event.setCancelled(true);
            }
        }
    }
}
