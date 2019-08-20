package me.zkingofkill.spartan.loja.objects;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Category {
    private String id;
    private String name;
    private int row;
    private int colunm;
    private ItemStack itemStack;
    private List<Item> items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColunm() {
        return colunm;
    }

    public void setColunm(int colunm) {
        this.colunm = colunm;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Category(String id, String name, int line, int colunm, ItemStack itemStack, List<Item> items) {
        this.id = id;
        this.name = name;
        this.row = line;
        this.colunm = colunm;
        this.itemStack = itemStack;
        this.items = items;
    }
}
