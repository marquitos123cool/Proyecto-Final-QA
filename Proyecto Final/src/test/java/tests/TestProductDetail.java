package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import pages.ProductDetailPage;
import utils.TestData;


public class TestProductDetail extends BaseTest {

    @BeforeClass
    public void iniciar() {
        System.out.println("\n========== PRODUCT DETAIL TESTS ==========");
    }

    @AfterClass
    public void finalizar() {
        System.out.println("========== FIN PRODUCT DETAIL TESTS ==========\n");
    }

    private InventoryPage loginYObtenerInventario() {
        LoginPage login = new LoginPage(driver, wait);
        login.login(TestData.USER_STANDARD, TestData.PASSWORD_VALID);
        InventoryPage inv = new InventoryPage(driver, wait);
        inv.waitForPage();
        return inv;
    }

    // TC-P01: La página de detalle muestra nombre, precio e imagen del producto
    @Test(priority = 0, description = "TC-P01: Página de detalle muestra elementos del producto")
    public void TC_P01_detalleProductoMuestraElementos() {
        InventoryPage inv = loginYObtenerInventario();
        inv.clickProductByName(TestData.PRODUCT_BACKPACK);

        ProductDetailPage detail = new ProductDetailPage(driver, wait);
        detail.waitForPage();

        // Validar que el nombre del producto es correcto
        String name = detail.getProductName();
        Assert.assertEquals(name, TestData.PRODUCT_BACKPACK,
                "TC-P01 FAIL: Nombre esperado '" + TestData.PRODUCT_BACKPACK + "', obtenido: " + name);

        // Validar que el precio es visible y tiene formato correcto
        String price = detail.getProductPrice();
        Assert.assertTrue(price.contains("$"),
                "TC-P01 FAIL: Precio no tiene formato '$': " + price);

        // Validar imagen y botones visibles
        Assert.assertTrue(detail.isProductImageVisible(),
                "TC-P01 FAIL: Imagen del producto no visible");
        Assert.assertTrue(detail.isAddToCartButtonVisible(),
                "TC-P01 FAIL: Botón Add to Cart no visible");
        Assert.assertTrue(detail.isBackButtonVisible(),
                "TC-P01 FAIL: Botón Back no visible");

        System.out.println("TC-P01 PASS | Producto: " + name + " | Precio: " + price);
    }

    // TC-P02: Agregar desde detalle actualiza el carrito ──────────────────────
    @Test(priority = 1, description = "TC-P02: Agregar producto desde detalle actualiza badge del carrito")
    public void TC_P02_agregarDesdeDetalleActualizaCarrito() {
        InventoryPage inv = loginYObtenerInventario();
        inv.clickProductByName(TestData.PRODUCT_FLEECE);

        ProductDetailPage detail = new ProductDetailPage(driver, wait);
        detail.waitForPage();

        // Badge debe estar en 0 antes de agregar
        Assert.assertEquals(detail.getCartCount(), 0,
                "TC-P02 FAIL: Badge debe ser 0 antes de agregar");

        detail.addToCart();

        // Badge debe ser 1 después de agregar
        Assert.assertEquals(detail.getCartCount(), 1,
                "TC-P02 FAIL: Badge debe ser 1 después de agregar");

        // El botón debe cambiar a "Remove"
        Assert.assertTrue(detail.isRemoveButtonVisible(),
                "TC-P02 FAIL: Botón Remove no visible después de agregar");
        Assert.assertFalse(detail.isAddToCartButtonVisible(),
                "TC-P02 FAIL: Botón Add to Cart debería desaparecer tras agregar");

        System.out.println("TC-P02 PASS | Badge tras agregar: " + detail.getCartCount());
    }

    // TC-P03: Botón Back regresa al inventario conservando el estado ──────────
    @Test(priority = 2, description = "TC-P03: Botón Back desde detalle regresa al inventario")
    public void TC_P03_botonBackRegresaAlInventario() {
        InventoryPage inv = loginYObtenerInventario();
        inv.clickProductByName(TestData.PRODUCT_ONESIE);

        ProductDetailPage detail = new ProductDetailPage(driver, wait);
        detail.waitForPage();

        // Verificar que estamos en la página de detalle
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory-item"),
                "TC-P03 FAIL: Debería estar en la página de detalle");

        // Click Back
        detail.clickBack();

        // Verificar que regresó al inventario
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"),
                "TC-P03 FAIL: Debió regresar al inventario");

        // El inventario sigue mostrando 6 productos
        inv.waitForPage();
        Assert.assertEquals(inv.getProductCount(), 6,
                "TC-P03 FAIL: El inventario debe tener 6 productos");

        System.out.println("TC-P03 PASS | Regresó al inventario con " + inv.getProductCount() + " productos");
    }
}
