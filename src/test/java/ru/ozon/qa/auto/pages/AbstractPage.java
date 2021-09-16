package ru.ozon.qa.auto.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

abstract class AbstractPage<T> {
    public WebDriver driver;

    public AbstractPage(WebDriver driver, T page) {
        PageFactory.initElements(driver, page);
        this.driver = driver;
    }
}
