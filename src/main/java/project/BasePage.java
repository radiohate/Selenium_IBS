package project;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.PageFactory.initElements;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static project.DriverManager.getDriver;
import static project.utils.FormatUtils.convertDateFormat;

public class BasePage {
    protected WebDriver driver = getDriver();
    protected final WebDriverWait wait = new WebDriverWait(driver, 15);

    public BasePage() {
        initElements(driver, this);
    }

    @FindBy(xpath = "//*[@class='loader-mask shown']")
    private WebElement loaderSpinner;

    /**
     * Ожидание исчезновения спиннера
     */
    protected void loading() {
        wait.until(invisibilityOf(loaderSpinner));
    }

    /**
     * Заполнение поля определённым значением
     *
     * @param element - веб элемент (поле какое-то) которое планируем заполнить)
     * @param value   - значение которы мы заполняем веб элемент (поле какое-то)
     */
    protected void fillInputField(WebElement element, String value) {
        scrollToElementJs(element);
        wait.until(elementToBeClickable(element));
        element.click();
        element.clear();
        element.sendKeys(value);
        boolean checkFlag = wait.until(attributeContains(element, "value", value));
        assertTrue(checkFlag,String.format("Поле заполнено %s некорректно. Ожидали значение <%s>,", element, value));
    }

    /**
     * Скрол до элемента на js коде
     *
     * @param element - веб элемент до которого нужно проскролить
     */
    private void scrollToElementJs(WebElement element) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Установка checkbox в указанное состояние
     *
     * @param checkBoxElement элемент типа checkBox
     * @param targetState     состояние, в которое хотим установить checkBox
     */
    public static void setCheckboxInState(WebElement checkBoxElement, boolean targetState) {
        // Проверяем, находится ли checkbox в нужном состоянии
        if (checkBoxElement.isSelected() != targetState) {
            // Если не находится, кликаем по нему
            checkBoxElement.click();
        }
        assertEquals(checkBoxElement.isSelected(), targetState,
                String.format("Поле заполнено %s некорректно. Ожидали значение <%s>,", checkBoxElement, targetState));
    }

    /**
     * Заполнение полей выбором из списка
     *
     * @param element       веб элемент, который планируем заполнить
     * @param targetSubdivisionBy отбор элемента, который надо выбрать
     * @param value значение
     */
    protected void fillSelectField(WebElement element, By targetSubdivisionBy, String value) {
        scrollToElementJs(element);
        element.click();
        WebElement targetValueElement = driver.findElement(targetSubdivisionBy);
        scrollToElementJs(targetValueElement);
        wait.until(elementToBeClickable(targetValueElement));
        targetValueElement.click();
        boolean checkFlag = wait.until(textToBePresentInElement(element, value));
        assertTrue(checkFlag,
                String.format("Поле заполнено %s некорректно. Ожидали значение <%s>,", element, value));
    }

    /**
     * Заполнить поле даты
     *
     * @param element   элемент, который заполняем значением даты
     * @param dateValue значение даты
     */
    protected void fillDateField(WebElement element, String dateValue) {
        scrollToElementJs(element);
        wait.until(elementToBeClickable(element));
        element.click();
        element.clear();
        element.sendKeys(dateValue);
        String day = dateValue.split("\\.")[0];
        String dayInCalendarXpath = String.format("//table[@class='ui-datepicker-calendar']//*[text()='%s']", day);
        WebElement dayInCalendarElement = driver.findElement(By.xpath(dayInCalendarXpath));
        dayInCalendarElement.click();
        WebElement elementValue = element.findElement(By.xpath("./../span/input"));
        boolean checkFlag = wait.until(attributeContains(elementValue,
                "value",
                convertDateFormat(dateValue)));
        assertTrue(checkFlag, String.format("Поле заполнено %s некорректно. Ожидали значение <%s>,", element, dateValue));
    }
}
