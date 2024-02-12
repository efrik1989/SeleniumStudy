import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class ProductPageCheck {
    WebDriver driver;
    final static long DEFAULT_TIMEOUT = 5000;

    @Before
    public void beforeTest() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    }

    @Test
    public void productCheck() {
        driver.get("http://localhost/litecard/");

        System.out.println("Проверка главной страницы.");
        WebElement element = driver.findElement(By.cssSelector("#box-campaigns li:nth-child(1) a.link[title*=Duck]"));
        String title = element.findElement(By.cssSelector("div.name")).getAttribute("textContent");
        String regularPrice = element.findElement(By.cssSelector("s.regular-price")).getAttribute("textContent");
        String regularPriceTag = element.findElement(By.cssSelector("s.regular-price")).getTagName();
        String regularPriceColor = element.findElement(By.cssSelector("s.regular-price")).getCssValue("color");
        Dimension regularPriceTextSize = element.findElement(By.cssSelector("s.regular-price")).getSize();
        String campaignPrice = element.findElement(By.cssSelector("strong.campaign-price")).getAttribute("textContent");
        String campaignPriceTag = element.findElement(By.cssSelector("strong.campaign-price")).getTagName();
        String campaignPriceColor = element.findElement(By.cssSelector("strong.campaign-price")).getCssValue("color");
        Dimension campaignPriceTextSize = element.findElement(By.cssSelector("strong.campaign-price")).getSize();

        element.click();

        System.out.println("Проверка страницы товара.");
        String productTitle = driver.findElement(By.tagName("h1")).getText();
        String productRegularPrice = driver.findElement(By.cssSelector("s.regular-price")).getText();
        String productRegularPriceTag = driver.findElement(By.cssSelector("s.regular-price")).getTagName();
        String productRegularPriceColor = driver.findElement(By.cssSelector("s.regular-price")).getCssValue("color");
        String productCampaignPrice = driver.findElement(By.cssSelector("strong.campaign-price")).getAttribute("textContent");
        String productCampaignPriceTag = driver.findElement(By.cssSelector("strong.campaign-price")).getTagName();
        String productCampaignPriceColor = driver.findElement(By.cssSelector("strong.campaign-price")).getCssValue("color");
        Dimension productRegularPriceSize = driver.findElement(By.cssSelector("s")).getSize();
        Dimension productCampaignPriceSize = driver.findElement(By.cssSelector("strong.campaign-price")).getSize();

        System.out.println("а) Совпадает название товара: " + (title.equals(productTitle)));
        System.out.println("б) Совпадают цены: обычная - " + (regularPrice.equals(productRegularPrice))
                + ", акционная - " + (campaignPrice.equals(productCampaignPrice)));
        System.out.println("в) Обычная цена зачеркнута и помечена серым цветом.\nГлавная страница: цвет - " + checkColor(regularPriceColor)
                + ", тэг элемента : " + regularPriceTag
                + "\nСтраница товара: цвет - " + checkColor(productRegularPriceColor) + ", тэг элемента : " + productRegularPriceTag);
        System.out.println("г) Акционная цена зачеркнута и помечена серым цветом.\nГлавная страница: цвет - " + checkColor(campaignPriceColor)
                + ", тэг элемента : " + campaignPriceTag
                + "\nСтраница товара: цвет - " + checkColor(productCampaignPriceColor) + ", тэг элемента : " + productCampaignPriceTag);
        System.out.println("д) Акционная цена крупнее. \nГлавная страница: " + isCampaignBigger(regularPriceTextSize, campaignPriceTextSize)
                + "\nСтраница продукта: " + isCampaignBigger(productRegularPriceSize, productCampaignPriceSize));

        driver.quit();
    }

    @After
    public void afterTest() {
        try {
            driver.close();
        }catch (Exception e) {
            System.out.println("Browser is already closed.");
        }

    }

    public String checkColor(String rgba) {

        if (rgba.matches("rgb.*\s0.\s0.*")) {
            return "RED";
        }
        String s = rgba.substring(rgba.indexOf("(") + 1, rgba.indexOf(")"));
        String[] rgbs = s.split(",\s");
        if (rgbs[0].equals(rgbs[1]) && rgbs[1].equals(rgbs[2])){
            return "GRAY";
        }
        return null;
    }

    public boolean isCampaignBigger(Dimension regularSize, Dimension campaignSize) {
        return regularSize.getWidth() < campaignSize.getWidth() &&
                regularSize.getHeight() < campaignSize.getHeight();
    }


}


