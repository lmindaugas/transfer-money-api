package commons;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.math.BigDecimal;

import static java.math.BigDecimal.TEN;

public final class PaymentBuilder {

    private final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode payload;

    public PaymentBuilder(){
        payload = mapper.createObjectNode();
        payload.putObject("Data");
    }

    public JsonNode build(){
        return payload;
    }

    public PaymentBuilder withDebtorAccount() {
        payload
            .with("Data")
            .set("DebtorAccount", debtorAccount());
        return this;
    }

    public PaymentBuilder withCreditorAccount() {
        payload
            .with("Data")
            .set("CreditorAccount", creditorAccount());
        return this;
    }

    public PaymentBuilder withInstructedAmount() {
        withInstructedAmount(TEN);
        return this;
    }

    public PaymentBuilder withInstructedAmount(BigDecimal amount) {
        payload
            .with("Data")
            .set("InstructedAmount", instructedAmount(amount));
        return this;
    }

    public PaymentBuilder withInstructedAmount(String amount) {
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
