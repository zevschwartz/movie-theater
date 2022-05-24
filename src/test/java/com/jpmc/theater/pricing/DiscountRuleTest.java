package com.jpmc.theater.pricing;

import com.jpmc.theater.model.Movie;
import com.jpmc.theater.model.Showing;
import com.jpmc.theater.pricing.Discount;
import com.jpmc.theater.pricing.MovieDiscountRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Pricing Rules Tests")
class DiscountRuleTest {

    @Nested
    @DisplayName("Sequence Discount")
    public class SequenceDiscountTest {

        @Test
        void shouldReturnWithCorrectDiscount() {
            var sequenceRule = new MovieDiscountRule((showing, sequence) -> sequence == 1, Discount.ofFixed(10));
            Showing showing = new Showing(
                    new Movie("spiderman", Duration.ofMinutes(90), 50, 0),
                    LocalDateTime.now()
            );

            var discount = sequenceRule.calculateTotalDiscount(showing, 1);

            assertEquals(10, discount);
        }

        @Test
        void shouldReturnWithNoDiscountWhenNothingApplicableTest() {
            var sequenceRule = new MovieDiscountRule((showing, sequence) -> false, Discount.ofFixed(10));
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
            var specialMovieDiscountRule = new MovieDiscountRule((show, __) -> true, Discount.ofPercentage(10));
            Showing showing = new Showing(
                    new Movie("spiderman", Duration.ofMinutes(90), 50, 1),
                    LocalDateTime.now()
            );

            var discount = specialMovieDiscountRule.calculateTotalDiscount(showing, 1);

            assertEquals(5, discount);
        }

        @Test
        void shouldReturnWithNoDiscountWhenNothingApplicableTest() {
            var specialMovieDiscountRule = new MovieDiscountRule((show, __) -> false, Discount.ofPercentage(10));
            Showing showing = new Showing(
                    new Movie("spiderman", Duration.ofMinutes(90), 50, 0),
                    LocalDateTime.now()
            );

            var discount = specialMovieDiscountRule.calculateTotalDiscount(showing, 1);

            assertEquals(0, discount);
        }
    }
}