package com.jpmc.theater;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pricing Rules Tests")
class DiscountRuleTest {

    @Nested
    @DisplayName("Sequence Discount")
    public class SequenceDiscountTest {

        @Test
        void shouldReturnWithCorrectDiscount() {
            var sequenceRule = new SequenceDiscountRule(10);
            Showing showing = new Showing(
                    new Movie("spiderman", Duration.ofMinutes(90), 50, 0),
                    LocalDateTime.now()
            );

            var discount = sequenceRule.calculateTotalDiscount(showing, 1);

            assertEquals(10, discount);
        }

        @Test
        void shouldReturnWithNoDiscountWhenNothingApplicableTest() {
            var sequenceRule = new SequenceDiscountRule(List.of());
            Showing showing = new Showing(
                    new Movie("spiderman", Duration.ofMinutes(90), 50, 0),
                    LocalDateTime.now()
            );

            var discount = sequenceRule.calculateTotalDiscount(showing, 1);

            assertEquals(0, discount);
        }
    }


    @Nested
    @DisplayName("Percent Discount Tests")
    public class PercentDiscountRuleTest {

        @Test
        void shouldReturnWithCorrectDiscount() {
            var specialMovieDiscountRule = new PercentDiscountRule((show, __) -> true, 10);
            Showing showing = new Showing(
                    new Movie("spiderman", Duration.ofMinutes(90), 50, 1),
                    LocalDateTime.now()
            );

            var discount = specialMovieDiscountRule.calculateTotalDiscount(showing, 1);

            assertEquals(5, discount);
        }

        @Test
        void shouldReturnWithNoDiscountWhenNothingApplicableTest() {
            var specialMovieDiscountRule = new PercentDiscountRule((show, __) -> false, 10);
            Showing showing = new Showing(
                    new Movie("spiderman", Duration.ofMinutes(90), 50, 0),
                    LocalDateTime.now()
            );

            var discount = specialMovieDiscountRule.calculateTotalDiscount(showing, 1);

            assertEquals(0, discount);
        }
    }
}