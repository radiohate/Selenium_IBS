package project.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import project.BasePage;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class MainPage extends BasePage {
    @FindBy(xpath = "//*[@id='main-menu']//*[@class='title' and text()='Расходы']/..")
    private WebElement coastsInMainMenuItem;

    @FindBy(xpath = "//*[@id='main-menu']//*[@class='title' and text()='Расходы']/../..//ul[@class='dropdown-menu menu_level_1']")
    private WebElement dropDownList;


    @FindBy(xpath = "//*[@id='main-menu']//*[@class='title' and text()='Расходы']/../..//*[text()='Командировки']")
    private WebElement businessTripItem;

    @FindBy(xpath = "//*[contains(@id, 'grid-crm-business-trip-grid')]")
    private WebElement businessTripGrid;

    /**
     * Нажать на раздел Расходы
     */
    public void clickCosts() {
        coastsInMainMenuItem.click();
    }

    /**
     * В выплывающем окне раздела Расходы нажать на Командировки
     */
    public void chooseBusinessTrip() {
        wait.until(visibilityOf(dropDownList));
        businessTripItem.click();
        loading();
        wait.until(visibilityOf(businessTripGrid));
    }
}
