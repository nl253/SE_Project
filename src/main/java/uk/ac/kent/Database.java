package uk.ac.kent;

import java.sql.Connection;
import java.text.MessageFormat;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author nl253
 */

@SuppressWarnings({"AlibabaCommentsMustBeJavadocFormat", "PublicMethodNotExposedInInterface", "FieldCanBeLocal"})
public final class Database {

    /** Logger for the class */
    private static final Logger log = Logger.getAnonymousLogger();

    private final Configuration configuration;
    private final SessionFactory sessionFactory;
    private static Connection connection;

    private Database() {
        configuration = new Configuration();
        sessionFactory = configuration.buildSessionFactory();
    }

    Session getSession() { return sessionFactory.openSession(); }

    @SuppressWarnings({"rawtypes", "LawOfDemeter"})
    public Stream query(final String queryString) {
        return getSession().createQuery(queryString).getResultStream();
    }

    @Override
    public String toString() {
        return MessageFormat
                .format("Database<configuration={0}, sessionFactory={1}", configuration
                        .toString(), sessionFactory.toString());
    }
}
