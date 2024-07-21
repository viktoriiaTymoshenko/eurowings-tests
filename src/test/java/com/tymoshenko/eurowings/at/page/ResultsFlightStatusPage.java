package com.tymoshenko.eurowings.at.page;

import com.codeborne.selenide.SelenideElement;
import com.tymoshenko.eurowings.at.util.DateUtils;
import io.qameta.allure.Step;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.*;

public class ResultsFlightStatusPage extends SearchFlightStatusPage {
    private final static String TABLE_TAB_DATE_FORMAT = "E, dd/MM/";
    private final static String TABLE_TAB_WITH_YEAR_DATE_FORMAT = TABLE_TAB_DATE_FORMAT + "yyyy";

    private final SelenideElement resultTable = $(".o-search-flight-status__flight-list-content");
    private final SelenideElement activeDateField = $(".o-search-flight-status__date-navigation__date--active");
    private final SelenideElement airportsField = $(".o-search-flight-status__card-airports");
    private final SelenideElement statusField = $(".o-search-flight-status__card-flight-status");

    private SelenideElement tabDateInput(String dateValue) {
        return $x("//div[@class='o-search-flight-status__date-navigation__date-day' and contains(text(), '" + dateValue + "')]");
    }

    @Step("Check that result table is presented")
    public ResultsFlightStatusPage checkResultTableIsPresented(){
        assertTrue(resultTable.isDisplayed(), "Results table should be displayed but isn't.");

        return this;
    }

    @Step("Check active date value in result table")
    public ResultsFlightStatusPage checkDate(LocalDate expectedDate) {
        String displayedDateText = activeDateField.getText();
        String dateWithYear = displayedDateText + expectedDate.getYear();

        LocalDate actualDate = DateUtils.toDate(dateWithYear, TABLE_TAB_WITH_YEAR_DATE_FORMAT, Locale.ENGLISH);

        assertEquals(expectedDate, actualDate, "Expected date: " + expectedDate + " but was: " + actualDate);

        return this;
    }

    @Step("Check airports in result table")
    public ResultsFlightStatusPage checkAirports(String expectedDeparture, String expectedDestination) {;
        String displayedAirports = airportsField.shouldBe(visible).getText().trim();
        String[] airports = displayedAirports.split("\\s+");
        Assert.assertEquals(airports.length, 2, "Expected two airport codes, but found: " + airports.length);
        Assert.assertEquals(airports[0], expectedDeparture, "Departure airport does not match");
        Assert.assertEquals(airports[1], expectedDestination, "Destination airport does not match");

        return this;
    }

    @Step("Check flight status in result table")
    public ResultsFlightStatusPage checkFlightStatus(){
        assertTrue(statusField.isDisplayed(), "Flight status field should be displayed but it isn't.");

        return this;
    }

    @Step("Change date in result table")
    public ResultsFlightStatusPage changeDate(LocalDate date)  {
        String formattedDate = date.format(DateTimeFormatter.ofPattern(TABLE_TAB_DATE_FORMAT, Locale.ENGLISH));
        tabDateInput(formattedDate).shouldBe(enabled).click();

        return this;
    }
}
