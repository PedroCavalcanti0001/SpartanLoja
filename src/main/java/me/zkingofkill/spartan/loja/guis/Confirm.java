package me.zkingofkill.spartan.loja.guis;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import me.zkingofkill.spartan.Maquinas;
import me.zkingofkill.spartan.api.SpartanMaquinasAPI;
import me.zkingofkill.spartan.loja.Loja;
import me.zkingofkill.spartan.loja.objects.Item;
import me.zkingofkill.spartan.loja.objects.ItemType;
import me.zkingofkill.spartan.loja.objects.TransactionType;
import me.zkingofkill.spartan.loja.objects.User;
import me.zkingofkill.spartan.loja.utils.ItemStackBuilder;
import me.zkingofkill.spartan.loja.utils.Utils;
import me.zkingofkill.spartan.objects.Combustivel;
import me.zkingofkill.spartan.objects.Maquina;
import me.zkingofkill.spartan.objects.Props;
import me.zkingofkill.spartan.rankup.SpartanRankup;
import me.zkingofkill.spartan.rankup.api.RankupAPI;
import me.zkingofkill.spartan.rankup.objects.Rank;
import me.zkingofkill.spartan.spawners.SpartanSpawners;
import me.zkingofkill.spartan.spawners.api.SpartanSpawnersAPI;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Confirm implements InventoryProvider {
    private SmartInventory INVENTORY;
    private Player player;
    private User user;
    private me.zkingofkill.spartan.loja.objects.Category category;
    private ItemStack back;
    private Item item;
    private TransactionType transactionType;

    public Confirm(User user, Item item, me.zkingofkill.spartan.loja.objects.Category category, TransactionType transactionType) {
        this.item = item.clone();
        this.player = user.getPlayer();
        this.category = category;
        this.user = user;
        this.transactionType = transactionType;
        this.INVENTORY = SmartInventory.builder()
                .id(this.player.toString())
                .provider(this)
                .size(4, 9)
                .title("[Confirmar transação]")
                .build();
        back = new ItemStackBuilder(Material.ARROW).setName("§cVoltar").addLore("", "§7< Voltar ao menu anterior.").build();


    }

    @Override
    public void init(Player player, InventoryContents contents) {
        int a1 = 1;
        int a2 = 32;
        int a3 = 64;
        if(item.getItemType().equals(ItemType.COMBUSTIVEL)) {
            a2 = 500;
            a3 = 1000;
            contents.set(1, 8, ClickableItem.of(new ItemStackBuilder(Material.STAINED_GLASS_PANE).setName("§a§lAdicionar 50000x").setDurability(13).build(), e -> {
                if (transactionType.equals(TransactionType.BUY)) {
                    item.setBuyprice((item.getBuyprice() + (item.getBuyprice() / item.getAmount() * 50000)));
                } else {
                    item.setSellprice((item.getSellprice() + (item.getSellprice() / item.getAmount() * 50000)));
                }
                item.setAmount(item.getAmount() + 50000);
                open();
            }));
            contents.set(1, 0, ClickableItem.of(new ItemStackBuilder(Material.STAINED_GLASS_PANE).setName("§c§lRemover 50000x").setDurability(14).build(), e -> {
                if (item.getAmount() >= 50001) {
                    if (transactionType.equals(TransactionType.BUY)) {
                        item.setBuyprice((item.getBuyprice() - (item.getBuyprice() / item.getAmount() * 50000)));
                    } else {
                        item.setSellprice((item.getSellprice() - (item.getSellprice() / item.getAmount() * 50000)));
                    }
                    item.setAmount(item.getAmount() - 50000);
                    open();
                }
            }));
        }

        contents.set(1, 3, ClickableItem.of(new ItemStackBuilder(Material.STAINED_GLASS_PANE).setName("§c§lRemover 1x").setAmount(1).setDurability(14).build(), e -> {
            if (item.getAmount() >= 2) {
                if (transactionType.equals(TransactionType.BUY)) {
                    item.setBuyprice((item.getBuyprice() - (item.getBuyprice() / item.getAmount() * 1)));
                } else {
                    item.setSellprice((item.getSellprice() - (item.getSellprice() / item.getAmount() * 1)));
                }
                item.setAmount(item.getAmount() - 1);
                open();
            }
        }));
        int finalA = a2;
        contents.set(1, 2, ClickableItem.of(new ItemStackBuilder(Material.STAINED_GLASS_PANE).setName("§c§lRemover "+a2+"x").setDurability(14).build(), e -> {
            if (item.getAmount() >= 1+finalA) {
                if (transactionType.equals(TransactionType.BUY)) {
                    item.setBuyprice((item.getBuyprice() - (item.getBuyprice() / item.getAmount() * finalA)));
                } else {
                    item.setSellprice((item.getSellprice() - (item.getSellprice() / item.getAmount() * finalA)));
                }
                item.setAmount(item.getAmount() - finalA);
                open();
            }
        }));
        int finalA1 = a3;
        contents.set(1, 1, ClickableItem.of(new ItemStackBuilder(Material.STAINED_GLASS_PANE).setName("§c§lRemover "+a3+"x").setDurability(14).build(), e -> {
            if (item.getAmount() >= 1+ finalA1) {
                if (transactionType.equals(TransactionType.BUY)) {
                    item.setBuyprice((item.getBuyprice() - (item.getBuyprice() / item.getAmount() * finalA1)));
                } else {
                    item.setSellprice((item.getSellprice() - (item.getSellprice() / item.getAmount() * finalA1)));
                }
                item.setAmount(item.getAmount() - finalA1);
                open();
            }
        }));
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§6• Quantidade: §ax" + item.getAmount());
        lore.add("§6• Valor: §a" + (transactionType.equals(TransactionType.BUY) ? Loja.getInstance().getUtils().numberFormat(item.getBuyprice()) : Loja.getInstance().getUtils().numberFormat(item.getSellprice())) + " Coins");
        lore.add("");
        contents.set(1, 4, ClickableItem.empty(new ItemStackBuilder(item.getItemStack().clone()).setLore(lore).build()));
        contents.set(1, 5, ClickableItem.of(new ItemStackBuilder(Material.STAINED_GLASS_PANE).setName("§a§lAdicionar 1x").setAmount(1).setDurability(13).build(), e -> {
            if (transactionType.equals(TransactionType.BUY)) {
                item.setBuyprice((item.getBuyprice() + (item.getBuyprice() / item.getAmount() * 1)));
            } else {
                item.setSellprice((item.getSellprice() + (item.getSellprice() / item.getAmount() * 1)));
            }
            item.setAmount(item.getAmount() + 1);
            open();
        }));

        contents.set(1, 6, ClickableItem.of(new ItemStackBuilder(Material.STAINED_GLASS_PANE).setName("§a§lAdicionar "+a2+"x").setDurability(13).build(), e -> {
                if (transactionType.equals(TransactionType.BUY)) {
                    item.setBuyprice((item.getBuyprice() + (item.getBuyprice() / item.getAmount() * finalA)));
                } else {
                    item.setSellprice((item.getSellprice() + (item.getSellprice() / item.getAmount() * finalA)));
                }
                item.setAmount(item.getAmount() + finalA);
                open();
        }));
        contents.set(1, 7, ClickableItem.of(new ItemStackBuilder(Material.STAINED_GLASS_PANE).setName("§a§lAdicionar "+a3+"x").setDurability(13).build(), e -> {
                if (transactionType.equals(TransactionType.BUY)) {
                    item.setBuyprice((item.getBuyprice() + (item.getBuyprice() / item.getAmount() * finalA1)));
                } else {
                    item.setSellprice((item.getSellprice() + (item.getSellprice() / item.getAmount() * finalA1)));
                }
                item.setAmount(item.getAmount() + finalA1);
                open();
        }));
        contents.set(3, 3, ClickableItem.of(back, e -> new Category(user, category).open()));
        contents.set(3, 5, ClickableItem.of(new ItemStackBuilder(Material.STAINED_GLASS).setName("§a§lConfirmar transação.").setDurability(5).build(), e -> {
            ItemStackBuilder itemStackBuilder = new ItemStackBuilder(item.getItemStack().getType()).setDurability(item.getItemStack().getDurability());
            for (Map.Entry<Enchantment, Integer> ench : item.getItemStack().getEnchantments().entrySet()) {
                itemStackBuilder.addEnchantment(ench.getKey(), ench.getValue());
            }
            item.setItemStack(itemStackBuilder.build());

            if (transactionType.equals(TransactionType.BUY)) {
                if (Loja.getInstance().getEcon().getBalance(player) >= item.getBuyprice()) {
                    if ((item.getItemType().equals(ItemType.COMBUSTIVEL) && Loja.getInstance().getUtils().getEmptySlots(player.getInventory(), new ItemStack(item.getItemStack())) >= 1)
                            ||  Loja.getInstance().getUtils().getEmptySlots(player.getInventory(), new ItemStack(item.getItemStack())) >= item.getAmount()) {
                        if (item.getItemType().equals(ItemType.ITEM)) {
                            for (int i = 0; i < item.getAmount(); i++) {
                                ItemStackBuilder itemStack = new ItemStackBuilder(item.getItemStack()).setLore(new ArrayList<>());
                                player.getInventory().addItem(itemStack.build());
                            }
                        } else if (item.getItemType().equals(ItemType.MAQUINA)) {
                            Props maquina = SpartanMaquinasAPI.getMaquina(item.getId());
                            SpartanMaquinasAPI.give(player, maquina, item.getAmount());
                        } else if (item.getItemType().equals(ItemType.SPAWNER)) {
                            SpartanSpawnersAPI.give(player, SpartanSpawnersAPI.getSpawner(item.getId()), item.getAmount());
                        } else if (item.getItemType().equals(ItemType.COMBUSTIVEL)) {
                            Combustivel c = SpartanMaquinasAPI.getCombustivel(Integer.parseInt(item.getId())).clone();
                            SpartanMaquinasAPI.giveComb(player, c, item.getAmount());
                        }
                        player.sendMessage("§a[Loja] §aCompra efetuada com sucesso!");
                        Loja.getInstance().getEcon().withdrawPlayer(player, item.getBuyprice());
                        close();
                        String n = item.getItemStack().hasItemMeta() && item.getItemStack().getItemMeta().hasDisplayName() ? item.getItemStack().getItemMeta().getDisplayName() : "N/A";
                        System.out.println("[LOJA] O jogador "+player.getName()+" comprou " +item.getAmount()+
                                "x "+item.getItemStack().getType()+"/" +n+" ("+item.getItemType()+") por "+Loja.getInstance().getUtils().numberFormat(item.getBuyprice()) +".");

                    } else {
                        player.sendMessage("§a[Loja] §cSem espaço suficiente no inventario!");
                        player.playSound(player.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.5F);
                    }
                } else {
                    player.sendMessage("§a[Loja] §cSem coins suficientes para efetuar a compra!");
                    player.playSound(player.getLocation(), Sound.NOTE_PIANO, 3.0F, 0.5F);
                }
            } else {
                int q = Loja.getInstance().getUtils().getSimilarItems(player.getInventory(), item.getItemStack());
                if (q >= item.getAmount()) {
                    Loja.getInstance().getEcon().depositPlayer(player, item.getSellprice());
                    player.sendMessage("§a[Loja] §aVenda efetuada com sucesso!");
                    Loja.getInstance().getUtils().removeItem(player.getInventory(), item.getItemStack(), item.getAmount());
                    close();
                } else {
                    player.sendMessage("§a[Loja] §cVocê não tem itens suficientes no inventário.");
                }
            }

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
