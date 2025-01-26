package ru.ibs.appline.tests;

import extentions.DriverExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import project.steps.LoginSteps;

import static project.properties.TestProperties.getProperty;

@ExtendWith(DriverExtension.class)
public class ExampleScenarioTest2 {

    private static final String USER_KEY = "user1";
    private static final String PASS_KEY = "pass1";
    private static final String DEPARTMENT_VALUE = "Отдел внутренней разработки";
    private static final String HOST_ORGANIZATION_VALUE = "(Edge) Призрачная Организация Охотников";
    private static final String DEPARTURE_CITY_VALUE = "Москва";
    private static final String ARRIVAL_CITY_VALUE = "Калининград";
    private static final String DEPARTURE_DATE_VALUE = "10.02.2025";
    private static final String RETURN_DATE_VALUE = "14.02.2025";

    private static final LoginSteps loginSteps = new LoginSteps();

    @Test
    public void trainingTest() {
        loginSteps
                .login(getProperty(USER_KEY),
                        getProperty(PASS_KEY))
                .chooseBusinessTripItem()
                .clickCreateBusinessTrip()
                .fillSubdivision(DEPARTMENT_VALUE)
                .fillHostOrganization(HOST_ORGANIZATION_VALUE)
                .fillDepartureAndArrivalCities(DEPARTURE_CITY_VALUE, ARRIVAL_CITY_VALUE)
                .fillAndDepartureAndReturnDates(DEPARTURE_DATE_VALUE, RETURN_DATE_VALUE)
                .setCheckboxTicketsOrder(true)
                .checkBusinessTripUsersIsEmpty()
                .clickSaveAndClose()
                .checkBusinessTripUsersError();
    }
}