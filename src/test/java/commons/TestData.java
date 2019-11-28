package commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestData {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String validpayload() {
        return "{\"Data\":{\"InstructedAmount\":{\"Amount\":\"10.00\",\"Currency\":\"EUR\"},\"DebtorAccount\":{\"SchemeName\":\"BBAN\",\"Identification\":\"09566600055543\",\"Name\":\"Michael O\"},\"CreditorAccount\":{\"SchemeName\":\"BBAN\",\"Identification\":\"00400440116243\",\"Name\":\"John D\"}}}";
    }

    public static String invalidPayload() {
        return "{\"Data\"\"InstructedAmount...\"";
    }

    public static JsonNode validpayloadAsJson() {
        try {
            return mapper.readTree(validpayload());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
