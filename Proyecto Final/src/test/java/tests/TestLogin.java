package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import utils.TestData;


public class TestLogin extends BaseTest {

    @BeforeClass
    public void iniciar() {
        System.out.println("\n========== LOGIN TESTS ==========");
    }

    @AfterClass
    public void finalizar() {
        System.out.println("========== FIN LOGIN TESTS ==========\n");
    }

    // TC-L01: Login exitoso con standard_user ─────────────────────────────────
    @Test(priority = 0, description = "TC-L01: Login exitoso redirige al inventario")
    public void TC_L01_loginExitosoRedirigaInventario() {
        LoginPage login = new LoginPage(driver, wait);
        InventoryPage inventory = new InventoryPage(driver, wait);

        login.login(TestData.USER_STANDARD, TestData.PASSWORD_VALID);
        inventory.waitForPage();

        // Validar URL
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"),
                "TC-L01 FAIL: No redirigió al inventario");
        // Validar título de la página
        Assert.assertEquals(inventory.getPageTitle(), "Products",
                "TC-L01 FAIL: Título esperado 'Products'");

        System.out.println("TC-L01 PASS | URL: " + driver.getCurrentUrl());
    }

    // TC-L02: Usuario bloqueado muestra error específico ──────────────────────
    @Test(priority = 1, description = "TC-L02: Usuario bloqueado muestra mensaje de error")
    public void TC_L02_usuarioBloqueadoMuestraError() {
        LoginPage login = new LoginPage(driver, wait);

        login.login(TestData.USER_LOCKED, TestData.PASSWORD_VALID);

        // Validar que el error específico de bloqueo aparece
        String error = login.getErrorMessage();
        Assert.assertTrue(error.contains("locked out"),
                "TC-L02 FAIL: Mensaje esperado contiene 'locked out', obtenido: " + error);
        // Validar que NO redirigió al inventario
        Assert.assertFalse(driver.getCurrentUrl().contains("inventory"),
                "TC-L02 FAIL: Usuario bloqueado no debió entrar al inventario");

        System.out.println("TC-L02 PASS | Error: " + error);
    }

    // TC-L03: Contraseña incorrecta muestra error ─────────────────────────────
    @Test(priority = 2, description = "TC-L03: Contraseña incorrecta muestra 'Username and password do not match'")
    public void TC_L03_passwordIncorrectoMuestraError() {
        LoginPage login = new LoginPage(driver, wait);

        login.login(TestData.USER_STANDARD, TestData.PASSWORD_INVALID);

        String error = login.getErrorMessage();
        Assert.assertTrue(error.contains("do not match"),
                "TC-L03 FAIL: Error esperado contiene 'do not match', obtenido: " + error);
        Assert.assertTrue(login.isErrorVisible(),
                "TC-L03 FAIL: El ícono de error no está visible");

        System.out.println("TC-L03 PASS | Error: " + error);
    }

    // TC-L04: Login sin credenciales muestra error ────────────────────────────
    @Test(priority = 3, description = "TC-L04: Campos vacíos muestran error de campo requerido")
    public void TC_L04_sinCredencialesMuestraError() {
        LoginPage login = new LoginPage(driver, wait);
        login.waitForPage();

        // Click sin llenar nada
        login.clickLogin();

        String error = login.getErrorMessage();
        Assert.assertTrue(error.contains("Username is required"),
                "TC-L04 FAIL: Error esperado 'Username is required', obtenido: " + error);
        Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo.com"),
                "TC-L04 FAIL: Debió permanecer en la página de login");

        System.out.println("TC-L04 PASS | Error: " + error);
    }

    // TC-L05: Validación UI del formulario de login ───────────────────────────
    @Test(priority = 4, description = "TC-L05: Todos los elementos del login son visibles y funcionales")
    public void TC_L05_validacionUIFormularioLogin() {
        LoginPage login = new LoginPage(driver, wait);
        login.waitForPage();

        Assert.assertTrue(login.isUsernameFieldVisible(),
                "TC-L05 FAIL: Campo Username no visible");
        Assert.assertTrue(login.isPasswordFieldVisible(),
                "TC-L05 FAIL: Campo Password no visible");
        Assert.assertTrue(login.isLoginButtonVisible(),
                "TC-L05 FAIL: Botón Login no visible");
        Assert.assertTrue(login.isLoginButtonEnabled(),
                "TC-L05 FAIL: Botón Login no está habilitado");

        // Validar logo de SauceDemo
        String logo = login.getLogoText();
        Assert.assertTrue(logo.contains("Swag Labs"),
                "TC-L05 FAIL: Logo esperado 'Swag Labs', obtenido: " + logo);

        System.out.println("TC-L05 PASS | Logo: " + logo);
    }
}

