import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GeoZonesPageSortCheck {

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
    public void geoZonesSortCheck() {
        driver.get("http://localhost/litecard/admin");

        //login
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        driver.findElement(By.linkText("Geo Zones")).click();

        List<String> countries = new LinkedList<>();
        for (WebElement element : driver.findElements(By.cssSelector("form[name=geo_zones_form] tr.row td:nth-child(3) a"))) {
            countries.add(element.getAttribute("textContent"));
        }

        for (String name : countries) {
            driver.findElement(By.linkText(name)).click();
            List<WebElement> zones = new LinkedList<>();
            for (WebElement element : driver.findElements(By.cssSelector("#table-zones tr td:nth-child(3) select"))) {
                element.click();
                zones.add(element.findElement(By.cssSelector("option[selected]")));
            }

            boolean isSorted = isInAlphabeticalOrderCheck(zones, "textContent");
            System.out.println(name + " geo zones is sorted: " + isSorted);
            driver.findElement(By.linkText("Geo Zones")).click();
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

    public boolean isInAlphabeticalOrderCheck(List<WebElement> list,  String attribute) {

        List<String> sortedList = new LinkedList<>();
        List<String> unsortedList = new LinkedList<>();
        for (WebElement element : list) {
            String str = element.getAttribute(attribute);
            unsortedList.add(str);
            sortedList.add(str);
        }
        Collections.sort(sortedList);
        boolean isSorted = true;
        for (int j = 0; j < unsortedList.size(); j++) {
            if (!unsortedList.get(j).equals(sortedList.get(j))) {
                isSorted = false;
                break;
            }
        }
        return isSorted;
    }

}
