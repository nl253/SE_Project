package uk.ac.kent;

import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import uk.ac.kent.models.people.Director;
import uk.ac.kent.models.people.Manager;

/**
 * @author nl253
 */

@SuppressWarnings({"WeakerAccess", "AlibabaCommentsMustBeJavadocFormat", "PublicMethodNotExposedInInterface", "FieldCanBeLocal", "ClassHasNoToStringMethod"})
public final class Database {

    /** Logger for the class */
    private static final Logger log = Logger.getAnonymousLogger();

    private static final SessionFactory sessionFactory = new Configuration().buildSessionFactory();

    // cannot be instantiated
    private Database() {}

    /**
     * @return Session
     */

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    /**
     * Pass SQL, return results as {@link Stream}.
     *
     * @param queryString SQL
     * @return Stream of results
     */

    @SuppressWarnings({"rawtypes", "LawOfDemeter"})
    public static Stream query(final String queryString) {
        return getSession().createQuery(queryString).getResultStream();
    }

    /**
     * Save the entity in this {@link Database}.
     *
     * @param entity Object to be saved
     */

    public static void save(final Object entity) {
        getSession().persist(entity);
    }

    /**
     * Fill the database.
     * Creating a {@link Manager} will create a bunch of employees and assign them to this {@link Manager}.
     * Creating an {@link uk.ac.kent.models.people.Employee} causes all relevant records to be created (although the data is fake).
     * This includes {@link uk.ac.kent.models.records.PersonalDetailsRecord} and {@link uk.ac.kent.models.records.EmploymentDetailsRecord}.
     */

    public static void populate() {
        // Make Directors
        IntStream.range(0, 4).forEach(x -> save(Director.fake()));
        // Make Managers
        IntStream.range(0, 25).forEach(x -> save(Manager.fake()));
    }
}
