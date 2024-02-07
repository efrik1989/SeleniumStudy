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

public class GeoZoneSortCheck {
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
    public void countriesInAlphabeticalOrder() {
        driver.get("http://localhost/litecard/admin");

        //login
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        driver.findElement(By.linkText("Countries")).click();

        // Список Геозон
        List<WebElement> rows = driver.findElements(By.cssSelector("td table tr.row"));
        System.out.println("Проверка записей на странице.");
        boolean isSorted = isInAlphabeticalOrderCheck(rows, "xpath", "textContent");
        System.out.println("Сountries in alphabetical order: " + isSorted);


        List<String> moreZonesCountries = findCountriesMoreThanZeroZones(rows);
        for (String country : moreZonesCountries) {
            driver.findElement(By.linkText(country)).click();
            List<WebElement> zones = driver.findElements(By.cssSelector("table.dataTable tr td:nth-child(3)"));
            isSorted = isInAlphabeticalOrderCheck(zones, "none", "value");
            System.out.println(country + " zones in alphabetical order: " + isSorted);
            driver.findElement(By.linkText("Countries")).click();
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

    public List<String> findCountriesMoreThanZeroZones(List<WebElement> rows) {
        System.out.println("Поиск стран с количеством зон больше 0.");
        List<String> list = new LinkedList<>();

        for (WebElement element : rows) {
            int countZones = Integer.parseInt(element.findElement(By.xpath("./td[6]")).getAttribute("textContent"));
            if (countZones > 0) {
                String name = element.findElement(By.xpath("./td[5]/a")).getAttribute("textContent");
                list.add(name);
                System.out.println(name + ": " + countZones);
            }
        }
        return list;
    }

    //TODO: сделать общий метод проверки на сортировку.
    public boolean isInAlphabeticalOrderCheck(List<WebElement> list, String searchMethod, String attribute) {

        List<String> sortedList = new LinkedList<>();
        List<String> unsortedList = new LinkedList<>();
        for (WebElement element : list) {
            String str = switch (searchMethod) {
                case "xpath" -> element.findElement(By.xpath("./td[5]/a")).getAttribute(attribute);
                case "none" -> element.getAttribute(attribute);
                default -> throw new IllegalStateException("Unexpected value: " + searchMethod);
            };
            if (str!=null && !str.isEmpty()) {
                unsortedList.add(str);
                sortedList.add(str);
            }
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
