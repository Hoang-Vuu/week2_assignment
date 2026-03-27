package app;

import java.util.List;

public class ShoppingCartLogic {

    public double itemTotal(double price, int qty) {
        return price * qty;
    }

    public double cartTotal(List<Double> items) {
        return items.stream().mapToDouble(d -> d).sum();
    }
}