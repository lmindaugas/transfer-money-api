package payment.rules;

import commons.PaymentBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import payment.rules.PaymentSchemaRule;

import static commons.TestData.validpayloadAsJson;
import static org.junit.jupiter.api.Assertions.*;

class PaymentSchemaRuleTest {

    @Test
    void validatesSchema(){
        assertTrue(new PaymentSchemaRule(validpayloadAsJson()).valid());
    }

    @Test
    void throwsExceptionWhenFieldsMissing(){
        Assertions.assertAll(
            () ->
                Assertions.assertEquals(
                    "InstructedAmount field is missing",
                    assertThrows(
                        IllegalArgumentException.class, () -> new PaymentSchemaRule(
                            new PaymentBuilder()
                                .withDebtorAccount()
                                .withCreditorAccount()
                                .build()
                        ).valid()
                    ).getMessage()
                ),
            () ->
                Assertions.assertEquals(
                    "DebtorAccount field is missing",
                    assertThrows(
                        IllegalArgumentException.class, () -> new PaymentSchemaRule(
                            new PaymentBuilder()
                                .withInstructedAmount()
                                .withCreditorAccount()
                                .build()
                        ).valid()
                    ).getMessage()
                ),
            () ->
                Assertions.assertEquals(
                    "CreditorAccount field is missing",
                    assertThrows(
                        IllegalArgumentException.class, () -> new PaymentSchemaRule(
                            new PaymentBuilder()
                                .withInstructedAmount()
                                .withDebtorAccount()
                                .build()
                        ).valid()
                    ).getMessage()
                )
        );
    }


}