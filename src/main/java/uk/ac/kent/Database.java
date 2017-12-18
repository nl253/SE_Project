package uk.ac.kent;

import java.sql.Connection;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.persistence.Entity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author nl253
 */

@SuppressWarnings({"WeakerAccess", "AlibabaCommentsMustBeJavadocFormat", "PublicMethodNotExposedInInterface", "FieldCanBeLocal", "ClassHasNoToStringMethod"})
public final class Database {

    /** Logger for the class */
    private static final Logger log = Logger.getAnonymousLogger();

    private static final SessionFactory sessionFactory = new Configuration()
            .buildSessionFactory();
    private static Connection connection;

    private Database() {}

    /**
     * A user creates a session when she logs in.
     *
     * @return Session
     */

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    @SuppressWarnings({"rawtypes", "LawOfDemeter"})
    public static Stream query(final String queryString) {
        return getSession().createQuery(queryString).getResultStream();
    }

    public static void save(final Entity entity) {
        getSession().persist(entity);
    }
}
