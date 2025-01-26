package project.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import project.BasePage;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class LoginPage extends BasePage {
    @FindBy(xpath = "//form[@id='login-form']")
    private WebElement loginFormWindow;

    @FindBy(xpath = "//form[@id='login-form']//input[@id='prependedInput']")
    private WebElement loginInput;

    @FindBy(xpath = "//form[@id='login-form']//input[@id='prependedInput2']")
    private WebElement passInput;

    @FindBy(xpath = "//form[@id='login-form']//button[@id='_submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//*[@class='progress progress-striped active']")
    private WebElement loadingElementAfterLogin;

    @FindBy(xpath = "//h1[contains(text(),'Панель быстрого запуска')]")
    private WebElement quickStartSubtitle;

    /**
     * Ввести логин и пароль
     *
     * @param login    login пользователя
     * @param password пароль пользователя
     */
    public void enterLoginAndPassword(String login, String password) {
        wait.until(visibilityOf(loginFormWindow));
        fillInputField(loginInput, login);
        fillInputField(passInput, password);
    }

    /**
     * Подтвердить авторизацию
     */
    public void submitLogin() {
        wait.until(elementToBeClickable(submitButton));
        submitButton.click();
        wait.until(invisibilityOf(loadingElementAfterLogin));
        // Проверить наличие на странице заголовка Панель быстрого запуска
        wait.until(visibilityOf(quickStartSubtitle));
    }
}
