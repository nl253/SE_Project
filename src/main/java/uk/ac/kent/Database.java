package uk.ac.kent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * NOTE make sure that a database called yuconz exists on dragon.kent.ac.uk
 *
 * @author nl253
 */

public final class Database {

    /** Logger for the class */
    private static final Logger log = Logger.getAnonymousLogger();

    private static final String PROTOCOL = "jdbc";
    private static final String DBTYPE = "mysql";
    private static final String HOST = "dragon.kent.ac.uk";
    private static final String DATABASE = "yuconz";
    private static final String URI = String
            .format("%s:%s:%s/%s", PROTOCOL, DBTYPE, HOST, DATABASE);
    private static boolean connected;
    private static Statement statement;
    private static Connection connection;

    private Database() {}

    /**
     * @param queryString sql query
     * @return Optionally a ResultSet
     */

    @SuppressWarnings({"MethodWithMultipleReturnPoints", "WeakerAccess"})
    static Optional<ResultSet> query(final String queryString) {
        if (!connected) connect();
        if (getStatement().isPresent()) try {
            return Optional.ofNullable(statement.executeQuery(queryString));
        } catch (final SQLException ignored) {}
        return Optional.empty();
    }

    @SuppressWarnings("CallToPrintStackTrace")
    private static void connect() {
        if (!connected) try {
            connection = DriverManager.getConnection(URI);
            statement = connection.createStatement();
            connected = true;
        } catch (final SQLException ignored) {}
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
}
