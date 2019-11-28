package account;

import com.fasterxml.jackson.databind.JsonNode;

public final class Account {
    private final JsonNode account;

    public Account(JsonNode account) {
        this.account = account;
    }

    public String id() {
        return account.at("/Identification").textValue();
    }
}
