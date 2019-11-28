package payment;

import static java.lang.System.currentTimeMillis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.Timestamp;
import java.util.UUID;

import account.Account;
import commons.UncheckedMapper;
import db.tables.records.PaymentRecord;

public final class Payment {

    private final UncheckedMapper mapper = new UncheckedMapper();
    private final JsonNode json;

    public Payment(PaymentRecord payment){
        this(payment.getPayload(), payment.getPaymentId(), payment.getTimestamp());
    }

    public Payment(String payment, UUID paymentId) {
        this(payment, paymentId, new Timestamp(currentTimeMillis()));
    }

    public Payment(String payment, UUID paymentId, Timestamp timestamp) {
        this.json = mapper.readTree(payment);
        ((ObjectNode)json)
            .put("PaymentId", paymentId.toString())
            .put("Timestamp", timestamp.toString());
    }

    public JsonNode asJson() {
        return json;
    }

    public UUID id() {
        return UUID.fromString(json.at("/PaymentId").textValue());
    }

    @Override
    public String toString() {
        return json.toString();
    }
}