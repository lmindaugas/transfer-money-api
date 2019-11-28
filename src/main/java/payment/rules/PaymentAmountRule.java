package payment.rules;

import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.util.Optional;

// TODO add JsonSchema validator lib
public class PaymentAmountRule {
    private final JsonNode payload;

    public PaymentAmountRule(JsonNode payload) {
        this.payload = payload;
    }

    public boolean valid() {
        Optional.of(payload)
            .map(JsonNode::textValue)
            .map(this::parse)
            .filter(this::positive)
            .orElseThrow(() -> new IllegalArgumentException("Invalid number format: " + payload.textValue()));
        return true;
    }

    private BigDecimal parse(String s) {
        try {
            return new BigDecimal(s);
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format: " + s);
        }
    }

    private boolean positive(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) >= 0;
    }
}
