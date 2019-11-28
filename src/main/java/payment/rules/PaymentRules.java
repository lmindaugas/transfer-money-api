package payment.rules;

import com.fasterxml.jackson.databind.JsonNode;
import payment.Rule;

public class PaymentRules implements Rule {

    private final JsonNode payload;

    public PaymentRules(JsonNode payload) {
        this.payload = payload;
    }

    @Override
    public boolean valid() {
        return
            new PaymentSchemaRule(payload).valid() &&
            new PaymentAmountRule(payload.at("/Data/InstructedAmount/Amount")).valid();
    }
}
