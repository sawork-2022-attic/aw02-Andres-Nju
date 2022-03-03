package com.example.poshell.cli;

import com.example.poshell.biz.PosService;
import com.example.poshell.model.Cart;
import com.example.poshell.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class PosCommand {

    private PosService posService;

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }

    @ShellMethod(value = "List Products", key = "p")
    public String products() {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (Product product : posService.products()) {
            stringBuilder.append("\t").append(++i).append("\t").append(product).append("\n");
        }
        return stringBuilder.toString();
    }

    @ShellMethod(value = "New Cart", key = "n")
    public String newCart() {
        return posService.newCart() + " OK";
    }

    @ShellMethod(value = "Add a Product to Cart", key = "a")
    public String addToCart(String productId, int amount) {
        if (posService.getCart() == null) return "There is no cart!";
        if (posService.add(productId, amount)) {
            return posService.getCart().toString();
        }
        return "ERROR";
    }

    @ShellMethod(value = "Print Current Cart", key = "c")
    public String PrintCart(){
        Cart cart = posService.getCart();
        if (cart == null){
            return "There is no cart!";
        }
        return cart.toString();
    }

    @ShellMethod(value = "Empty the Cart", key = "e")
    public String EmptyCart(){
        Cart cart = posService.getCart();
        if (cart == null){
            return "There is no cart!";
        }
        cart.emptyCart();
        return cart.toString();
    }

    @ShellMethod(value = "Modify Certain Item in the Cart", key = "m")
    public String ModifyItem(String productId, int amount){
        Cart cart = posService.getCart();
        if (cart == null){
            return "There is no cart!";
        }
        if (amount < 0){
            return "Amount cannot be negative!";
        }
        if (cart.removeItem(cart.getItemById(productId))){
            if (amount == 0) return cart.toString();
            if (posService.add(productId, amount)) {
                return cart.toString();
            }
            else return "ERROR";
        }
        return "No such item in cart!";
    }
}
