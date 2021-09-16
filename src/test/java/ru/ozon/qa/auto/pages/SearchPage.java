package ru.ozon.qa.auto.pages;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SearchPage {
    public WebDriver driver;

    public SearchPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//a[@href='/cart']")
    private WebElement cart;

    @FindBy(xpath = "//a[@href='/cart']/span")
    private WebElement cartHint;

    @FindBy(xpath = "//div[contains(text(),'Цена')]/following-sibling::div//input[@qa-id='range-to']")
    private WebElement priceTo;

    @FindBy(xpath = "//div[contains(text(),'Бренды')]/following-sibling::div/span")
    public WebElement showBrands;

    @FindBy(xpath = "//div[contains(text(),'Бренды')]/following-sibling::div//input[contains(@class,'hlVC')]")
    public WebElement inputSearchBrand;

    @FindBy(xpath = "//div[contains(text(),'Бренды')]/following-sibling::div//label")
    public List<WebElement> listBrand;

    @FindBy(css = ".e1p5 .e3f8")
    public List<WebElement> listGoods;


    @Step("Ввести максимальную цену")
    public void inputPriceTo(String price) {
        priceTo.isDisplayed();
        priceTo.sendKeys(Keys.chord(Keys.CONTROL, "a"), price);
    }

    @Step("Открыть список брендов")
    public void clickShowBrands() {
        showBrands.isDisplayed();
        showBrands.click();
    }

    @Step("Ввести в поисковую строку брендов '{nameBrand}'")
    public void inputSearchBrandQuery(String nameBrand) {
        inputSearchBrand.isDisplayed();
        inputSearchBrand.sendKeys(Keys.chord(Keys.CONTROL, "a"), nameBrand);
    }

    @Step("Выбрать первый елемент списка")
    public void clickSearchResultBrandByIndex(int index) {
        listBrand.get(index).click();
    }

    @Step("Добавить 8 четных товаров")
    @Attachment()
    public ArrayList<String> addProducts() {
        ArrayList<String> listName = new ArrayList<String>();
        for (int i = 1; i < 16; i += 2) {
            WebElement product = listGoods.get(i);
            product.isDisplayed();
            WebElement addButton = product.findElement(By.cssSelector("div.a1h7 button"));
            WebElement name = product.findElement(By.cssSelector(".e3s5 a span span"));
            listName.add(name.getText());
            addButton.isDisplayed();
            addButton.click();
        }
        writeToFile(listName);
        getBytes("listProduct.txt");
        return listName;
    }

    public void waitUntilPageIsLoaded() {
        new WebDriverWait(driver, 30).until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    @Step("Открыть корзину")
    public void openCart() {
        cart.isDisplayed();
        cart.click();
    }

    @Step("Проверить число товаров, добавленных в корзину")
    public void checkCartHint(int number) {
        cartHint.isDisplayed();
        Assert.assertTrue(cartHint.getText().equals(Integer.toString(number)));
    }

    @Attachment
    public static byte[] getBytes(String resourceName) {
        try {
            return Files.readAllBytes(Paths.get("target", resourceName));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public synchronized void writeToFile(List<String> list) {
        String path = System.getProperty ("user.dir");

        try {
            File file = new File("target/listProduct.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            Path out = Paths.get( path + "/target/listProduct.txt");
            Files.write(out, list, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
