package com.jpmc.theater.pricing;

import com.jpmc.theater.model.Movie;
import com.jpmc.theater.model.Showing;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Pricing Rules Tests")
class DiscountTypeRuleTest {

    @Nested
    @DisplayName("Sequence Discount")
    public class SequenceDiscountTestType {

        @Test
        void shouldReturnWithCorrectDiscount() {
            var sequenceRule = new DiscountRule((showing, sequence) -> sequence == 1, DiscountType.ofFixed(10));
            Showing showing = new Showing(
                    new Movie("spiderman", Duration.ofMinutes(90), 50, 0),
                    LocalDateTime.now()
            );

            var discount = sequenceRule.apply(showing, 1);

            assertEquals(10, discount);
        }

        @Test
        void shouldReturnWithNoDiscountWhenNothingApplicableTest() {
            var sequenceRule = new DiscountRule((showing, sequence) -> false, DiscountType.ofFixed(10));
            Showing showing = new Showing(
                    new Movie("spiderman", Duration.ofMinutes(90), 50, 0),
                    LocalDateTime.now()
            );

            var discount = sequenceRule.apply(showing, 1);

            assertEquals(0, discount);
        }
    }


    @Nested
    @DisplayName("Percent Discount Tests")
    public class PercentDiscountRuleTestType {

        @Test
        void shouldReturnWithCorrectDiscount() {
            var specialMovieDiscountRule = new DiscountRule((show, __) -> true, DiscountType.ofPercentage(10));
            Showing showing = new Showing(
                    new Movie("spiderman", Duration.ofMinutes(90), 50, 1),
                    LocalDateTime.now()
            );

            var discount = specialMovieDiscountRule.apply(showing, 1);

            assertEquals(5, discount);
        }

        @Test
        void shouldReturnWithNoDiscountWhenNothingApplicableTest() {
            var specialMovieDiscountRule = new DiscountRule((show, __) -> false, DiscountType.ofPercentage(10));
            Showing showing = new Showing(
                    new Movie("spiderman", Duration.ofMinutes(90), 50, 0),
                    LocalDateTime.now()
            );

            var discount = specialMovieDiscountRule.apply(showing, 1);

            assertEquals(0, discount);
        }
    }
}