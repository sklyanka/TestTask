package ru.ozon.qa.auto.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    public WebDriver driver;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//input[@placeholder='Искать на Ozon']")
    private WebElement searchInput;

    @FindBy(css = "button.f9k")
    private WebElement searchButton;

    @Step("Ввести в поисковую строку {query}")
    public void inputSearchQuery(String query) {
        searchInput.isDisplayed();
        searchInput.sendKeys(query);
    }

    @Step("Нажать на кнопку поиска")
    public void pressSearchButton() {
        searchButton.isDisplayed();
        searchButton.isEnabled();
        searchButton.click();
    }

    public void waitUntilPageIsLoaded() {
        new WebDriverWait(driver, 30).until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }
}
