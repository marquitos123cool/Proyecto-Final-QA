package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // ── Locators ──────────────────────────────────────────────────────────────
    private final By txtUsername  = By.id("user-name");
    private final By txtPassword  = By.id("password");
    private final By btnLogin     = By.id("login-button");
    private final By lblError     = By.cssSelector("[data-test='error']");
    private final By lblTitle     = By.cssSelector(".login_logo");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait   = wait;
    }

    // ── Métodos de interacción ────────────────────────────────────────────────

    public void waitForPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtUsername));
    }

    public void enterUsername(String username) {
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(txtUsername));
        field.clear();
        field.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(txtPassword));
        field.clear();
        field.sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(btnLogin)).click();
    }

    public void login(String username, String password) {
        waitForPage();
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // ── Métodos de consulta ───────────────────────────────────────────────────

    public String getErrorMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(lblError)).getText();
    }

    public boolean isErrorVisible() {
        try {
            return driver.findElement(lblError).isDisplayed();
        } catch (Exception e) { return false; }
    }

    public String getLogoText() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(lblTitle)).getText();
    }

    public boolean isUsernameFieldVisible() {
        return driver.findElement(txtUsername).isDisplayed();
    }

    public boolean isPasswordFieldVisible() {
        return driver.findElement(txtPassword).isDisplayed();
    }

    public boolean isLoginButtonVisible() {
        return driver.findElement(btnLogin).isDisplayed();
    }

    public boolean isLoginButtonEnabled() {
        return driver.findElement(btnLogin).isEnabled();
    }
}