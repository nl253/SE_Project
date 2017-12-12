package uk.ac.kent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.logging.Logger;
import org.hibernate.SessionFactory;
import uk.ac.kent.people.Employee;

/**
 * @author nl253
 */

@SuppressWarnings("AlibabaCommentsMustBeJavadocFormat")
public final class Database {

    /** Logger for the class */
    private static final Logger log = Logger.getAnonymousLogger();

    SessionFactory factory = new SessionFactory() {};

    private static boolean connected;
    private static Statement statement;
    private static Connection connection;

    private Database() {}

    /**
     * @param queryString sql query
     * @return Optionally a ResultSet
     */

    @SuppressWarnings({"MethodWithMultipleReturnPoints", "WeakerAccess", "IfMayBeConditional"})
    static Optional<ResultSet> query(final String queryString) throws SQLException {
        if (!connected) connect();
        if (getStatement().isPresent())
            return Optional.ofNullable(statement.executeQuery(queryString));
        else return Optional.empty();
    }

    @SuppressWarnings("CallToPrintStackTrace")
    private static void connect() throws SQLException {
        if (connected) return;
        connection = DriverManager.getConnection(URI);
        statement = connection.createStatement();
        connected = true;
    }

    /**
     * @throws SQLException when something goes wrong when communicating with datatabase
     */

    static void close() throws SQLException {
        if (!connected) return;
        statement.close();
        connection.close();
        connected = false;
    }

    static boolean isConnected() { return connected; }

    @SuppressWarnings({"StaticVariableUsedBeforeInitialization", "WeakerAccess"})
    static Optional<Statement> getStatement() {
        return Optional.ofNullable(statement);
    }

    @SuppressWarnings("StaticVariableUsedBeforeInitialization")
    static Optional<Connection> getConnection() {
        return Optional.ofNullable(connection);
    }

    @SuppressWarnings({"AlibabaAvoidCommentBehindStatement", "NestedTryStatement"})
    public static void main(final String... args) throws SQLException {

        // @formatter:off
        try (Connection conn = DriverManager.getConnection(
                MessageFormat.format("{0}:{1}:{2}", PROTOCOL, DIALECT, DATABASE))) {

            try (Statement stmt = conn.createStatement()) {
                stmt.setQueryTimeout(10);
                stmt.executeUpdate("DROP TABLE IF EXISTS person");
                stmt.executeUpdate("CREATE TABLE person (id INTEGER, name TEXT)");
                stmt.executeUpdate("INSERT INTO person VALUES(1, 'leo')");

                try (ResultSet results = stmt.executeQuery("SELECT * FROM person")) {

                    while (results.next()) System.out.println(
                            MessageFormat.format("name = {0}, id = {1}",
                                                 results.getString("name"),
                                                 results.getInt("id")));
                    // @formatter:on
                }
            }
        }


    }
}
