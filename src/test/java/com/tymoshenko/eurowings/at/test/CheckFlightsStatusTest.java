package com.tymoshenko.eurowings.at.test;

import com.tymoshenko.eurowings.at.page.ResultsFlightStatusPage;
import com.tymoshenko.eurowings.at.page.SearchFlightStatusPage;
import com.tymoshenko.eurowings.at.util.DateUtils;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.LocalDate;

@Epic("Eurowings interview")
@Feature("Interview task")
public class CheckFlightsStatusTest extends CommonTest {
    SearchFlightStatusPage searchFlightPage = new SearchFlightStatusPage();
    ResultsFlightStatusPage resultsFlightStatusPage = new ResultsFlightStatusPage();

    @Test(description = "Verify flight status")
    @Parameters({"departureAirport", "destinationAirport", "searchDate"})
    public void checkMainSearch(String departureAirport, String destinationAirport, String searchDate) {
        LocalDate testDate = DateUtils.toDate(searchDate);
        LocalDate nextDate = testDate.plusDays(1);

        searchFlightPage.openFlightsStatusPage()
                .allowCookies()
                .selectDepartureAirport(departureAirport)
                .selectDestinationAirport(destinationAirport)
                .selectDate(testDate)
                .clickSearchFlightStatus();

        resultsFlightStatusPage.checkResultTableIsPresented()
                .checkDate(testDate)
                .checkAirports(departureAirport, destinationAirport)
                .checkFlightStatus()
                .changeDate(nextDate)
                .checkDate(nextDate)
                .checkAirports(departureAirport, destinationAirport)
                .checkFlightStatus();
    }
}


