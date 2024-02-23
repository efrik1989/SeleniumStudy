package task19.tests;

import org.junit.Test;

public class CartWorkCheckScenario extends TestBase {

    @Test
    public void cartWorkCheck(){
        app.open();
        app.addProductsToCart();
        app.openCartPage();
        app.deleteProductsFromCart();
        app.quit();
    }

}
