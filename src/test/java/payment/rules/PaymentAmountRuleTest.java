package payment.rules;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import commons.PaymentBuilder;

class PaymentAmountRuleTest {

    @Test
    void validatesAmountRule(){
        assertTrue(new PaymentAmountRule(new PaymentBuilder().withInstructedAmount(ZERO).build().at("/Data/InstructedAmount/Amount")).valid());
        assertTrue(new PaymentAmountRule(new PaymentBuilder().withInstructedAmount(ONE).build().at("/Data/InstructedAmount/Amount")).valid());
    }

    @Test
    void hasNegativeAmount(){
        assertEquals("Invalid number format: -10",
            assertThrows(
                IllegalArgumentException.class,
                () -> new PaymentAmountRule(
                    new PaymentBuilder()
                        .withInstructedAmount(TEN.negate())
                        .build()
                        .at("/Data/InstructedAmount/Amount")
                ).valid()
            ).getMessage()
        );
    }

    @Test
    void hasInvalidAmount(){
        assertEquals("Invalid number format: foo",
            assertThrows(
                IllegalArgumentException.class,
                () -> new PaymentAmountRule(
                    new PaymentBuilder()
                        .withInstructedAmount("foo")
                        .build()
                        .at("/Data/InstructedAmount/Amount")
                ).valid()
            ).getMessage()
        );
    }


}