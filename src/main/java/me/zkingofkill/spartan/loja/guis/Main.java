package me.zkingofkill.spartan.loja.guis;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.zkingofkill.spartan.loja.Loja;
import me.zkingofkill.spartan.loja.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Main implements InventoryProvider {
    private SmartInventory INVENTORY;
    private Player player;
    private ItemStack lojaservidor;
    private ItemStack lojavips;
    private ItemStack lojamercado;

    public Main(Player player){
        this.player = player;
        this.INVENTORY = SmartInventory.builder()
                .id(this.player.toString())
                .provider(this)
                .size(3, 9)
                .title("[Lojas]")
                .build();
        lojaservidor = new ItemStackBuilder(Material.GRASS).setName("§aLoja do servidor").addLore("","§7Clique para abrir","§7a loja de itens do servidor.").build();
        lojavips = new ItemStackBuilder(Material.DIAMOND_BLOCK).setName("§aLojas vips").addLore("","§7Clique para abrir","§7o menu de lojas vips.","","§cEm desenvolvimento.").build();
        lojamercado = new ItemStackBuilder(Material.EMERALD_BLOCK).setName("§aMercado público").addLore("","§7Clique para abrir","§7o mercado público de itens.","","§cEm desenvolvimento.").build();
    }
    @Override
    public void init(Player player, InventoryContents contents) {
        contents.set(1, 1, ClickableItem.of(lojaservidor, e -> new Categories(player).open()));
        contents.set(1, 4, ClickableItem.of(lojavips, e ->{

        }));
        contents.set(1, 7, ClickableItem.of(lojamercado, e ->{

        }));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
    public void open() {
        this.INVENTORY.open(player);
    }

    public void close() {
        this.INVENTORY.close(player);
    }
}
