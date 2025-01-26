package ru.ibs.appline.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class ExampleScenarioTest {

    private static final String USER_KEY = "user1";
    private static final String PASS_KEY = "pass1";
    private static final String DEPARTMENT_VALUE = "Отдел внутренней разработки";
    private static final String HOST_ORGANIZATION_VALUE = "(Edge) Призрачная Организация Охотников";
    private static final String DEPARTURE_CITY_VALUE = "Москва";
    private static final String ARRIVAL_CITY_VALUE = "Калининград";
    private static final String DEPARTURE_DATE_VALUE = "10.02.2025";
    private static final String RETURN_DATE_VALUE = "14.02.2025";

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void before() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/webdriver/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-web-security");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 15, 1000);
        String baseUrl = getProperty("url");
        driver.get(baseUrl);
    }

    @AfterEach
    public void after(){
        driver.quit();
    }

    @Test
    public void trainingTest() {
        // Пройти авторизацию
        login(getProperty(USER_KEY), getProperty(PASS_KEY));
        // Проверить наличие на странице заголовка Панель быстрого запуска
        wait.until(visibilityOfElementLocated(
                By.xpath("//h1[contains(text(),'Панель быстрого запуска')]")));
        // В выплывающем окне раздела Расходы нажать на Командировки
        String mainMenuItemXpath = "//*[@id='main-menu']//*[@class='title' and text()='%s']/..";
        WebElement mainMenuItem = driver.findElement(By.xpath(String.format(mainMenuItemXpath, "Расходы")));
        mainMenuItem.click();
        WebElement businessTripItem = mainMenuItem.findElement(By.xpath("./..//*[text()='Командировки']"));
        businessTripItem.click();
        loading();
        wait.until(visibilityOfElementLocated(By.xpath("//*[contains(@id, 'business-trip')]")));
        // Нажать на  Создать командировку
        WebElement createBusinessTripButton = driver.findElement(By.xpath("//a[@title='Создать командировку']"));
        createBusinessTripButton.click();
        loading();
        //Проверить наличие на странице заголовка "Создать командировку"
        wait.until(visibilityOfElementLocated(
                By.xpath("//h1[contains(text(),'Создать командировку')]")));
        // Заполнить или выбрать поля:
        // Подразделение - выбрать Отдел внутренней разработки
        String subdivisionSelectXpath = "//select[contains(@id,'crm_business_trip_businessUnit')]";
        WebElement subdivisionSelectElement = driver.findElement(By.xpath(subdivisionSelectXpath));
        By targetSubdivisionBy = By.xpath(String.format(subdivisionSelectXpath + "/*[text()='%s']", DEPARTMENT_VALUE));
        fillSelectField(subdivisionSelectElement, targetSubdivisionBy, DEPARTMENT_VALUE);
        //— Принимающая организация  - нажать "Открыть список" и в поле "Укажите организацию" выбрать любое значение
        WebElement openListLink = driver.findElement(By.xpath("//*[@id='company-selector-show']"));
        openListLink.click();
        WebElement hostOrganizationSelectElement = driver.findElement(
                By.xpath("//*[contains(@id,'s2id_crm_business_trip_companySelector')]"));
        By hostOrganizationTargetBy = By.xpath(String.format(
                "//*[contains(@class, 'select2-result-label') and text()='%s']", HOST_ORGANIZATION_VALUE));
        fillSelectField(hostOrganizationSelectElement, hostOrganizationTargetBy, HOST_ORGANIZATION_VALUE);
        //— В задачах поставить чекбокс на "Заказ билетов"
        WebElement taskTicketsOrderCheckbox = driver.findElement(
                By.xpath("//label[text()='Заказ билетов']/..//*[@type = 'checkbox']"));
        setCheckboxInState(taskTicketsOrderCheckbox, true);
        //— Указать города выбытия и прибытия
        WebElement departureCityElement= driver.findElement(
                By.xpath("//*[contains(@id, 'crm_business_trip_departureCity')]"));
        fillInputField(departureCityElement, DEPARTURE_CITY_VALUE);
        WebElement arrivalCityElement= driver.findElement(
                By.xpath("//*[contains(@id, 'crm_business_trip_arrivalCity')]"));
        fillInputField(arrivalCityElement, ARRIVAL_CITY_VALUE);
        //—Указать даты выезда и возвращения
        WebElement departureDatePlanElement= driver.findElement(
                By.xpath("//*[contains(@id, 'date_selector_crm_business_trip_departureDatePlan')]"));
        fillDateField(departureDatePlanElement, DEPARTURE_DATE_VALUE);
        WebElement returnDatePlanElement= driver.findElement(
                By.xpath("//*[contains(@id, 'date_selector_crm_business_trip_returnDatePlan')]"));
        fillDateField(returnDatePlanElement, RETURN_DATE_VALUE);
        // Нажать "Сохранить и закрыть"
        WebElement saveAndCloseButtonElement = driver.findElement(
                By.xpath("//button[contains(text(),'Сохранить и закрыть') and @type='submit']"));
        saveAndCloseButtonElement.click();
        loading();
        // Раздел Командированные сотрудники не заполнять - проверим, что поле не заполнено
        String businessTripUserXpath = "//*[@class='control-label wrap']/*[text()='Командированные сотрудники']/../..";
        WebElement businessTripUsersValue = driver.findElement(
                By.xpath(businessTripUserXpath + "//*[contains(@id, 'crm_business_trip_users')]"));
        assertEquals("", businessTripUsersValue.getText(), "Поле Командированные сотрудники не пустое");
        // Проверить, что на странице появилось сообщение: "Список командируемых сотрудников не может быть пустым"
        WebElement businessTripUsersError = driver.findElement(
                By.xpath(businessTripUserXpath + "//*[@class='validation-failed']"));
        waitUtilElementToBeVisible(businessTripUsersError);
        String expectedErrorMessage = "Список командируемых сотрудников не может быть пустым";
        assertEquals(expectedErrorMessage,
                businessTripUsersError.getText(),
                "Проверка ошибки у поля не была пройдена");
    }

    /**
     * Дождаться загрузки
     */
    private void loading() {
        wait.until(visibilityOfElementLocated(By.xpath("//body[not(contains(@class, 'loading'))]")));
    }

    /**
     * Пройти авторизацию по указанным логину и паролю
     *
     * @param login login пользователя
     * @param password пароль пользователя
     */
    private void login(String login, String password) {
        WebElement loginForm = driver.findElement(By.xpath("//form[@id='login-form']"));
        WebElement loginInput = loginForm.findElement(By.xpath(".//input[@id='prependedInput']"));
        WebElement passInput = loginForm.findElement(By.xpath(".//input[@id='prependedInput2']"));
        fillInputField(loginInput, login);
        fillInputField(passInput, password);
        WebElement submitButton = loginForm.findElement(By.xpath(".//button[@id='_submit']"));
        waitUtilElementToBeClickable(submitButton);
        submitButton.click();
        wait.until(invisibilityOfElementLocated(By.xpath("//*[@class='progress progress-striped active']")));
    }

    /**
     * Получить значение по ключу из файла environment.properties
     *
     * @param key ключ
     * @return значение
     */
    private String getProperty(String key) {
        String value = "";
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream("src/test/resources/environment.properties")) {
            properties.load(input);
            value = properties.getProperty(key);

            if (value == null) {
                String errorMsg = String.format("Значение по ключу %s не найдено в environment.properties", key);
                throw new IllegalArgumentException(errorMsg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
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
     * Заполнить поле даты
     *
     * @param element элемент, который заполняем значением даты
     * @param dateValue значение даты
     */
    private void fillDateField(WebElement element, String dateValue) {
        scrollToElementJs(element);
        waitUtilElementToBeClickable(element);
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
        assertTrue(checkFlag, "Поле было заполнено некорректно");
    }

    /**
     * Переводим дату из формата dd.MM.yyyy в формат yyyy-MM-dd
     *
     * @param dateStr дата
     * @return отформатированная строка с датой
     */
    public static String convertDateFormat(String dateStr) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = inputFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputFormat.format(date);
    }

    /**
     * Явное ожидание того что элемент станет кликабельный
     *
     * @param element - веб элемент до которого нужно проскролить
     */
    private void waitUtilElementToBeClickable(WebElement element) {
        wait.until(elementToBeClickable(element));
    }

    /**
     * Явное ожидание того что элемент станет видимым
     *
     * @param element веб элемент который мы ожидаем что будет виден на странице
     */
    private void waitUtilElementToBeVisible(WebElement element) {
        wait.until(visibilityOf(element));
    }

    /**
     * Установка checkbox в указанное состояние
     *
     * @param checkBoxElement элемент типа checkBox
     * @param targetState состояние, в которое хотим установить checkBox
     */
    public static void setCheckboxInState(WebElement checkBoxElement, boolean targetState) {
        // Проверяем, находится ли checkbox в нужном состоянии
        if (checkBoxElement.isSelected() != targetState) {
            // Если не находится, кликаем по нему
            checkBoxElement.click();
        }
        assertEquals(checkBoxElement.isSelected(), targetState, "Поле было заполнено некорректно");
    }

    /**
     * Заполнение полей выбором из списка
     *
     * @param element веб элемент, который планируем заполнить
     * @param targetValueBy отбор элемента, который надо выбрать
     * @param targetValue целевое значение для проверки
     */
    private void fillSelectField(WebElement element, By targetValueBy, String targetValue) {
        scrollToElementJs(element);
        element.click();
        WebElement targetValueElement = driver.findElement(targetValueBy);
        scrollToElementJs(targetValueElement);
        waitUtilElementToBeClickable(targetValueElement);
        targetValueElement.click();
        boolean checkFlag = wait.until(textToBePresentInElement(element, targetValue));
        assertTrue(checkFlag, "Поле было заполнено некорректно");
    }

    /**
     * Заполнение полей определённым значений
     *
     * @param element - веб элемент (поле какое-то) которое планируем заполнить)
     * @param value - значение которы мы заполняем веб элемент (поле какое-то)
     */
    private void fillInputField(WebElement element, String value) {
        scrollToElementJs(element);
        waitUtilElementToBeClickable(element);
        element.click();
        element.clear();
        element.sendKeys(value);
        boolean checkFlag = wait.until(attributeContains(element, "value", value));
        assertTrue(checkFlag, "Поле было заполнено некорректно");
    }
}