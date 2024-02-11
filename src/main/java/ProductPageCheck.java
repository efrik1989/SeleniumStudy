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

        boolean isCampaignStronger = false;
        if (regularPriceTextSize.getWidth() < campaignPriceTextSize.getWidth() &&
                regularPriceTextSize.getHeight() < campaignPriceTextSize.getHeight()) {
            isCampaignStronger = true;
        }

        System.out.println(title + " \n"
                + regularPrice + "\n"
                + checkColor(regularPriceColor) + " " + regularPriceTag + "\n"
                + campaignPrice + "\n"
                + checkColor(campaignPriceColor) + " " + campaignPriceTag + "\n"
                + regularPriceTextSize + " "
                + campaignPriceTextSize + " " + isCampaignStronger);


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


}


