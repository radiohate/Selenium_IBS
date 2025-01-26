package project.steps;

import project.pages.BusinessTripPage;

public class BusinessTripPageSteps {
    public CreatingBusinessTripPageSteps clickCreateBusinessTrip() {
        BusinessTripPage businessTripPage = new BusinessTripPage();
        businessTripPage.clickCreateBusinessTripButton();
        businessTripPage.checkCreateBusinessTripHeader();
        return new CreatingBusinessTripPageSteps();
    }
}
