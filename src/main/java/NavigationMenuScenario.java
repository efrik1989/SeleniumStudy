import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NavigationMenuScenario {
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
    public void menuCheck() {
        driver.get("http://localhost/litecard/admin");

        //login
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        //Получить список названий менюшек
        List<String> appNames = getMenuElementsNames("div#box-apps-menu-wrapper li#app-");
        for (String elementName : appNames) {
            driver.findElement(By.linkText(elementName)).click();
            driver.findElement(By.tagName("h1"));
            System.out.println("Menu element: " + elementName);
            //Получить список под меню каждого пункта
            List<String> subMenuNames = getMenuElementsNames("div#box-apps-menu-wrapper li#app- li");
            if (subMenuNames.size() > 0) {
                for (String name : subMenuNames) {
                    driver.findElement(By.linkText(name)).click();
                    driver.findElement(By.tagName("h1"));
                    System.out.println("Sub menu element:" + name);
                }
            }
        }
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

    public List<String> getMenuElementsNames(String cssSelector) {
        List<String> menuElementsNames = new LinkedList<>();
        List<WebElement> elements = driver.findElements(By.cssSelector(cssSelector));
        if (elements.size() > 0) {
            for (WebElement element : elements) {
                menuElementsNames.add(element.getText());
            }
        }
        return menuElementsNames;
    }

}
