package uk.ac.kent;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.Transient;
import uk.ac.kent.controllers.BaseController;

/**
 * For details about the API see <a href="http://docs.jboss.org/hibernate/orm/5.2/quickstart/html_single/#hibernate-gsg-tutorial-basic-mapping">
 * Hibernate Quick Start </a>.
 * <p>
 * Each {@link BaseController} stores a reference to this
 * {@link Database} and executes {@link Query} on it.
 * <p>
 * The result of those is transferred to the GUI which handles presentation.
 *
 * @author Norbert
 */

@SuppressWarnings({"WeakerAccess", "AlibabaCommentsMustBeJavadocFormat", "PublicMethodNotExposedInInterface", "FieldCanBeLocal", "ClassHasNoToStringMethod", "NonBooleanMethodNameMayNotStartWithQuestion"})
public final class Database {

    /** Logger for the class */
    @Transient
    private static final Logger log = Logger.getAnonymousLogger();

    /**
     * Development database (because dragon.kent.ac.uk can only be accessed from inside of campus).
     * The H2 (see <a href="http://www.h2database.com/html/main.html">the docs</a>)
     * database can be stored in memory ie in RAM (or as a file) so
     * <em>you don't need a "real" database to run and test the app</em>.
     * <p>
     * The names `dragon.kent.ac.uk` and `dev` correspond to a persistence-unit names in persistence.xml.
     * Comment out before handing in: {@code private static final EntityManagerFactory sessionFactory = Persistence .createEntityManagerFactory("dragon.kent.ac.uk"); }
     */

    private static final EntityManagerFactory sessionFactory = Persistence
            .createEntityManagerFactory("dev");

    /** current session */
    private static EntityManager session;

    /** current transaction */
    private static EntityTransaction transaction;

    /** cannot be instantiated */
    private Database() {}

    /**
     * @return true iff the {@link Database} has an active {@link javax.transaction.Transaction}
     */

    public static boolean inTransaction() {
        return (transaction != null) && transaction.isActive();
    }

    /**
     * @return true iff the {@link Database} has an open {@link org.hibernate.Session}
     */

    public static boolean openSession() {
        return (session != null) && session.isOpen();
    }

    /**
     * To be run <strong>BEFORE</strong> interacting with the database. Used internally.
     * See {@link javax.transaction.Transaction} for details.
     */

    static void beginTransaction() {
        session = sessionFactory.createEntityManager();
        transaction = session.getTransaction();
        transaction.begin();
    }

    /**
     * To be run <strong>AFTER</strong> interacting with the database. Used internally.
     * See {@link javax.transaction.Transaction} for details.
     */

    static void finishTransaction() {
        transaction.commit();
        session.close();
    }

    /**
     * Rollback the {@link javax.transaction.Transaction}. See {@link javax.transaction.Transaction} for details.
     */

    @SuppressWarnings({"rawtypes", "LawOfDemeter"})
    public static void rollback() {
        if (transaction != null) transaction.rollback();
    }

    /**
     * Pass SQL (optionally pass parameters), return results as {@link List}.
     *
     * @param queryString SQL ({@link String})
     * @param params parameters to pass into query
     * @return List of results
     */

    @SuppressWarnings({"rawtypes", "LawOfDemeter", "OverloadedVarargsMethod"})
    public static List query(final String queryString, final Object... params) {

        final Query query = session.createQuery(queryString);

        // bind parameters
        for (int i = 0; i < params.length; i++)
            query.setParameter(i, params[i]);

        final List result = query.getResultList();

        return result;
    }

    /**
     * @param funct a custom {@link Function} (a query) that accepts an {@link EntityManager} and returns a {@link List} of results
     * @return results of the query
     */

    @SuppressWarnings({"rawtypes", "LawOfDemeter"})
    public static List query(final Function<EntityManager, List> funct) {
        return funct.apply(session);
    }

    /**
     * @param funct a {@link Consumer} that accepts an {@link EntityManager} to run with an open session
     */

    @SuppressWarnings({"rawtypes", "LawOfDemeter"})
    public static void query(final Consumer<EntityManager> funct) {
        funct.accept(session);
    }

    /**
     * Remove the {@link javax.persistence.Entity} from this {@link Database}.
     *
     * @param entity an {@link Object} to remove
     */

    public static void remove(final Object entity) {
        session.remove(entity);
    }

    /**
     * Save the {@link javax.persistence.Entity} in this {@link Database}.
     *
     * @param entity {@link Object} to be saved
     */

    public static void persist(final Object entity) {
        session.persist(entity);
    }

    /**
     * Begin {@link javax.transaction.Transaction}, merge the {@link javax.persistence.Entity} in this {@link Database} and close {@link javax.transaction.Transaction}.
     *
     * @param entity {@link Object} to be saved
     */

    public static void merge(final Object entity) {
        session.merge(entity);
    }

    @SuppressWarnings("UnnecessarilyQualifiedInnerClassAccess")
    @Override
    public String toString() {
        // @formatter:off
        return MessageFormat.format("Database<open={0}, inTransaction={1}, properties=[{2}]>",
                                    openSession(),
                                    inTransaction(),
                                    sessionFactory.getProperties()
                                            .entrySet()
                                            .stream()
                                            .map((Map.Entry<String, Object> x) -> MessageFormat.format("{0}: {1}", x.getKey(), x.getValue()))
                                            .collect(Collectors.joining(", ")));
        // @formatter:on
    }
}
