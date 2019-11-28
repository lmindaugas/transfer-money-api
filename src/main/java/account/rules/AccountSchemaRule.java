package account.rules;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

// TODO add JsonSchema validator lib
public class AccountSchemaRule {
    private final JsonNode payload;

    public AccountSchemaRule(JsonNode payload) {
        this.payload = payload;
    }

    public boolean valid() {
        required(payload.at("/Id"), "Id field is missing");
        required(payload.at("/Name"), "Name field is missing");
        required(payload.at("/Balance"), "Balance field is missing");
        required(payload.at("/Currency"), "Currency field is missing");
        required(payload.at("/Status"), "Status field is missing");

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
