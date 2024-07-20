package com.tymoshenko.eurowings.test;

import com.tymoshenko.eurowings.test.page.ResultsFlightStatusPage;
import com.tymoshenko.eurowings.test.page.SearchFlightStatusPage;
import com.tymoshenko.eurowings.test.util.DateUtils;
import io.qameta.allure.Allure;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class CheckFlightsStatusTest extends CommonTest {
    SearchFlightStatusPage searchFlightPage = new SearchFlightStatusPage();
    ResultsFlightStatusPage resultsFlightStatusPage = new ResultsFlightStatusPage();

    @Test
    @Parameters({"departureAirport", "destinationAirport", "searchDate"})
    public void checkMainSearch(String departureAirport, String destinationAirport, String searchDate) {
        LocalDate testDate = DateUtils.toDate(searchDate);
        LocalDate nextDate = testDate.plusDays(1);

        try {
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
                    .checkAirports(departureAirport,destinationAirport)
                    .checkFlightStatus();
        } catch (Throwable error) {
            Allure.step(error.getMessage());
            Allure.attachment("error screen", doScreenshot());
            throw error;
        }
    }



}
