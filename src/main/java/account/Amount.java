package account;

import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;

public class Amount {
    private final JsonNode json;

    public Amount(JsonNode json) {
        this.json = json;
    }

    public BigDecimal value(){
        return new BigDecimal(json.at("/Amount").textValue());
    }
}
