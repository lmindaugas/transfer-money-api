package account;

import static db.Tables.ACCOUNT;

import db.tables.records.AccountRecord;
import org.jooq.DSLContext;

public class AccountService {

    private final DSLContext ctx;

    public AccountService(DSLContext ctx) {
        this.ctx = ctx;
    }

    public synchronized void persist(UserAccount account) {
        ctx.selectFrom(ACCOUNT)
            .where(ACCOUNT.ID.eq(account.id()))
            .fetch()
            .stream()
            .findFirst()
            .ifPresentOrElse(
                record -> {
                    throw new IllegalArgumentException("Account already exist: " + record.getId());
                },
                () -> createRecord(account).store()
            );
    }

    private AccountRecord createRecord(UserAccount account) {
        AccountRecord record = ctx.newRecord(ACCOUNT);
        record.setId(account.id());
        record.setName(account.name());
        record.setBalance(account.balance());
        record.setCurrency(account.currency());
        record.setStatus(account.status());
        return record;
    }
}
