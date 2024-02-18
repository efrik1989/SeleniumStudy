import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class NewWindowCheckScenario {
    WebDriverWait wait;
    WebDriver driver;

    @Before
    public void beforeTest() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));

    }

    @Test
    public void newWindowOpenCheck() {
        driver.get("http://localhost/litecard/admin");

        //login
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        driver.findElement(By.linkText("Countries")).click();
        driver.findElement(By.cssSelector("#content td:nth-child(5) > a")).click();

        List<WebElement> links = driver.findElements(By.cssSelector("td#content form table a[target=_blank]"));
        String mainWindow = driver.getWindowHandle();
        System.out.println("Главное окно: " + mainWindow);
        for (WebElement element : links) {
            element.click();
            Set<String> windowsId = driver.getWindowHandles();
            for (String id : windowsId) {
                if(!id.equals(mainWindow)) driver.switchTo().window(id);
            }
            if (wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body"))).isDisplayed());
            System.out.println("Окно открылось: " + (windowsId.size() > 1));

            String currentWindow = driver.getWindowHandle();
            System.out.println("Текущее окно: " + currentWindow);
            if (!currentWindow.equals(mainWindow)) {
                driver.close();
            }
            driver.switchTo().window(mainWindow);

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

}

