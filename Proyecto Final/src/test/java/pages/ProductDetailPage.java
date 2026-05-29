package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductDetailPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // ── Locators ──────────────────────────────────────────────────────────────
    private final By productName    = By.cssSelector("[data-test='inventory-item-name']");
    private final By productDesc    = By.cssSelector("[data-test='inventory-item-desc']");
    private final By productPrice   = By.cssSelector("[data-test='inventory-item-price']");
    private final By productImg     = By.cssSelector("img.inventory_details_img, [data-test='item-sauce-labs-backpack-img']");
    private final By btnAddToCart   = By.cssSelector("[data-test^='add-to-cart']");
    private final By btnRemove      = By.cssSelector("[data-test^='remove']");
    private final By btnBack        = By.id("back-to-products");
    private final By cartBadge      = By.cssSelector(".shopping_cart_badge");

    public ProductDetailPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait   = wait;
    }

    // ── Esperar carga de la página ────────────────────────────────────────────
    public void waitForPage() {
        wait.until(ExpectedConditions.urlContains("inventory-item"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(productName));
    }

    // ── Acciones ──────────────────────────────────────────────────────────────
    public void addToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(btnAddToCart)).click();
    }

    public void removeFromCart() {
        wait.until(ExpectedConditions.elementToBeClickable(btnRemove)).click();
    }

    public void clickBack() {
        wait.until(ExpectedConditions.elementToBeClickable(btnBack)).click();
    }

    // ── Consultas ─────────────────────────────────────────────────────────────
    public String getProductName() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(productName)).getText();
    }

    public String getProductDescription() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(productDesc)).getText();
    }

    public String getProductPrice() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(productPrice)).getText();
    }

    public boolean isProductImageVisible() {
        try { return driver.findElement(productImg).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    public boolean isAddToCartButtonVisible() {
        try { return driver.findElement(btnAddToCart).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    public boolean isRemoveButtonVisible() {
        try { return driver.findElement(btnRemove).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    public boolean isBackButtonVisible() {
        try { return driver.findElement(btnBack).isDisplayed(); }
        catch (Exception e) { return false; }
    }

    public int getCartCount() {
        try { return Integer.parseInt(driver.findElement(cartBadge).getText()); }
        catch (Exception e) { return 0; }
    }
}