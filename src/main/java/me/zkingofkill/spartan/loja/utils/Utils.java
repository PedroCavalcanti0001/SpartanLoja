package me.zkingofkill.spartan.loja.utils;

import me.zkingofkill.spartan.loja.Loja;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class Utils {
    public String numberFormat(double value) {
        String[] suffix = Loja.getInstance().getConfig().getStringList("decimalplaces").toArray(new String[0]);
        int index = 0;
        while ((value / 1000) >= 1) {
            value = value / 1000;
            index++;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return String.format("%s %s", decimalFormat.format(value), suffix[index]);
    }
    public int getEmptySlots(Inventory inventory, ItemStack... search) {
        List<ItemStack> itens = Arrays.asList(search);
        int slots = 0;
        ItemStack[] contents = inventory.getContents();
        for (ItemStack item : contents) {
            if (item != null) {
                slots += itens.stream().filter(itemStack ->
                        (item.getType().equals(itemStack.getType()) && item.getDurability() == itemStack.getDurability()) && (
                                ((item.hasItemMeta() && itemStack.hasItemMeta())
                                        && item.getItemMeta().equals(itemStack.getItemMeta()))
                                        || !item.hasItemMeta() && !itemStack.hasItemMeta()))
                        .mapToInt(w -> (64 - item.getAmount())).sum();
            } else {
                slots += 64;
            }
        }
        return slots;
    }
    public int getSimilarItems(Inventory inventory, ItemStack item) {
        final int[] q = {0};
        Arrays.stream(inventory.getContents()).forEach(itemStack -> {
            if(itemStack != null) {
                if (item.getType().equals(itemStack.getType())) {
                    if ((item.getType().equals(itemStack.getType()) && item.getDurability() == itemStack.getDurability()) && (
                            ((item.hasItemMeta() && itemStack.hasItemMeta())
                                    && item.getItemMeta().equals(itemStack.getItemMeta()))
                                    || !item.hasItemMeta() && !itemStack.hasItemMeta())) {
                        q[0] += itemStack.getAmount();
                    }
                }
            }
        });
        return q[0];
    }
    public void removeItem(Inventory inventory, ItemStack item, int amount){
        final int[] q = {0};
        Arrays.stream(inventory.getContents()).forEach(itemStack -> {
            if(itemStack != null) {
                if (item.getType().equals(itemStack.getType())) {
                    if (((item.hasItemMeta() && itemStack.hasItemMeta()) && item.getItemMeta().equals(itemStack.getItemMeta())) || !item.hasItemMeta() && !itemStack.hasItemMeta()) {
                        if (q[0] < amount) {
                            if (itemStack.getAmount() <= amount) {
                                inventory.removeItem(itemStack);
                                q[0] += itemStack.getAmount();
                            } else {
                                q[0] += amount;
                                itemStack.setAmount(itemStack.getAmount() - amount);
                            }
                        }
                    }
                }
            }
        });
    }
}
