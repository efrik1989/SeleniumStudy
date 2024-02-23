package task19.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import task19.pages.CartPage;
import task19.pages.MainPage;
import task19.pages.ProductPage;

import java.util.List;

public class Application {
    private WebDriver driver;
    MainPage mainPage;
    ProductPage productPage;
    CartPage cartPage;

    public Application() {
        driver = new FirefoxDriver();
        mainPage = new MainPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }
    public void addProductsToCart(){
        for (int i = 0; i < 3; i++) {
            System.out.println("Открыть первый товар из списка");
            //Проверить количество товара в корзине
            int productCountBefore = countProduct();
            mainPage.firstProduct.click();
            productPage.sizeSelection();
            productPage.addButton.click();
            countProductInCartChangeCheck(productCountBefore);
            productPage.backToMainPage.click();
        }
    }

    public void deleteProductsFromCart() {
        System.out.println("Удаляем товары из корзины");
        while (true) {
            List<WebElement> positions = cartPage.cartItemList;
            int quantity = cartPage.quantityCheck(positions);
            if (quantity == 0) {
                break;
            }
            cartPage.removeButton.click();
            cartPage.tableUpdateCheck(positions);
            System.out.println("Количество товаров в корзине: " + quantity);

        }
        System.out.println("Все товары удалены из корзины");
    }

    public void quit() {
        driver.quit();
    }

    public void open() {
        System.out.println("Открыть главную страницу");
        mainPage.openMainPage();
    }

    public void openCartPage() {
        mainPage.cart.click();
        cartPage.title.isDisplayed();
    }

    public void countProductInCartChangeCheck(int productCountBefore) {
        int productCountAfter = 0;
        boolean isProductCountChanged = mainPage.isCountChange(productCountBefore);
        if (isProductCountChanged) {
            productCountAfter = countProduct();
        }

        System.out.println("Количество товаров в корзине увеличилось: " + isProductCountChanged +
                " = " + productCountAfter);
    }

    public int countProduct() {
        return Integer.parseInt(mainPage.quantity.getAttribute("textContent"));
    }
}
