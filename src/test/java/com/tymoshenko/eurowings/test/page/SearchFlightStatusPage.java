package com.tymoshenko.eurowings.test.page;


import com.codeborne.selenide.SelenideElement;
import com.tymoshenko.eurowings.test.util.DateUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;


import java.time.LocalDate;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SearchFlightStatusPage {
    protected final static String FORM_DATE_FORMAT = "yyyy-MM-dd";
    protected final static String FLIGHTS_STATUS_URL = "https://www.eurowings.com/en/information/at-the-airport/flight-status.html";

    private final SelenideElement acceptCookiesButton = $(".cookie-consent--cta-accept");
    private final SelenideElement departureAirportField = $(".o-compact-search__bar-item--station-select-origin");
    private final SelenideElement enterDepartureAirport = $x("//*[@id='station-select-228-input']");
    private final SelenideElement enterDestinationAirport = $x("//*[@id='station-select-232-input']");
    private final SelenideElement destinationAirportsField = $(".o-compact-search__bar-item--station-select-destination");
    private final SelenideElement showStatusButton = $(".o-search-flight-status__flight-search__form [type=submit]");
    private final SelenideElement datePicker = $(".m-form-datepicker");

    private SelenideElement pointValue(String code) {
        return $x("//span[contains(., '•\u00A0" + code + "')]");
    }

    private SelenideElement dateInput(String dateValue) {
        return $x("//input[@class='calendar-date__input' and @value='" + dateValue + "']");
    }

    @Step("Open flight status search page")
    public SearchFlightStatusPage openFlightsStatusPage(){
        open(FLIGHTS_STATUS_URL);
        return this;
    }

    @Step("Accept cookies")
    public SearchFlightStatusPage allowCookies() {
        try {
            acceptCookiesButton.shouldBe(visible).click();
        } catch (TimeoutException e) {
            System.out.println("No cookie dialog");
        }

        return this;
    }

    @Step("Enter departure airport {departureAirport} and destination airports {destinationAirport}")
    public SearchFlightStatusPage selectDepartureAirport (String departureAirport) {
        departureAirportField.shouldBe(visible).click();
        enterDepartureAirport.shouldBe(visible).click();
        enterDepartureAirport.sendKeys(departureAirport);
        pointValue(departureAirport).shouldBe(visible).click();

        return this;
    }

    @Step("Enter destination airport {destinationAirport}")
    public SearchFlightStatusPage selectDestinationAirport (String destinationAirport) {
        destinationAirportsField.shouldBe(visible).click();
        enterDestinationAirport.shouldBe(visible).click();
        enterDestinationAirport.sendKeys(destinationAirport);
        pointValue(destinationAirport).shouldBe(visible).click();

        return this;
    }

    @Step("Select date")
    public SearchFlightStatusPage selectDate(LocalDate date) {
        String formattedDate = DateUtils.toString(date, FORM_DATE_FORMAT);

        datePicker.shouldBe(enabled).click();
        dateInput(formattedDate).click();

        return this;
    }

    @Step("Start searching of flights")
    public SearchFlightStatusPage clickSearchFlightStatus() {
        showStatusButton.shouldBe(enabled).click();

        return this;
    }
}
