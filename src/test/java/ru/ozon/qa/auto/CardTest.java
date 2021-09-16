package ru.ozon.qa.auto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.ozon.qa.auto.pages.CartShoppingPage;
import ru.ozon.qa.auto.pages.MainPage;
import ru.ozon.qa.auto.pages.SearchPage;

import java.util.ArrayList;

public class CardTest extends BaseTest {
    public static MainPage mainPage;
    public static SearchPage searchPage;
    public static CartShoppingPage cartShoppingPage;

    @BeforeEach
    void setupPages() {
        mainPage = new MainPage(driver);
        searchPage = new SearchPage(driver);
        cartShoppingPage = new CartShoppingPage(driver);
    }

    @Test
    public void test_card() {
        mainPage.inputSearchQuery("Беспроводные наушники");
        mainPage.pressSearchButton();
        searchPage.inputPriceTo("10000");
        searchPage.clickShowBrands();
        searchPage.inputSearchBrandQuery("beats");
        searchPage.clickSearchResultBrandByIndex(0);
        searchPage.inputSearchBrandQuery("samsung");
        searchPage.clickSearchResultBrandByIndex(0);
        searchPage.waitUntilPageIsLoaded();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<String> listNameProduct = searchPage.addProducts();

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        searchPage.checkCartHint(listNameProduct.size());
        searchPage.openCart();

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cartShoppingPage.closePopup();
        cartShoppingPage.checkTitleCart(listNameProduct.size());
        cartShoppingPage.checkProduct(listNameProduct);
        cartShoppingPage.deleteAllProduct();
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cartShoppingPage.checkEmptyCart();

    }
}
