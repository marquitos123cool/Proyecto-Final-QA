package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // ── Locators ──────────────────────────────────────────────────────────────
    private final By pageTitle        = By.cssSelector(".title");
    private final By productNames     = By.cssSelector("[data-test='inventory-item-name']");
    private final By productPrices    = By.cssSelector("[data-test='inventory-item-price']");
    private final By productCards     = By.cssSelector(".inventory_item");
    private final By sortDropdown     = By.cssSelector("[data-test='product-sort-container']");
    private final By cartIcon         = By.cssSelector(".shopping_cart_link");
    private final By cartBadge        = By.cssSelector(".shopping_cart_badge");
    private final By burgerMenu       = By.id("react-burger-menu-btn");
    private final By logoutLink       = By.id("logout_sidebar_link");
    private final By btnAddBackpack   = By.id("add-to-cart-sauce-labs-backpack");
    private final By btnAddBikeLight  = By.id("add-to-cart-sauce-labs-bike-light");
    private final By btnAddBoltShirt  = By.id("add-to-cart-sauce-labs-bolt-t-shirt");
    private final By btnAddFleece     = By.id("add-to-cart-sauce-labs-fleece-jacket");
    private final By btnAddOnesie     = By.id("add-to-cart-sauce-labs-onesie");
    private final By btnAddRedShirt   = By.id("add-to-cart-test.allthethings()-t-shirt-(red)");

    public InventoryPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait   = wait;
    }

    // ── Esperar carga del inventario ──────────────────────────────────────────
    public void waitForPage() {
        wait.until(ExpectedConditions.urlContains("inventory"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
    }

    // ── Sorting dropdown ──────────────────────────────────────────────────────
    public void sortBy(String visibleText) {
        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(sortDropdown));
        new Select(dropdown).selectByVisibleText(visibleText);
    }

    public String getSelectedSortOption() {
        WebElement dropdown = driver.findElement(sortDropdown);
        return new Select(dropdown).getFirstSelectedOption().getText();
    }

    // ── Agregar productos al carrito ──────────────────────────────────────────
    public void addBackpackToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(btnAddBackpack)).click();
    }
    public void addBikeLightToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(btnAddBikeLight)).click();
    }
    public void addBoltShirtToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(btnAddBoltShirt)).click();
    }
    public void addFleeceToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(btnAddFleece)).click();
    }
    public void addOnesieToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(btnAddOnesie)).click();
    }
    public void addRedShirtToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(btnAddRedShirt)).click();
    }

    // Agregar producto por nombre de botón genérico
    public void addProductToCartByName(String productId) {
        By btn = By.id("add-to-cart-" + productId);
        wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
    }

    // ── Navegar a producto ────────────────────────────────────────────────────
    public void clickProductByName(String name) {
        By link = By.xpath("//div[@data-test='inventory-item-name' and text()='" + name + "']");
        wait.until(ExpectedConditions.elementToBeClickable(link)).click();
    }

    // ── Navegación ────────────────────────────────────────────────────────────
    public void goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();
    }

    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(burgerMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }

    // ── Consultas ─────────────────────────────────────────────────────────────
    public String getPageTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(pageTitle)).getText();
    }

    public int getProductCount() {
        return driver.findElements(productCards).size();
    }

    public int getCartItemCount() {
        try {
            return Integer.parseInt(driver.findElement(cartBadge).getText());
        } catch (Exception e) { return 0; }
    }

    public boolean isCartBadgeVisible() {
        try {
            return driver.findElement(cartBadge).isDisplayed();
        } catch (Exception e) { return false; }
    }

    public List<String> getProductNames() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productNames));
        return driver.findElements(productNames)
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productPrices));
        return driver.findElements(productPrices)
                .stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .collect(Collectors.toList());
    }

    public boolean isSortDropdownVisible() {
        try {
            return driver.findElement(sortDropdown).isDisplayed();
        } catch (Exception e) { return false; }
    }
}