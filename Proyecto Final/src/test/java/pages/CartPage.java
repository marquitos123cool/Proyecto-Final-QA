package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // ── Locators ──────────────────────────────────────────────────────────────
    private final By pageTitle        = By.cssSelector(".title");
    private final By cartItems        = By.cssSelector(".cart_item");
    private final By cartItemNames    = By.cssSelector(".inventory_item_name");
    private final By cartItemPrices   = By.cssSelector(".inventory_item_price");
    private final By cartItemQtys     = By.cssSelector(".cart_quantity");
    private final By btnCheckout      = By.id("checkout");
    private final By btnContinueShopping = By.id("continue-shopping");
    private final By removeButtons    = By.cssSelector("[id^='remove-']");

    public CartPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait   = wait;
    }

    // ── Espera la carga del carrito ─────────────────────────────────────────────
    public void waitForPage() {
        wait.until(ExpectedConditions.urlContains("cart"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
    }

    // ── Acciones ──────────────────────────────────────────────────────────────
    public void clickCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(btnCheckout)).click();
    }

    public void clickContinueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(btnContinueShopping)).click();
    }

    public void removeItemByName(String productName) {
        String id = "remove-" + productName.toLowerCase()
                .replace(" ", "-")
                .replace("(", "")
                .replace(")", "")
                .replace(".", "");
        wait.until(ExpectedConditions.elementToBeClickable(By.id(id))).click();
    }

    public void removeFirstItem() {
        List<WebElement> btns = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(removeButtons));
        if (!btns.isEmpty()) btns.get(0).click();
    }

    // ── Consultas ─────────────────────────────────────────────────────────────
    public String getPageTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(pageTitle)).getText();
    }

    public int getCartItemCount() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cartItems));
            return driver.findElements(cartItems).size();
        } catch (Exception e) { return 0; }
    }

    public List<String> getCartItemNames() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cartItemNames));
            return driver.findElements(cartItemNames)
                    .stream().map(WebElement::getText).collect(Collectors.toList());
        } catch (Exception e) { return List.of(); }
    }

    public boolean isCartEmpty() {
        return driver.findElements(cartItems).isEmpty();
    }

    public boolean isCheckoutButtonVisible() {
        try {
            return driver.findElement(btnCheckout).isDisplayed();
        } catch (Exception e) { return false; }
    }

    public boolean containsProduct(String productName) {
        return getCartItemNames().contains(productName);
    }

    public String getFirstItemQuantity() {
        try {
            return driver.findElements(cartItemQtys).get(0).getText();
        } catch (Exception e) { return "0"; }
    }
}