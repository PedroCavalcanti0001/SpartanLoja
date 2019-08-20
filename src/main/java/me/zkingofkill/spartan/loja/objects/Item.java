package me.zkingofkill.spartan.loja.objects;

        import org.bukkit.inventory.ItemStack;

public class Item implements Cloneable{
    private int position;
    private String name;
    private String id;
    private ItemStack itemStack;
    private ItemType itemType;
    private double buyprice;
    private double sellprice;
    private boolean buyable;
    private boolean selleable;
    private int amount;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public double getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(double buyprice) {
        this.buyprice = buyprice;
    }

    public double getSellprice() {
        return sellprice;
    }

    public void setSellprice(double sellprice) {
        this.sellprice = sellprice;
    }

    public boolean isBuyable() {
        return buyable;
    }

    public void setBuyable(boolean buyable) {
        this.buyable = buyable;
    }

    public boolean isSelleable() {
        return selleable;
    }

    public void setSelleable(boolean selleable) {
        this.selleable = selleable;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    @Override
    public Item clone() {
        try {
            return (Item) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Item(int position, String name, String id, ItemStack itemStack, ItemType itemType, double buyprice, double sellprice, boolean buyable, boolean selleable, int amount) {
        this.position = position;
        this.name = name;
        this.id = id;
        this.itemStack = itemStack;
        this.itemType = itemType;
        this.buyprice = buyprice;
        this.sellprice = sellprice;
        this.buyable = buyable;
        this.selleable = selleable;
        this.amount = amount;
    }
}
