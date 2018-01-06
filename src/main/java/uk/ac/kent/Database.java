package uk.ac.kent;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.Transient;
import uk.ac.kent.controllers.BaseController;
import uk.ac.kent.models.people.Director;
import uk.ac.kent.models.people.Manager;

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

    // the name `dragon.kent.ac.uk` corresponds to a persistance-unit name in persistance.xml
    private static final EntityManagerFactory sessionFactory = Persistence
            .createEntityManagerFactory("dragon.kent.ac.uk");

    /** current session */
    private static EntityManager session;

    /** current transaction */
    private static EntityTransaction transaction;

    /** cannot be instantiated */
    private Database() {}

    public static boolean inTransaction() {
        return (transaction != null) && transaction.isActive();
    }

    public static boolean openSession() {
        return (session != null) && session.isOpen();
    }

    /**
     * To be run <strong>BEFORE</strong> interacting with the database. Used internally.
     */

    private static void beginTransaction() {
        session = sessionFactory.createEntityManager();
        transaction = session.getTransaction();
        transaction.begin();
    }

    /**
     * To be run <strong>AFTER</strong> interacting with the database. Used internally.
     */

    private static void finishTransaction() {
        transaction.commit();
        session.close();
    }

    /**
     * Pass SQL (optionally pass parameters), return results as {@link List}.
     *
     * @param queryString SQL
     * @param params parameters to pass into query
     * @return List of results
     */

    @SuppressWarnings({"rawtypes", "LawOfDemeter", "OverloadedVarargsMethod"})
    public static List query(final String queryString, final Object... params) {

        beginTransaction();

        final Query query = session.createQuery(queryString);

        // bind parameters
        for (int i = 0; i < params.length; i++)
            query.setParameter(i, params[i]);

        final List result = query.getResultList();

        finishTransaction();

        return result;
    }

    /**
     * @param funct a custom {@link Function} (a query) that accepts an {@link EntityManager} and returns a {@link List} of results
     * @return results of the query
     */

    @SuppressWarnings({"rawtypes", "LawOfDemeter"})
    public static List query(final Function<EntityManager, List> funct) {
        beginTransaction();
        final List results = funct.apply(session);
        finishTransaction();
        return results;
    }

    /**
     * @param funct a {@link Consumer} that accepts an {@link EntityManager} to run with an open session
     */

    @SuppressWarnings({"rawtypes", "LawOfDemeter"})
    public static void query(final Consumer<EntityManager> funct) {
        beginTransaction();
        funct.accept(session);
        finishTransaction();
    }

    /**
     * @param entity an entity to remove
     */

    public static void remove(final Object entity) {
        beginTransaction();
        session.remove(entity);
        finishTransaction();
    }

    /**
     * Save the entity in this {@link Database}.
     *
     * @param entity Object to be saved
     */

    public static void save(final Object entity) {
        beginTransaction();
        session.persist(entity);
        finishTransaction();
    }

    /**
     * Fill the database.
     * Creating a {@link Manager} will create a bunch of employees and assign them to this {@link Manager}.
     * Creating an {@link uk.ac.kent.models.people.Employee} causes all relevant records to be created (although the data is fake).
     * This includes {@link uk.ac.kent.models.records.PersonalDetailsRecord} and {@link uk.ac.kent.models.records.EmploymentDetailsRecord}.
     */

    public static void populate() {
        beginTransaction();
        try {
            // Make Directors
            IntStream.range(0, 4)
                    .forEach((int x) -> session.persist(Director.fake()));

            // Make Managers
            IntStream.range(0, 25)
                    .forEach((int x) -> session.persist(Manager.fake()));

            finishTransaction();

        } catch (Exception e) {
            System.err.println(e.getMessage());
            transaction.rollback();
        }
    }

    @SuppressWarnings("UnnecessarilyQualifiedInnerClassAccess")
    @Override
    public String toString() {
        return MessageFormat
                .format("Database<open={0}, inTransaction={1} properties=[{2}]>", openSession(), inTransaction(), sessionFactory
                        .getProperties().entrySet().stream()
                        .map((Map.Entry<String, Object> x) -> MessageFormat
                                .format("{0}: {1}", x.getKey(), x.getValue()))
                        .collect(Collectors.joining(", ")));
    }
}
