package project.steps;

import project.pages.CreatingBusinessTripPage;

public class CreatingBusinessTripPageSteps {

    CreatingBusinessTripPage creatingBusinessTripPage = new CreatingBusinessTripPage();

    public CreatingBusinessTripPageSteps fillSubdivision(String value) {
        creatingBusinessTripPage.fillSelectFieldSubdivision(value);
        return this;
    }

    public CreatingBusinessTripPageSteps fillHostOrganization(String value) {
        creatingBusinessTripPage.fillHostOrganization(value);
        return this;
    }

    public CreatingBusinessTripPageSteps setCheckboxTicketsOrder(boolean state) {
        creatingBusinessTripPage.setCheckboxTicketsOrder(state);
        return this;
    }

    public CreatingBusinessTripPageSteps fillDepartureAndArrivalCities(String departureCity, String arrivalCity) {
        creatingBusinessTripPage.fillDepartureCity(departureCity);
        creatingBusinessTripPage.fillArrivalCity(arrivalCity);
        return this;
    }

    public CreatingBusinessTripPageSteps fillAndDepartureAndReturnDates(String departurePlanDate, String returnPlanDate) {
        creatingBusinessTripPage.fillDeparturePlanDate(departurePlanDate);
        creatingBusinessTripPage.fillReturnPlanDate(returnPlanDate);
        return this;
    }

    public CreatingBusinessTripPageSteps checkBusinessTripUsersIsEmpty() {
        creatingBusinessTripPage.checkBusinessTripUsersIsEmpty();
        return this;
    }

    public CreatingBusinessTripPageSteps clickSaveAndClose() {
        creatingBusinessTripPage.clickSaveAndClose();
        return this;
    }

    public void checkBusinessTripUsersError() {
        creatingBusinessTripPage.checkBusinessTripUsersError();
    }
}
