import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.xml.datatype.DatatypeConstants;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RegistrationScenario {

    WebDriver driver;
    final static long DEFAULT_TIMEOUT = 5000;

    @Before
    public void beforeTest(){
        WebDriverManager.chromedriver().setup();
        //ChromeOptions options = new ChromeOptions();
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    }

    @Test
    public void registrationCheck() throws Exception {
        driver.get("http://localhost/litecard");

        System.out.println("Регистрация нового пользователя");
        //Регистрация нового пользователя
        driver.findElement(By.cssSelector("#box-account-login > div > form > table > tbody > tr:nth-child(5) > td > a")).click();
        driver.findElement(By.cssSelector("#create-account > div > form input[name=firstname]")).sendKeys("Andrew");
        driver.findElement(By.cssSelector("#create-account > div > form input[name=lastname]")).sendKeys("Talbot");
        driver.findElement(By.cssSelector("#create-account > div > form input[name=address1]")).sendKeys("2nd avenue");
        driver.findElement(By.cssSelector("#create-account > div > form input[name=postcode]")).sendKeys("55555");
        driver.findElement(By.cssSelector("#create-account > div > form input[name=city]")).sendKeys("Providance");
        WebElement el = driver.findElement(By.cssSelector("span[dir=ltr]"));
        Actions actions = new Actions(driver);
                actions.moveToElement(el).click().build().perform();

        driver.findElement(By.cssSelector("span.select2-search.select2-search--dropdown > input")).sendKeys("United States" + Keys.ENTER);

        String email = "something" + new Date().getTime() + "@some.com";
        driver.findElement(By.cssSelector("#create-account > div > form input[name=email]")).sendKeys(email);
        driver.findElement(By.cssSelector("#create-account > div > form input[name=phone]")).sendKeys("7775555555");
        driver.findElement(By.cssSelector("#create-account > div > form input[name=password]")).sendKeys("aaaaaa");
        driver.findElement(By.cssSelector("#create-account > div > form input[name=confirmed_password]")).sendKeys("aaaaaa");

        driver.findElement(By.cssSelector("#create-account > div button[name=create_account]")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.notice.success")));
        System.out.println("Регистрация успешна");

        //логаут
        driver.findElement(By.cssSelector("#box-account > div > ul > li:nth-child(4) > a")).click();
        System.out.println("Логаут");

        //логин под только что созданным покупателем.
        driver.findElement(By.name("email")).sendKeys("something@some.com");
        driver.findElement(By.name("password")).sendKeys("aaaaaa");
        driver.findElement(By.cssSelector("#box-account-login form button[name=login]")).click();
        System.out.println("Логин");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.notice.success")));

        //логаут
        driver.findElement(By.cssSelector("#box-account > div > ul > li:nth-child(4) > a")).click();
        System.out.println("Логаут");
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

