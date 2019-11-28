package account.rules;

import com.fasterxml.jackson.databind.JsonNode;

import payment.Rule;
import payment.rules.PaymentAmountRule;
import payment.rules.PaymentSchemaRule;

public class AccountRules implements Rule {

    private final JsonNode payload;

    public AccountRules(JsonNode payload) {
        this.payload = payload;
    }

    @Override
    public boolean valid() {
        return new AccountSchemaRule(payload).valid() &&
               new PaymentAmountRule(payload.at("/Balance")).valid();
    }
}
