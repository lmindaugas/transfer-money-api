import static spark.Spark.port;

import org.apache.commons.dbcp.BasicDataSource;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.ThreadLocalTransactionProvider;

import java.io.IOException;
import java.util.Properties;

/**
 * A simple RESTful service to transfer money between two accounts.
 *
 */
public class Main {
    public static void main(String[] args) throws IOException {
        new PaymentsHandler(init()).handle();
    }

    private static DSLContext init() throws IOException {
        final BasicDataSource ds = new BasicDataSource();
        final Properties properties = new Properties();
        properties.load(Main.class.getResourceAsStream("/config.properties"));

        ds.setDriverClassName(properties.getProperty("db.driver"));
        ds.setUrl(properties.getProperty("db.url"));
        ds.setUsername(properties.getProperty("db.username"));
        ds.setPassword(properties.getProperty("db.password"));

        final ConnectionProvider cp = new DataSourceConnectionProvider(ds);
        final Configuration configuration = new DefaultConfiguration()
                .set(cp)
                .set(SQLDialect.H2)
                .set(new ThreadLocalTransactionProvider(cp, true));
        port(8080);

        return DSL.using(configuration);
    }
}