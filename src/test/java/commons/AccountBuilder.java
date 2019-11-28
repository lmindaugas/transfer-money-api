package commons;

import static java.math.BigDecimal.TEN;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.math.BigDecimal;

public final class AccountBuilder {

    private final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode payload;

    public AccountBuilder(){
        payload = mapper.createObjectNode();
    }

    public JsonNode build(){
        return payload;
    }

    public AccountBuilder withDebtorAccount() {
        payload
            .with("Data")
            .set("DebtorAccount", debtorAccount());
        return this;
    }

    public AccountBuilder withCreditorAccount() {
        payload
            .with("Data")
            .set("CreditorAccount", creditorAccount());
        return this;
    }

    public AccountBuilder withInstructedAmount() {
        withInstructedAmount(TEN);
        return this;
    }

    public AccountBuilder withInstructedAmount(BigDecimal amount) {
        payload
            .with("Data")
            .set("InstructedAmount", instructedAmount(amount));
        return this;
    }

    public AccountBuilder withInstructedAmount(String amount) {
        payload
            .with("Data")
            .set("InstructedAmount", instructedAmount(amount));
        return this;
    }

    private JsonNode debtorAccount() {
        return mapper.createObjectNode()
            .put("SchemeName", "BBAN")
            .put("Identification", "09566600055543");
    }

    private JsonNode instructedAmount(BigDecimal amount) {
        return mapper.createObjectNode()
            .put("Amount", amount.toString())
            .put("Currency", "EUR");
    }

    private JsonNode instructedAmount(String amount) {
        return mapper.createObjectNode()
            .put("Amount", amount)
            .put("Currency", "EUR");
    }

    private JsonNode creditorAccount() {
        return mapper.createObjectNode()
            .put("SchemeName", "BBAN")
            .put("Identification", "09566600055543");
    }
}
