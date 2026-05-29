package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.TestData;

import java.util.List;

public class TestInventory extends BaseTest {

    @BeforeClass
    public void iniciar() {
        System.out.println("\n========== INVENTORY TESTS ==========");
    }

    @AfterClass
    public void finalizar() {
        System.out.println("========== FIN INVENTORY TESTS ==========\n");
    }

    private InventoryPage loginYObtenerInventario() {
        LoginPage login = new LoginPage(driver, wait);
        login.login(TestData.USER_STANDARD, TestData.PASSWORD_VALID);
        InventoryPage inv = new InventoryPage(driver, wait);
        inv.waitForPage();
        return inv;
    }

    // TC-I01: El inventario muestra exactamente 6 productos ───────────────────
    @Test(priority = 0, description = "TC-I01: El inventario muestra 6 productos disponibles")
    public void TC_I01_inventarioMuestraSeisProduc() {
        InventoryPage inv = loginYObtenerInventario();

        int count = inv.getProductCount();
        Assert.assertEquals(count, 6,
                "TC-I01 FAIL: Se esperaban 6 productos, se encontraron: " + count);

        // Validar que el dropdown de sorting está visible
        Assert.assertTrue(inv.isSortDropdownVisible(),
                "TC-I01 FAIL: Dropdown de sorting no visible");

        System.out.println("TC-I01 PASS | Productos encontrados: " + count);
    }

    // TC-I02: Sorting A-Z — nombres en orden ascendente ───────────────────────
    @Test(priority = 1, description = "TC-I02: Sorting A-Z ordena productos por nombre ascendente")
    public void TC_I02_sortingAZOrdenaNombresAscendente() {
        InventoryPage inv = loginYObtenerInventario();

        inv.sortBy(TestData.SORT_NAME_AZ);

        List<String> names = inv.getProductNames();
        // Verificar que la lista está ordenada A-Z
        for (int i = 0; i < names.size() - 1; i++) {
            Assert.assertTrue(
                    names.get(i).compareToIgnoreCase(names.get(i + 1)) <= 0,
                    "TC-I02 FAIL: '" + names.get(i) + "' debería ir antes de '" + names.get(i+1) + "'"
            );
        }
        System.out.println("TC-I02 PASS | Primer producto A-Z: " + names.get(0));
    }

    // TC-I03: Sorting Z-A — nombres en orden descendente ─────────────────────
    @Test(priority = 2, description = "TC-I03: Sorting Z-A ordena productos por nombre descendente")
    public void TC_I03_sortingZAOrdenaNombresDescendente() {
        InventoryPage inv = loginYObtenerInventario();

        inv.sortBy(TestData.SORT_NAME_ZA);

        List<String> names = inv.getProductNames();
        for (int i = 0; i < names.size() - 1; i++) {
            Assert.assertTrue(
                    names.get(i).compareToIgnoreCase(names.get(i + 1)) >= 0,
                    "TC-I03 FAIL: '" + names.get(i) + "' debería ir después de '" + names.get(i+1) + "'"
            );
        }
        System.out.println("TC-I03 PASS | Primer producto Z-A: " + names.get(0));
    }

    // TC-I04: Sorting precio bajo a alto ──────────────────────────────────────
    @Test(priority = 3, description = "TC-I04: Sorting precio ordena de menor a mayor")
    public void TC_I04_sortingPrecioAscendente() {
        InventoryPage inv = loginYObtenerInventario();

        inv.sortBy(TestData.SORT_PRICE_LOW);

        List<Double> prices = inv.getProductPrices();
        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue(prices.get(i) <= prices.get(i + 1),
                    "TC-I04 FAIL: $" + prices.get(i) + " debería ser <= $" + prices.get(i+1));
        }
        System.out.println("TC-I04 PASS | Precio más bajo: $" + prices.get(0)
                + " | Más alto: $" + prices.get(prices.size()-1));
    }
}

