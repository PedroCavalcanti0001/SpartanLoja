package me.zkingofkill.spartan.loja.objects;

import org.bukkit.entity.Player;

public class User {
    private Player player;
    private int sellDiscount;
    private int buyDiscount;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getSellDiscount() {
        return sellDiscount;
    }

    public void setSellDiscount(int sellDiscount) {
        this.sellDiscount = sellDiscount;
    }

    public int getBuyDiscount() {
        return buyDiscount;
    }

    public void setBuyDiscount(int buyDiscount) {
        this.buyDiscount = buyDiscount;
    }

    public User(Player player) {
        this.player = player;
    }
}
