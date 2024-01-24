import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
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
    final static long DEFAULT_TIMEOUT = 5000;

    @Before
    public void beforeTest() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);

    }

    @Test
    public void litecartLoginCheck() {
        driver.get("http://localhost/litecard/admin");

        try {
            Thread.sleep(DEFAULT_TIMEOUT);

            email = driver.findElement(By.name("username"));
            password = driver.findElement(By.name("password"));
            loginButton = driver.findElement(By.name("login"));

            email.sendKeys("admin");
            password.sendKeys("admin");
            loginButton.click();

            Thread.sleep(DEFAULT_TIMEOUT);

        } catch (InterruptedException e) {
        e.printStackTrace();
    }
        driver.close();

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
