package payment;

import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockDataProvider;

class PaymentsServiceTest {

    @Test
    @Disabled
    void executesPayment(){

//        MockDataProvider provider = new PaymentDataProvider();
//        MockConnection connection = new MockConnection(provider);
//
//        DSLContext create = DSL.using(connection, SQLDialect.H2);
//
//        PaymentsService paymentsService = new PaymentsService(create);
//        paymentsService.execute(new Payment(TestData.validpayload(), UUID.randomUUID()));
    }

}