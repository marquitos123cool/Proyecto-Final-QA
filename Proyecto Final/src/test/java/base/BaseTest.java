package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.ByteArrayInputStream;
import java.time.Duration;

/**
 * BaseTest — Configuración del WebDriver (base/)

 * Patrón POM:
 *   base/  → configuración del driver
 *   pages/ → Page Objects (locators + métodos)
 *   tests/ → casos de prueba + assertions
 *   utils/ → constantes y datos de prueba
 */
public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public static final String BASE_URL = "https://www.saucedemo.com/";

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // ---  Desactiva Leak Detection y Safe Browsing ---
        options.addArguments("--password-store=basic");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-infobars");

        // Apagar la función específica que detecta contraseñas filtradas
        options.addArguments("--disable-features=PasswordLeakDetection");

        // Ejecutar en incógnito asegura una sesión 100% limpia sin mi perfil
        options.addArguments("--incognito");

        java.util.Map<String, Object> prefs = new java.util.HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("password_manager_enabled", false);

        // Desactiva la alerta a nivel preferencias internas
        prefs.put("profile.password_manager_leak_detection", false);
        prefs.put("safebrowsing.enabled", false);

        options.setExperimentalOption("prefs", prefs);


        // Sin modo headless para que se vea la ejecución
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get(BASE_URL);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                byte[] screenshot = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Screenshot al fallar - " + result.getName(),
                        new ByteArrayInputStream(screenshot));
            } catch (Exception ignored) {}
            System.out.println("FAIL: " + result.getName());
            if (result.getThrowable() != null) {
                result.getThrowable().printStackTrace();
            }
        }
        if (driver != null) driver.quit();
    }
}