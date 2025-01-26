package project;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static project.properties.TestProperties.getProperty;

public class DriverManager {
    private static WebDriver driver;
    public static WebDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
        return driver;
    }

    public static void initDriver() {
        System.setProperty(getProperty("webDriver"), getProperty("webDriverPath"));
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-web-security");
        driver = new ChromeDriver(options);
        driver.get(getProperty("url"));
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    public static void closeDriver() {
        driver.quit();
    }
}
