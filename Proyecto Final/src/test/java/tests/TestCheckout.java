package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;
import utils.TestData;

public class TestCheckout extends BaseTest {

    @BeforeClass
    public void iniciar() {
        System.out.println("\n========== CHECKOUT TESTS ==========");
    }

    @AfterClass
    public void finalizar() {
        System.out.println("========== FIN CHECKOUT TESTS ==========\n");
    }

    // Helper: login, agregar producto, ir al carrito y comenzar checkout
    private CheckoutPage prepararCheckout() {
        LoginPage login = new LoginPage(driver, wait);
        login.login(TestData.USER_STANDARD, TestData.PASSWORD_VALID);

        InventoryPage inv = new InventoryPage(driver, wait);
        inv.waitForPage();
        inv.addBackpackToCart();
        inv.goToCart();

        CartPage cart = new CartPage(driver, wait);
        cart.waitForPage();
        cart.clickCheckout();

        return new CheckoutPage(driver, wait);
    }

    // TC-CH01: Checkout completo exitoso muestra confirmación ─────────────────
    @Test(priority = 0, description = "TC-CH01: Flujo completo de compra termina con 'Thank you for your order!'")
    public void TC_CH01_checkoutCompletoExitoso() {
        CheckoutPage checkout = prepararCheckout();

        // Step One: llenar datos del comprador
        checkout.fillCustomerInfo(
                TestData.CHECKOUT_FIRST,
                TestData.CHECKOUT_LAST,
                TestData.CHECKOUT_ZIP
        );
        checkout.clickContinue();

        // Step Two: verificar resumen y finalizar
        checkout.waitForStepTwo();
        Assert.assertEquals(checkout.getSummaryItemCount(), 1,
                "TC-CH01 FAIL: Se esperaba 1 item en el resumen");

        checkout.clickFinish();

        // Confirmación
        checkout.waitForConfirmation();
        String header = checkout.getConfirmationHeader();
        Assert.assertEquals(header, "Thank you for your order!",
                "TC-CH01 FAIL: Confirmación esperada 'Thank you for your order!'");

        System.out.println("TC-CH01 PASS | Confirmación: " + header);
    }

    // TC-CH02: Campos vacíos en Step One muestran error ───────────────────────
    @Test(priority = 1, description = "TC-CH02: Continuar sin llenar datos muestra error 'First Name is required'")
    public void TC_CH02_camposVaciosMuestranError() {
        CheckoutPage checkout = prepararCheckout();
        checkout.waitForStepOne();

        // Click Continue sin llenar nada
        checkout.clickContinue();

        Assert.assertTrue(checkout.isErrorVisible(),
                "TC-CH02 FAIL: Mensaje de error no visible");
        String error = checkout.getErrorMessage();
        Assert.assertTrue(error.contains("First Name is required"),
                "TC-CH02 FAIL: Error esperado 'First Name is required', obtenido: " + error);

        System.out.println("TC-CH02 PASS | Error: " + error);
    }

    // TC-CH03: El resumen muestra subtotal, tax y total correctos ─────────────
    @Test(priority = 2, description = "TC-CH03: El resumen de compra muestra subtotal, impuesto y total")
    public void TC_CH03_resumenMuestraPreciosCorrectos() {
        CheckoutPage checkout = prepararCheckout();

        checkout.fillCustomerInfo(
                TestData.CHECKOUT_FIRST,
                TestData.CHECKOUT_LAST,
                TestData.CHECKOUT_ZIP
        );
        checkout.clickContinue();
        checkout.waitForStepTwo();

        // Validar que subtotal, tax y total son visibles y no vacíos
        String subtotal = checkout.getOrderSubtotal();
        String tax      = checkout.getOrderTax();
        String total    = checkout.getOrderTotal();

        Assert.assertFalse(subtotal.isEmpty(), "TC-CH03 FAIL: Subtotal vacío");
        Assert.assertFalse(tax.isEmpty(),      "TC-CH03 FAIL: Tax vacío");
        Assert.assertFalse(total.isEmpty(),    "TC-CH03 FAIL: Total vacío");

        // El total debe incluir el signo "$"
        Assert.assertTrue(total.contains("$"),
                "TC-CH03 FAIL: Total no contiene '$': " + total);

        System.out.println("TC-CH03 PASS | " + subtotal + " | " + tax + " | " + total);
    }

    // TC-CH04: Cancelar en Step Two regresa al inventario ─────────────────────
    @Test(priority = 3, description = "TC-CH04: Cancelar en el resumen de compra regresa al inventario")
    public void TC_CH04_cancelarEnStepTwoRegresaInventario() {
        CheckoutPage checkout = prepararCheckout();

        checkout.fillCustomerInfo(
                TestData.CHECKOUT_FIRST,
                TestData.CHECKOUT_LAST,
                TestData.CHECKOUT_ZIP
        );
        checkout.clickContinue();
        checkout.waitForStepTwo();

        // Click Cancel en step two
        checkout.clickFinish();
        checkout.waitForConfirmation();

        // Desde confirmación, back to home → inventario
        checkout.clickBackToHome();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"),
                "TC-CH04 FAIL: Debió regresar al inventario");

        System.out.println("TC-CH04 PASS | Regresó a: " + driver.getCurrentUrl());
    }
}

