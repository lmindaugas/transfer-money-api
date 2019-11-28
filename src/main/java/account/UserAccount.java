package account;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.math.BigDecimal;

import commons.UncheckedMapper;
import db.tables.records.AccountRecord;

public final class UserAccount {

    private final JsonNode json;
    private final UncheckedMapper mapper = new UncheckedMapper();

    public UserAccount(String account){
        this.json = mapper.readTree(account);
    }

    public UserAccount(AccountRecord record){
        this.json = mapper.createObjectNode();
        ((ObjectNode)json)
            .put("Id", record.getId())
            .put("Name", record.getName())
            .put("Balance", String.valueOf(record.getBalance()))
            .put("Currency", record.getCurrency())
            .put("Status", record.getStatus());
    }

    @Override
    public String toString() {
        return json.toString();
    }

    public JsonNode asJson() {
        return json;
    }

    public String id() {
        return json.at("/Id").textValue();
    }

    public String name() {
        return json.at("/Name").textValue();
    }

    public BigDecimal balance() {
        return new BigDecimal(json.at("/Balance").textValue());
    }

    public String currency() {
        return json.at("/Currency").textValue();
    }

    public String status() {
        return json.at("/Status").textValue();
    }
}
