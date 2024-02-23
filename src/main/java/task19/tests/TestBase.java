package task19.tests;

import org.junit.After;
import org.junit.Before;
import task19.app.Application;

public class TestBase {
    Application app;

    @Before
    public void beforeTest(){
        app = new Application();

    }

    @After
    public void afterTest() {
        try {
            app.quit();
        }catch (Exception e) {
            System.out.println("Browser is already closed.");
        }

    }
}
