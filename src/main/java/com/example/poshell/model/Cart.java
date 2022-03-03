package com.example.poshell.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {

    private List<Item> items = new ArrayList<>();

    public boolean addItem(Item item) {
        return items.add(item);
    }

    //TODO:remove item
    public boolean removeItem(Item item){
        return items.remove(item);
    }
    //TODO:empty the items
    public void emptyCart(){
        this.items.clear();
    }
    //TODO:get certain item by id from list of items
    public Item getItemById(String id_){
        for (Item item : items){
            if (item.getProduct().getId().equals(id_)){
                return item;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        if (items.size() ==0){
            return "Empty Cart";
        }
        double total = 0;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cart -----------------\n"  );

        for (int i = 0; i < items.size(); i++) {
            stringBuilder.append(items.get(i).toString()).append("\n");
            total += items.get(i).getAmount() * items.get(i).getProduct().getPrice();
        }
        stringBuilder.append("----------------------\n"  );

        stringBuilder.append("Total...\t\t\t" + total );

        return stringBuilder.toString();
    }
}
