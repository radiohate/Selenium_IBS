package project.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import project.BasePage;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

public class BusinessTripPage extends BasePage {
    @FindBy(xpath = "//a[@title='Создать командировку']")
    private WebElement createBusinessTripButton;

    @FindBy(xpath = "//h1[contains(text(),'Создать командировку')]")
    private WebElement createBusinessTripHeader;

    /**
     * Нажать на  Создать командировку
     */
    public void clickCreateBusinessTripButton() {
        createBusinessTripButton.click();
        loading();
    }

    /**
     * Проверить наличие на странице заголовка "Создать командировку"
     */
    public void checkCreateBusinessTripHeader() {
        wait.until(visibilityOf(createBusinessTripHeader));
    }
}
