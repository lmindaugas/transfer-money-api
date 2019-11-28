package payment;

import static db.Tables.ACCOUNT;
import static db.tables.Payment.PAYMENT;

import com.fasterxml.jackson.databind.JsonNode;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.function.Function;

import account.Account;
import account.Amount;
import db.tables.records.AccountRecord;
import db.tables.records.PaymentRecord;
import org.jooq.DSLContext;
import org.jooq.Field;

public class PaymentService {

    private final DSLContext ctx;

    public PaymentService(DSLContext ctx) {
        this.ctx = ctx;
    }

    public void execute(Payment payment) {
        JsonNode json = payment.asJson();
        Account debtorAccount = new Account(json.at("/Data/DebtorAccount"));
        Account creditorAccount = new Account(json.at("/Data/CreditorAccount"));
        Amount amount = new Amount(json.at("/Data/InstructedAmount"));

        Optional<AccountRecord> debtor = findAccount(debtorAccount);
        Optional<AccountRecord> creditor = findAccount(creditorAccount);
        if (debtor.isPresent() && creditor.isPresent() && hasMoney(debtor, amount)) {
            update(payment, debtorAccount, creditorAccount, amount);
        }
    }

    private boolean hasMoney(Optional<AccountRecord> debtorRecord, Amount amount) {
        debtorRecord.map(AccountRecord::getBalance)
            .filter(balance -> balance.compareTo(amount.value()) >= 0)
            .orElseThrow(() -> new IllegalArgumentException("Debtor has insufficient amount of money"));
        return true;
    }

    private void update(Payment payment, Account debtor, Account creditor, Amount amount) {
        updateMoney(debtor, amount.value(), am -> ACCOUNT.BALANCE.sub(am));
        updateMoney(creditor, amount.value(), am -> ACCOUNT.BALANCE.add(am));
        persistPayment(payment);
    }

    private Optional<AccountRecord> findAccount(Account account) {
        return ctx.selectFrom(ACCOUNT)
            .where(ACCOUNT.ID.eq(account.id()))
            .fetch()
            .stream()
            .findFirst()
            .or(() -> {
                throw new IllegalArgumentException("Account not found:" + account.id());
            });
    }

    private void persistPayment(Payment payment) {
        PaymentRecord paymentRecord = ctx.newRecord(PAYMENT);
        paymentRecord.setPaymentId(payment.id());
        paymentRecord.setPayload(payment.toString());
        paymentRecord.setTimestamp(new Timestamp(System.currentTimeMillis()));
        paymentRecord.store();
    }

    private void updateMoney(Account account, BigDecimal amount, Function<BigDecimal, Field<BigDecimal>> calc) {
        boolean executed = ctx.update(ACCOUNT)
            .set(ACCOUNT.BALANCE, calc.apply(amount))
            .where(ACCOUNT.ID.eq(account.id()))
            .execute() > 0;
        if (!executed) {
            throw new IllegalArgumentException("Could not update the money from account: " + account.id());
        }
    }
}
