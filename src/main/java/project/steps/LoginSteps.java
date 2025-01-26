package project.steps;

import project.pages.LoginPage;

public class LoginSteps {
    public MainPageSteps login(String login, String password) {
        LoginPage loginPage = new LoginPage();
        loginPage.enterLoginAndPassword(login, password);
        loginPage.submitLogin();
        return new MainPageSteps();
    }
}
