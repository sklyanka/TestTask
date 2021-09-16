package ru.ozon.qa.auto.pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartShoppingPage {

    private WebDriver driver;

    public CartShoppingPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
    @FindBy(css = "div.b4k1")
    private WebElement titleCart;

    @FindBy(css = "div.b4k1")
    private WebElement titleHintCart;

    @FindBy(css = "div.b4k6 h1")
    private WebElement emptyCartH1;

    @FindBy(css = "span.b4i9")
    private WebElement deleteAllButton;

    @FindBy(css = ".a7m4")
    public List<WebElement> listProduct;

    @FindBy(xpath = "//div[@data-widget='alertPopup']//div[@class='g5c7']//button")
    public WebElement closePopupButton;

    @Step("Проверить, что товары в корзине")
    public void checkProduct(List<String> listName) {
        int count = 0;
        for(int i = 0; i<listProduct.size(); i++) {
            WebElement productInCart = listProduct.get(i).findElement(By.cssSelector("a.a7n3 span"));
            productInCart.isDisplayed();
            for (String name : listName) {
                if(productInCart.getText().equals(name)) {
                    count++;
                    break;
                }
            }
        }
        Assert.assertTrue(count == listProduct.size());
    }

    @Step("Проверить, что товары в корзине")
    public void checkTitleCart(int countProduct) {
        titleCart.isDisplayed();
        Assert.assertTrue(titleCart.getText().equals("Корзина"));
        titleHintCart.isDisplayed();
        Assert.assertTrue(titleHintCart.getText().equals(Integer.toString(countProduct)));
    }

    @Step("Удалить все товары в корзине")
    public void deleteAllProduct() {
        deleteAllButton.isDisplayed();
        deleteAllButton.click();
    }

    @Step("Закрыть попап")
    public void closePopup() {
        closePopupButton.isDisplayed();
        closePopupButton.click();
    }

    @Step("Проверить, что корзина пуста")
    public void checkEmptyCart() {
        emptyCartH1.isDisplayed();
        Assert.assertTrue(emptyCartH1.getText().contains("Корзина пуста"));
    }

}
