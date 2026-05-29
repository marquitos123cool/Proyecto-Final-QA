package utils;

/**
 * TestData — Constantes y datos de prueba centralizados (utils/)
 * Centralizar los datos evita duplicación y facilita el mantenimiento.
 * Si una credencial cambia, solo se edita aquí.
 */
public class TestData {

    // ── Credenciales válidas ──────────────────────────────────────────────────
    public static final String USER_STANDARD     = "standard_user";
    public static final String USER_LOCKED       = "locked_out_user";
    public static final String USER_PROBLEM      = "problem_user";
    public static final String USER_PERFORMANCE  = "performance_glitch_user";
    public static final String PASSWORD_VALID    = "secret_sauce";
    public static final String PASSWORD_INVALID  = "wrongpassword123";

    // ── Productos de SauceDemo ────────────────────────────────────────────────
    public static final String PRODUCT_BACKPACK  = "Sauce Labs Backpack";
    public static final String PRODUCT_BIKE_LIGHT= "Sauce Labs Bike Light";
    public static final String PRODUCT_BOLT_SHIRT= "Sauce Labs Bolt T-Shirt";
    public static final String PRODUCT_FLEECE    = "Sauce Labs Fleece Jacket";
    public static final String PRODUCT_ONESIE    = "Sauce Labs Onesie";
    public static final String PRODUCT_RED_SHIRT = "Test.allTheThings() T-Shirt (Red)";

    // ── Opciones de sorting ───────────────────────────────────────────────────
    public static final String SORT_NAME_AZ      = "Name (A to Z)";
    public static final String SORT_NAME_ZA      = "Name (Z to A)";
    public static final String SORT_PRICE_LOW    = "Price (low to high)";
    public static final String SORT_PRICE_HIGH   = "Price (high to low)";

    // ── Datos de checkout ─────────────────────────────────────────────────────
    public static final String CHECKOUT_FIRST    = "Juan";
    public static final String CHECKOUT_LAST     = "Pérez";
    public static final String CHECKOUT_ZIP      = "22000";

    // ── Mensajes esperados ────────────────────────────────────────────────────
    public static final String MSG_LOCKED_ERROR  = "Epic sadface: Sorry, this user has been locked out.";
    public static final String MSG_ORDER_COMPLETE = "Thank you for your order!";
    public static final String MSG_EMPTY_CART    = "";

    private TestData() {} // no instanciar
}