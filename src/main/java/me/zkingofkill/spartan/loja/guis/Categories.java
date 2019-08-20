package me.zkingofkill.spartan.loja.guis;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import me.zkingofkill.spartan.loja.Loja;
import me.zkingofkill.spartan.loja.objects.User;
import me.zkingofkill.spartan.loja.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachmentInfo;

public class Categories implements InventoryProvider {
    private SmartInventory INVENTORY;
    private Player player;
    private ItemStack back;
    public Categories(Player player){
        this.player = player;
        this.INVENTORY = SmartInventory.builder()
                .id(this.player.toString())
                .provider(this)
                .size(4, 9)
                .title("[Loja do servidor]")
                .build();
        back = new ItemStackBuilder(Material.ARROW).setName("§cVoltar").addLore("","§7< Voltar ao menu anterior.").build();
    }
    @Override
    public void init(Player player, InventoryContents contents) {
        User user = new User(player);
        Loja.getInstance().getCache().categoryList.forEach(category -> contents.set(category.getRow(), category.getColunm(),
                ClickableItem.of(category.getItemStack(), e -> {
                    for (PermissionAttachmentInfo perm : player.getEffectivePermissions()) {
                        //spartanloja.desconto.buy.10
                        if (perm.getPermission().contains("spartanloja.desconto.")) {
                           if(perm.getPermission().contains("buy")){
                              user.setBuyDiscount(Integer.parseInt(perm.getPermission().split("\\.")[3]));
                           }
                            if(perm.getPermission().contains("sell")){
                                user.setSellDiscount(Integer.parseInt(perm.getPermission().split("\\.")[3]));
                            }
                        }
                    }
                    new Category(user, category).open();
                })));

        contents.set(3, 4, ClickableItem.of(back, e -> new Main(player).open()));
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
