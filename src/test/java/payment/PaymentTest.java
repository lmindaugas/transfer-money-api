package payment;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static commons.TestData.invalidPayload;
import static commons.TestData.validpayload;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentTest {

    @Test
    void hasPaymentId(){
        UUID paymentId = randomUUID();
        Payment payment = new Payment(validpayload(), paymentId);
        assertEquals(payment.asJson().at("/PaymentId").textValue(), paymentId.toString());
    }

    @Test
    void acceptsValidJsonOnly(){
        assertEquals(
            "Invalid json schema",
            assertThrows(
                IllegalArgumentException.class, () ->  new Payment(invalidPayload(), randomUUID())
            ).getMessage());
    }
}