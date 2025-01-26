package project.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import project.BasePage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreatingBusinessTripPage extends BasePage {
    @FindBy(xpath = "//select[contains(@id,'crm_business_trip_businessUnit')]")
    private WebElement subdivision;

    @FindBy(xpath = "//*[@id='company-selector-show']")
    private WebElement organizationOpenListLink;

    @FindBy(xpath = "//*[contains(@id,'s2id_crm_business_trip_companySelector')]")
    private WebElement hostOrganization;

    @FindBy(xpath = "//label[text()='Заказ билетов']/..//*[@type = 'checkbox']")
    private WebElement taskTicketsOrderCheckbox;

    @FindBy(xpath = "//*[contains(@id, 'crm_business_trip_departureCity')]")
    private WebElement departureCityElement;

    @FindBy(xpath = "//*[contains(@id, 'crm_business_trip_arrivalCity')]")
    private WebElement arrivalCityElement;

    @FindBy(xpath = "//*[contains(@id, 'date_selector_crm_business_trip_departureDatePlan')]")
    private WebElement departurePlanDateElement;

    @FindBy(xpath = "//*[contains(@id, 'date_selector_crm_business_trip_returnDatePlan')]")
    private WebElement returnPlanDateElement;

    @FindBy(xpath = "//button[contains(text(),'Сохранить и закрыть') and @type='submit']")
    private WebElement saveAndCloseButtonElement;

    @FindBy(xpath = "//*[@class='control-label wrap']/*[text()='Командированные сотрудники']/../.." +
            "//*[contains(@id, 'crm_business_trip_users')]")
    private WebElement businessTripUsersValue;


    @FindBy(xpath = "//*[@class='control-label wrap']/*[text()='Командированные сотрудники']/../.." +
            "//*[@class='validation-failed']")
    private WebElement businessTripUsersError;

    /**
     * Заполнить поле Подразделение = Отдел внутренней разработки
     *
     * @param value значение
     */
    public void fillSelectFieldSubdivision(String value) {
        By targetSubdivisionBy = By.xpath(String.format(
                "//select[contains(@id,'crm_business_trip_businessUnit')]//*[text()='%s']", value));
        fillSelectField(subdivision, targetSubdivisionBy, value);
    }

    /**
     * Заполнить Принимающую организацию
     *
     * @param value значение
     */
    public void fillHostOrganization(String value) {
        organizationOpenListLink.click();
        By hostOrganizationTargetBy = By.xpath(String.format(
                "//*[contains(@class, 'select2-result-label') and text()='%s']", value));
        fillSelectField(hostOrganization, hostOrganizationTargetBy, value);
    }

    /**
     * Заполнить чекбокс "Заказ билетов"
     *
     * @param targetState значение чекбокса
     */
    public void setCheckboxTicketsOrder(boolean targetState) {
        setCheckboxInState(taskTicketsOrderCheckbox, targetState);
    }

    /**
     * Указать город прибытия
     *
     * @param value значение
     */
    public void fillArrivalCity(String value) {
        fillInputField(arrivalCityElement, value);
    }

    /**
     * Указать город выбытия
     *
     * @param value значение
     */
    public void fillDepartureCity(String value) {
        fillInputField(departureCityElement, value);
    }

    /**
     * Указать дату выезда
     *
     * @param value значение
     */
    public void fillDeparturePlanDate(String value) {
        fillDateField(departurePlanDateElement, value);
    }

    /**
     * Указать дату возвращения
     *
     * @param value значение
     */
    public void fillReturnPlanDate(String value) {
        fillDateField(returnPlanDateElement, value);
    }

    /**
     * Раздел Командированные сотрудники не заполнять - проверим, что поле не заполнено
     */
    public void checkBusinessTripUsersIsEmpty() {
        assertEquals("", businessTripUsersValue.getText(), "Поле Командированные сотрудники не пустое");
    }

    /**
     * Нажать "Сохранить и закрыть"
     */
    public void clickSaveAndClose() {
        saveAndCloseButtonElement.click();
        loading();
    }

    /**
     * Проверить, что на странице появилось сообщение: "Список командируемых сотрудников не может быть пустым"
     */
    public void checkBusinessTripUsersError() {
        wait.until(ExpectedConditions.visibilityOf(businessTripUsersError));
        String expectedErrorMessage = "Список командируемых сотрудников не может быть пустым";
        assertEquals(expectedErrorMessage,
                businessTripUsersError.getText(),
                String.format("Проверка ошибки <%s> не пройдена", expectedErrorMessage));
    }
}
