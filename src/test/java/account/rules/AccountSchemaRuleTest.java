package account.rules;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import commons.UncheckedMapper;

class AccountSchemaRuleTest {

    private static final UncheckedMapper mapper = new UncheckedMapper();

    @Test
    void buildsAccountSchema(){
        assertTrue(new AccountSchemaRule(validpayloadAsJson()).valid());
    }

    private static ObjectNode validpayloadAsJson() {
        return mapper.createObjectNode()
            .put("Id", "232380298")
            .put("Name", "foobar")
            .put("Balance", BigDecimal.TEN)
            .put("Currency", "EUR")
            .put("Status", "Active");
    }

    @Test
    void hasInvalidPayload(){
        Assertions.assertAll(
            () ->
                Assertions.assertEquals(
                    "Id field is missing",
                    assertThrows(
                        IllegalArgumentException.class, () -> new AccountSchemaRule(
                            missingField("Id")).valid()
                    ).getMessage()
                ),
            () ->
                Assertions.assertEquals(
                    "Name field is missing",
                    assertThrows(
                        IllegalArgumentException.class, () -> new AccountSchemaRule(
                            missingField("Name")).valid()
                    ).getMessage()
                ),
            () ->
                Assertions.assertEquals(
                    "Balance field is missing",
                    assertThrows(
                        IllegalArgumentException.class, () -> new AccountSchemaRule(
                            missingField("Balance")).valid()
                    ).getMessage()
                )
        );
    }

    private ObjectNode missingField(String node) {
        ObjectNode payload = validpayloadAsJson();
        payload.remove(node);
        return payload;
    }
}