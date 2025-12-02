package app.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

class SeleniumPostDeployTest {

    @Test
    @Tag("selenium")
    void deveAcessarEndpointsPrincipais() {
        String baseUrl = System.getenv("APP_URL");
        Assumptions.assumeTrue(baseUrl != null && !baseUrl.isBlank(), "APP_URL não configurada, ignorando teste Selenium");

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);
        try {
            driver.get(baseUrl + "/categories");
            String source = driver.getPageSource();
            assertNotNull(source);

            driver.get(baseUrl + "/products");
            String sourceProducts = driver.getPageSource();
            assertNotNull(sourceProducts);
        } finally {
            driver.quit();
        }
    }
}
