package app;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartLogicTest {

    ShoppingCartLogic logic = new ShoppingCartLogic();

    @Test
    void testItemTotal() {
        assertEquals(50.0, logic.itemTotal(10.0, 5));
    }

    @Test
    void testCartTotal() {
        List<Double> list = List.of(10.0, 20.0, 30.0);
        assertEquals(60.0, logic.cartTotal(list));
    }
}