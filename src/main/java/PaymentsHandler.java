import static java.util.UUID.fromString;
import static java.util.UUID.randomUUID;

import static db.Tables.ACCOUNT;
import static db.tables.Payment.PAYMENT;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.post;

import java.util.Optional;
import java.util.stream.Collectors;

import account.UserAccount;
import account.rules.AccountRules;
import commons.UncheckedMapper;
import db.tables.records.AccountRecord;
import db.tables.records.PaymentRecord;
import org.jooq.DSLContext;
import org.jooq.JSONFormat;
import account.AccountService;
import payment.Payment;
import payment.PaymentService;
import payment.rules.PaymentRules;

public class PaymentsHandler {

    private final DSLContext dsl;
    private final JSONFormat format;
    private final AccountService accountService;
    private final UncheckedMapper mapper = new UncheckedMapper();
    private final PaymentService paymentService;

    // TODO: add DI
    public PaymentsHandler(DSLContext dsl) {
        this.dsl = dsl;
        this.paymentService = new PaymentService(dsl);
        this.accountService = new AccountService(dsl);
        this.format = new JSONFormat()
            .format(false)
            .header(false)
            .recordFormat(JSONFormat.RecordFormat.OBJECT);
    }

    public void handle() {

        /**
         * POST /payments
         *
         */
        post("/payments", "application/json", (req, resp) -> {
            resp.type("application/json");
            resp.status(201);
            return dsl.transactionResult(() -> {
                Payment payment = new Payment(req.body(), randomUUID());
                Optional.of(payment)
                    .map(Payment::asJson)
                    .map(PaymentRules::new)
                    .filter(PaymentRules::valid)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid payment payload"));

                paymentService.execute(payment);
                return payment.asJson();
            });
        });

        /**
         * GET /payments/{paymentId}
         */
        get("/payments/:paymentId", (req, resp) -> {
            resp.type("application/json");
            return dsl.transactionResult(() ->
                dsl.select()
                    .from(PAYMENT)
                    .where(PAYMENT.PAYMENT_ID.eq(fromString(req.params(":paymentId"))))
                    .fetch()
                    .into(PaymentRecord.class)
                    .stream()
                    .map(Payment::new)
                    .findFirst().orElseThrow(()-> new IllegalArgumentException("payment not found"))
            );
        });

        /**
         * GET /payments
         */
        get("/payments", (req, resp) -> {
            resp.type("application/json");
            return dsl.transactionResult(() ->
                dsl.select()
                    .from(PAYMENT)
                    .fetch()
                    .into(PaymentRecord.class)
                    .stream()
                    .map(Payment::new)
                    .collect(Collectors.toList())
            );
        });

        /**
         * POST /accounts
         *
         */
        post("/accounts", "application/json", (req, resp) -> {
            resp.type("application/json");
            resp.status(201);
            return dsl.transactionResult(() -> {
                UserAccount account = new UserAccount(req.body());
                Optional.of(account)
                    .map(UserAccount::asJson)
                    .map(AccountRules::new)
                    .filter(AccountRules::valid)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid account payload"));

                accountService.create(account);
                return account.asJson();
            });
        });

        /**
         * GET /accounts
         */
        get("/accounts", (req, resp) -> {
            resp.type("application/json");
            return dsl.transactionResult(() ->
                dsl.select()
                    .from(ACCOUNT)
                    .fetch()
                    .into(AccountRecord.class)
                    .stream()
                    .map(UserAccount::new)
                    .collect(Collectors.toList())
            );
        });

        /**
         * GET /accounts/{account}
         */
        get("/accounts/:id", (req, resp) -> {
            resp.type("application/json");
            return dsl.transactionResult(() ->
                dsl.select()
                    .from(ACCOUNT)
                    .where(ACCOUNT.ID.eq(req.params(":id"))))
                    .fetch()
                    .into(AccountRecord.class)
                    .stream()
                    .map(UserAccount::new)
                    .findFirst().orElseThrow(
                        ()-> new IllegalArgumentException("account not found")
                );
        });

        exception(IllegalArgumentException.class, (exception, req, resp) -> {
            resp.type("application/json");
            resp.status(400);
            resp.body(invalidInput(exception.getMessage()));
        });
    }

    private String invalidInput(String message){
        return mapper.createObjectNode()
            .put("Invalid Input", message)
            .toPrettyString();
    }
}
