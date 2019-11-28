package account;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static commons.TestData.validpayloadAsJson;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {

    @Test
    void buildAccounts(){
        assertEquals(
            new Account(validpayloadAsJson().at("/Data/DebtorAccount")).id(),
            validpayloadAsJson().at("/Data/DebtorAccount/Identification").textValue());

        Assertions.assertEquals(
            new Account(validpayloadAsJson().at("/Data/CreditorAccount")).id(),
            validpayloadAsJson().at("/Data/CreditorAccount/Identification").textValue());
    }
}