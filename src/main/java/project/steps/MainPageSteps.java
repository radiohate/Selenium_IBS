package project.steps;

import project.pages.MainPage;

public class MainPageSteps {
    public BusinessTripPageSteps chooseBusinessTripItem() {
        MainPage mainPage = new MainPage();
        mainPage.clickCosts();
        mainPage.chooseBusinessTrip();
        return new BusinessTripPageSteps();
    }
}
