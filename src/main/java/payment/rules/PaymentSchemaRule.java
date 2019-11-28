package payment.rules;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

// TODO add JsonSchema validator lib
public class PaymentSchemaRule {
    private final JsonNode payload;

    public PaymentSchemaRule(JsonNode payload) {
        this.payload = payload;
    }

    public boolean valid() {
        required(payload.at("/Data"), "Data field is missing");
        required(payload.at("/Data/InstructedAmount"), "InstructedAmount field is missing");
        required(payload.at("/Data/DebtorAccount"), "DebtorAccount field is missing");
        required(payload.at("/Data/CreditorAccount"), "CreditorAccount field is missing");

        required(payload.at("/Data/InstructedAmount/Amount"), "InstructedAmount/Amount field is missing");
        required(payload.at("/Data/InstructedAmount/Currency"), "InstructedAmount/Currency field is missing");

        required(payload.at("/Data/DebtorAccount/SchemeName"), "DebtorAccount/SchemeName field is missing");
        required(payload.at("/Data/DebtorAccount/Identification"), "DebtorAccount/Identification field is missing");

        required(payload.at("/Data/CreditorAccount/SchemeName"), "CreditorAccount/SchemeName field is missing");
        required(payload.at("/Data/CreditorAccount/Identification"), "CreditorAccount/Identification field is missing");

        return true;
    }

    private void required(JsonNode jsonNode, String message) {
        Optional.of(jsonNode)
            .filter(this::notMissing)
            .orElseThrow(() -> new IllegalArgumentException(message));
    }

    private boolean notMissing(JsonNode jsonNode) {
        return !jsonNode.isMissingNode();
    }
}
