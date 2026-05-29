package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // ── Locators Step One ─────────────────────────────────────────────────────
    private final By txtFirstName  = By.id("first-name");
    private final By txtLastName   = By.id("last-name");
    private final By txtZipCode    = By.id("postal-code");
    private final By btnContinue   = By.id("continue");
    private final By btnCancel     = By.id("cancel");
    private final By lblError      = By.cssSelector("[data-test='error']");
    private final By pageTitle     = By.cssSelector(".title");

    // ── Locators Step Two  ───────────────────────────────────────────
    private final By summaryItems      = By.cssSelector(".cart_item");
    private final By summaryTotal      = By.cssSelector(".summary_total_label");
    private final By summarySubtotal   = By.cssSelector(".summary_subtotal_label");
    private final By summaryTax        = By.cssSelector(".summary_tax_label");
    private final By btnFinish         = By.id("finish");
    private final By btnBackToCart     = By.id("cancel");

    // ── Locators Confirmación ─────────────────────────────────────────────────
    private final By confirmHeader     = By.cssSelector(".complete-header");
    private final By confirmText       = By.cssSelector(".complete-text");
    private final By btnBackHome       = By.id("back-to-products");

    public CheckoutPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait   = wait;
    }

    // ── Métodos Step One ──────────────────────────────────────────────────────
    public void waitForStepOne() {
        wait.until(ExpectedConditions.urlContains("checkout-step-one"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtFirstName));
    }

    public void enterFirstName(String name) {
        wait.until(ExpectedConditions.elementToBeClickable(txtFirstName))
                .sendKeys(name);
    }

    public void enterLastName(String name) {
        wait.until(ExpectedConditions.elementToBeClickable(txtLastName))
                .sendKeys(name);
    }

    public void enterZipCode(String zip) {
        wait.until(ExpectedConditions.elementToBeClickable(txtZipCode))
                .sendKeys(zip);
    }

    public void fillCustomerInfo(String firstName, String lastName, String zip) {
        waitForStepOne();
        enterFirstName(firstName);
        enterLastName(lastName);
        enterZipCode(zip);
    }

    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();
    }

    public void clickCancel() {
        wait.until(ExpectedConditions.elementToBeClickable(btnCancel)).click();
    }

    public String getErrorMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(lblError)).getText();
    }

    public boolean isErrorVisible() {
        try { return driver.findElement(lblError).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    // ── Métodos Step Two ───────────────────────────────────────────────
    public void waitForStepTwo() {
        wait.until(ExpectedConditions.urlContains("checkout-step-two"));
    }

    public String getOrderTotal() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(summaryTotal)).getText();
    }

    public String getOrderSubtotal() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(summarySubtotal)).getText();
    }

    public String getOrderTax() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(summaryTax)).getText();
    }

    public int getSummaryItemCount() {
        return driver.findElements(summaryItems).size();
    }

    public void clickFinish() {
        wait.until(ExpectedConditions.elementToBeClickable(btnFinish)).click();
    }

    // ── Métodos Confirmación ──────────────────────────────────────────────
    public void waitForConfirmation() {
        wait.until(ExpectedConditions.urlContains("checkout-complete"));
    }

    public String getConfirmationHeader() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(confirmHeader)).getText();
    }

    public String getConfirmationText() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(confirmText)).getText();
    }

    public void clickBackToHome() {
        wait.until(ExpectedConditions.elementToBeClickable(btnBackHome)).click();
    }

    // ── Consultas generales ─────────────────────────────────────────────────
    public String getPageTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(pageTitle)).getText();
    }
}