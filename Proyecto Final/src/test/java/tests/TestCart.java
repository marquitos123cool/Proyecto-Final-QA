package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;
import utils.TestData;


public class TestCart extends BaseTest {

    @BeforeClass
    public void iniciar() {
        System.out.println("\n========== CART TESTS ==========");
    }

    @AfterClass
    public void finalizar() {
        System.out.println("========== FIN CART TESTS ==========\n");
    }

    private InventoryPage loginYObtenerInventario() {
        LoginPage login = new LoginPage(driver, wait);
        login.login(TestData.USER_STANDARD, TestData.PASSWORD_VALID);
        InventoryPage inv = new InventoryPage(driver, wait);
        inv.waitForPage();
        return inv;
    }

    // TC-C01: Agregar un producto actualiza el badge del carrito ──────────────
    @Test(priority = 0, description = "TC-C01: Agregar un producto muestra badge '1' en el carrito")
    public void TC_C01_agregarProductoActualizaBadge() {
        InventoryPage inv = loginYObtenerInventario();

        // Antes de agregar: el badge no existe
        Assert.assertFalse(inv.isCartBadgeVisible(),
                "TC-C01 FAIL: El badge no debería estar visible antes de agregar");

        inv.addBackpackToCart();

        // Después de agregar: badge muestra "1"
        Assert.assertTrue(inv.isCartBadgeVisible(),
                "TC-C01 FAIL: El badge debería aparecer tras agregar producto");
        Assert.assertEquals(inv.getCartItemCount(), 1,
                "TC-C01 FAIL: Badge esperado '1'");

        System.out.println("TC-C01 PASS | Badge: " + inv.getCartItemCount());
    }

    // TC-C02: Agregar múltiples productos acumula en el carrito ───────────────
    @Test(priority = 1, description = "TC-C02: Agregar 3 productos muestra badge '3' y persisten en carrito")
    public void TC_C02_agregarMultiplesProductos() {
        InventoryPage inv = loginYObtenerInventario();

        inv.addBackpackToCart();
        inv.addBikeLightToCart();
        inv.addBoltShirtToCart();

        // Validar badge = 3
        Assert.assertEquals(inv.getCartItemCount(), 3,
                "TC-C02 FAIL: Badge esperado '3'");

        // Ir al carrito y verificar que los 3 productos están
        inv.goToCart();
        CartPage cart = new CartPage(driver, wait);
        cart.waitForPage();

        Assert.assertEquals(cart.getCartItemCount(), 3,
                "TC-C02 FAIL: El carrito debe tener 3 items");
        Assert.assertTrue(cart.containsProduct(TestData.PRODUCT_BACKPACK),
                "TC-C02 FAIL: Backpack no encontrado en el carrito");
        Assert.assertTrue(cart.containsProduct(TestData.PRODUCT_BIKE_LIGHT),
                "TC-C02 FAIL: Bike Light no encontrado en el carrito");

        System.out.println("TC-C02 PASS | Items en carrito: " + cart.getCartItemCount());
    }

    // TC-C03: Eliminar producto del carrito lo remueve correctamente ───────────
    @Test(priority = 2, description = "TC-C03: Eliminar un producto del carrito lo remueve y actualiza el badge")
    public void TC_C03_eliminarProductoDelCarrito() {
        InventoryPage inv = loginYObtenerInventario();

        inv.addBackpackToCart();
        inv.addBikeLightToCart();
        Assert.assertEquals(inv.getCartItemCount(), 2, "Setup TC-C03: badge debe ser 2");

        inv.goToCart();
        CartPage cart = new CartPage(driver, wait);
        cart.waitForPage();

        // Eliminar el primer item
        cart.removeFirstItem();

        // Queda solo 1 item
        Assert.assertEquals(cart.getCartItemCount(), 1,
                "TC-C03 FAIL: Debe quedar 1 item tras eliminar");

        System.out.println("TC-C03 PASS | Items restantes: " + cart.getCartItemCount());
    }

    // TC-C04: Carrito vacío — botón Continue Shopping regresa al inventario ───
    @Test(priority = 3, description = "TC-C04: Desde carrito vacío, 'Continue Shopping' regresa al inventario")
    public void TC_C04_carritoVacioContinueShopping() {
        InventoryPage inv = loginYObtenerInventario();

        // Ir al carrito sin agregar nada
        inv.goToCart();
        CartPage cart = new CartPage(driver, wait);
        cart.waitForPage();

        // Valida que el carrito está vacío
        Assert.assertTrue(cart.isCartEmpty(),
                "TC-C04 FAIL: El carrito debería estar vacío");

        // Valida título del carrito
        Assert.assertEquals(cart.getPageTitle(), "Your Cart",
                "TC-C04 FAIL: Título esperado 'Your Cart'");

        // Click Continue Shopping → regresa al inventario
        cart.clickContinueShopping();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"),
                "TC-C04 FAIL: Debió regresar al inventario");

        System.out.println("TC-C04 PASS | Continue Shopping regresó al inventario");
    }
}
