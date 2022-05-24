package com.jpmc.theater;

import com.jpmc.theater.model.Theater;
import com.jpmc.theater.service.CurrentDateProviderImpl;
import com.jpmc.theater.service.TheaterSchedule;
import com.jpmc.theater.service.TheaterService;
import com.jpmc.theater.service.TheaterServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Integrations Tests")
class ApplicationIntegrationTest {

    private final TheaterServiceImpl theaterService = new TheaterServiceImpl(CurrentDateProviderImpl.getInstance());
    private final TheaterSchedule theaterSchedule = theaterService.getTheaterSchedule();

    @Nested
    @DisplayName("Pricing Rules on Schedule IT")
    class PricingRuleTests {

        @Test
        void checkSpiderManMovieSpecialPercentOffPrice() {
            var showDetail = theaterSchedule.showDetails().get(7);

            assertEquals("Spider-Man: No Way Home", showDetail.title());

            assertEquals(12.5, showDetail.price());
            assertEquals(10, showDetail.finalPrice());
        }

        @Test
        void checkSpiderManMovieElevenToFourPercentOffPrice() {
            var showDetail = theaterSchedule.showDetails().get(1);

            assertEquals("Spider-Man: No Way Home", showDetail.title());

            assertEquals(12.5, showDetail.price());
            assertEquals(9.375, showDetail.finalPrice());
        }

        @Test
        void checkFirstMovieDiscount() {
            var showDetail = theaterSchedule.showDetails().get(0);

            assertEquals("Turning Red", showDetail.title());

            assertEquals(11, showDetail.price());
            assertEquals(8, showDetail.finalPrice());
        }

        @Test
        void checkRegularPriceMovieNoDiscount() {
            var showDetail = theaterSchedule.showDetails().get(5);

            assertEquals("The Batman", showDetail.title());

            assertEquals(9, showDetail.price());
            assertEquals(9, showDetail.finalPrice());
        }
    }
}