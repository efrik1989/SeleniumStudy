package selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class LoginTestScenario {
    WebElement email;
    WebElement password;
    WebElement loginButton;
    WebDriver driver;

    @Before
    void beforeTest() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);

        driver.get("http://localhost/litecard/admin");

        email = driver.findElement(By.name("username"));
        password = driver.findElement(By.name("password"));
        loginButton = driver.findElement(By.name("login"));

    }

    @Test
    void login() {
        email.sendKeys("admin");
        password.sendKeys("admin");
        loginButton.click();
        Assert.assertEquals("logged in as admin", driver.findElement(By.className("notice success")));
    }

    @Test
    void closeBrowser() {
        driver.close();
        String currentUrl;
        try {
            currentUrl = driver.getCurrentUrl();
        } catch (Exception e) {
            currentUrl = "browser is closed";
        }
        Assert.assertEquals("browser is closed", currentUrl);

    }
}
