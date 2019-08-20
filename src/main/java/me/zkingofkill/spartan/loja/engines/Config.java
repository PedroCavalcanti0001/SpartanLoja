package me.zkingofkill.spartan.loja.engines;

import me.zkingofkill.spartan.loja.Loja;
import me.zkingofkill.spartan.loja.objects.Category;
import me.zkingofkill.spartan.loja.objects.Item;
import me.zkingofkill.spartan.loja.objects.ItemType;
import me.zkingofkill.spartan.loja.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Config {
    public void init(){
        Configuration cf = Loja.getInstance().getConfig();
        for(String categoria : cf.getConfigurationSection("shop").getKeys(false)){
            String name = cf.getString("shop."+categoria+".name").replace("&","§");
            int row = cf.getInt("shop."+categoria+".position.row");
            int colunm = cf.getInt("shop."+categoria+".position.colunm");
            Material material = Material.getMaterial(cf.getInt("shop."+categoria+".item.material"));
            int data = cf.getInt("shop."+categoria+".item.data");
            boolean glow = cf.getBoolean("shop."+categoria+".item.glow");
            List<String> lore = cf.getStringList("shop."+categoria+".item.lore").stream()
                    .map(o -> o.contains("&") ? o.replace("&", "§") : o)
                    .map(o -> o.contains("%categoria%") ? o.replace("%categoria%",categoria) : o)
                    .collect(Collectors.toList());
            ItemStack itemStack = new ItemStackBuilder(material).setDurability(data).setName(name).setGlow(glow).setLore(lore).build();
            List<Item> items = new ArrayList<>();
            for(String item : cf.getConfigurationSection("shop."+categoria+".items").getKeys(false)){
                String itemName = cf.contains("shop."+categoria+".items."+item+".name") ? cf.getString("shop."+categoria+".items."+item+".name").replace("&","§") : null;
                String itemId = cf.getString("shop."+categoria+".items."+item+".id").replace("&","§");
                Material itemMaterial = Material.getMaterial(cf.getInt("shop."+categoria+".items."+item+".material"));
                int itemData = cf.getInt("shop."+categoria+".items."+item+".data");
                int itemAmount = cf.getInt("shop."+categoria+".items."+item+".amount");
                ItemStackBuilder itemStack1 = new ItemStackBuilder(itemMaterial).setDurability(itemData);
                if(itemName != null){
                    itemStack1.setName(itemName);
                }
                if(cf.contains("shop."+categoria+".items."+item+".lore")){
                    List<String> lore2 = cf.getStringList("shop."+categoria+".items."+item+".lore");
                    itemStack1.setLore(lore2);
                }
                ItemType itemType = ItemType.valueOf(cf.getString("shop."+categoria+".items."+item+".type").toUpperCase());
                double buyprice = cf.getDouble("shop."+categoria+".items."+item+".buyprice");
                double sellprice = cf.getDouble("shop."+categoria+".items."+item+".sellprice");
                boolean selleable = cf.contains("shop."+categoria+".items."+item+".sellprice");
                boolean buyable = cf.contains("shop."+categoria+".items."+item+".buyprice");
                if(cf.contains("shop."+categoria+".items."+item+".enchantments")){
                    cf.getStringList("shop."+categoria+".items."+item+".enchantments").forEach(s -> {
                        Enchantment e = Enchantment.getByName(s.split("-")[0]);
                        int level = Integer.valueOf(s.split("-")[1]);
                        itemStack1.addEnchantment(e, level);
                    });
                }
                Item itemObj = new Item(Integer.valueOf(item), itemName, itemId, itemStack1.build(), itemType, buyprice, sellprice, buyable, selleable, itemAmount);
                items.add(itemObj);
            }
            Category category = new Category(categoria, name, row, colunm, itemStack, items);
            Loja.getInstance().getCache().categoryList.add(category);
        }
    }
}
