package me.zkingofkill.spartan.loja.guis;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import me.zkingofkill.spartan.api.SpartanMaquinasAPI;
import me.zkingofkill.spartan.loja.Loja;
import me.zkingofkill.spartan.loja.objects.Item;
import me.zkingofkill.spartan.loja.objects.ItemType;
import me.zkingofkill.spartan.loja.objects.TransactionType;
import me.zkingofkill.spartan.loja.objects.User;
import me.zkingofkill.spartan.loja.utils.ItemStackBuilder;
import me.zkingofkill.spartan.objects.Props;
import me.zkingofkill.spartan.rankup.api.RankupAPI;
import me.zkingofkill.spartan.spawners.api.SpartanSpawnersAPI;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Category implements InventoryProvider {
    private SmartInventory INVENTORY;
    private Player player;
    private int page = 1;
    private ItemStack back;
    private ItemStack next;
    private ItemStack previous;
    private User user;
    private me.zkingofkill.spartan.loja.objects.Category category;

    public Category(User user, me.zkingofkill.spartan.loja.objects.Category category) {
        this.user = user;
        this.player = user.getPlayer();
        this.category = category;
        this.INVENTORY = SmartInventory.builder()
                .id(this.player.toString())
                .provider(this)
                .size(6, 9)
                .title(category.getId() + " #" + page)
                .build();

        back = new ItemStackBuilder(Material.ARROW).setName("§c< Voltar").addLore("", "§7Voltar ao menu principal.").build();
        next = new ItemStackBuilder(Material.ARROW).setName("§a> Pagina seguinte ").addLore("", "§7Abrir proxima pagina.").build();
        previous = new ItemStackBuilder(Material.ARROW).setName("§c< Pagina anterior").addLore("", "§7Abrir a pagina anterior.").build();
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();
        ClickableItem[] items = new ClickableItem[category.getItems().size()];
        int i = 0;
        for (Item itm : new ArrayList<>(category.getItems())) {
            Item item = itm.clone();
            item.setBuyprice(item.getBuyprice() - (item.getBuyprice() * user.getBuyDiscount() / 100));
            item.setSellprice(item.getSellprice() + (item.getSellprice() * user.getSellDiscount() / 100));
            ItemStackBuilder it = new ItemStackBuilder(item.getItemStack().clone());
            List<String> lore = it.build().getItemMeta().getLore() != null ? new ArrayList<>(it.build().getItemMeta().getLore()) : new ArrayList<>();
            lore.add("");
            lore.add("§6• Quantidade: §ax" + item.getAmount());
            lore.add("");
            if (item.isBuyable()) {
                lore.add("§6• Comprar: " + Loja.getInstance().getUtils().numberFormat(item.getBuyprice()) + "§7 (§a-" + user.getBuyDiscount() + "%§7) [direito]");
            }
            if (item.isSelleable()) {
                lore.add("§6• Vender: " + Loja.getInstance().getUtils().numberFormat(item.getSellprice()) + "§7 (§a+" + user.getSellDiscount() + "%§7) [esquerdo]");
            }
            it.setLore(lore);
            items[i] = ClickableItem.of(it.build(), e -> {
                if (e.isRightClick()) {
                    if (item.isBuyable()) {
                        me.zkingofkill.spartan.rankup.objects.User rank = RankupAPI.getCache().getUserByPlayer(player);
                        if (item.getItemType().equals(ItemType.MAQUINA)) {
                            Props maquina = SpartanMaquinasAPI.getMaquina(item.getId());
                            if (rank.getRank().getPosition() < maquina.getRankMinimo() && rank.getPrestigy() == 0) {
                                e.setCurrentItem(it.addLore("", "§cVocê não pode comprar esse item!").build());
                                return;
                            }
                        } else if (item.getItemType().equals(ItemType.SPAWNER)) {
                            me.zkingofkill.spartan.spawners.objects.Props spawner = SpartanSpawnersAPI.getSpawner(item.getId());
                            if (rank.getRank().getPosition() < spawner.getRankMinimo() && rank.getPrestigy() == 0) {
                                e.setCurrentItem(it.addLore("", "§cVocê não pode comprar esse item!").build());
                                return;
                            }
                        }
                        new Confirm(user, item, category, TransactionType.BUY).open();

                    } else {
                        player.sendMessage("§a[Loja] §cVocê não pode comprar esse item!");
                        player.playSound(player.getLocation(), Sound.NOTE_PIANO, 50F, 120F);
                    }
                }
                if (e.isLeftClick()) {
                    if (item.isSelleable()) {
                        new Confirm(user, item, category, TransactionType.SELL).open();
                    } else {
                        player.sendMessage("§a[Loja] §cVocê não pode vender esse item!");
                        player.playSound(player.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.5F);
                    }
                }
            });
            i++;
        }
        pagination.setItems(items);
        pagination.setItemsPerPage(28);
        SlotIterator iterator = contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1);
        iterator.blacklist(1, 0);
        iterator.blacklist(2, 0);
        iterator.blacklist(3, 0);
        iterator.blacklist(4, 0);

        iterator.blacklist(1, 8);
        iterator.blacklist(2, 8);
        iterator.blacklist(3, 8);
        iterator.blacklist(4, 8);

        iterator.allowOverride(false);
        pagination.addToIterator(iterator);
        contents.set(3, 0, ClickableItem.of(previous,
                e -> {
                    INVENTORY.open(player, pagination.previous().getPage());
                    page -= 1;
                }));
        contents.set(3, 8, ClickableItem.of(next,
                e -> {
                    INVENTORY.open(player, pagination.next().getPage());
                    page += 1;
                }));
        contents.set(5, 4, ClickableItem.of(back, e -> new Categories(player).open()));
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
