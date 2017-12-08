package database;

import java.sql.*;
import java.util.Optional;

/**
 * @author nl253
 */

@SuppressWarnings({"NewClassNamingConvention", "CallToDriverManagerGetConnection", "CallToSystemExit", "CallToPrintStackTrace", "JDBCResourceOpenedButNotSafelyClosed", "JDBCExecuteWithNonConstantString", "StaticVariableUsedBeforeInitialization", "StaticVariableMayNotBeInitialized", "UtilityClassCanBeEnum", "FieldNamingConvention", "MethodWithMultipleReturnPoints", "SameParameterValue", "UseOfSystemOutOrSystemErr"})
public final class Shortcuts {

    private static final String PROTOCOL = "jdbc";
    private static final String DBTYPE = "sqlite";
    private static final String DATABASE = "database.sqlite3";
    private static final String URI = String
            .format("%s:%s:%s", PROTOCOL, DBTYPE, DATABASE);
    private static boolean connected;
    private static Statement statement;
    private static Connection connection;

    private Shortcuts() {}

    /**
     * @param queryString
     * @return
     *
     * @throws SQLException
     */

    @SuppressWarnings({"MethodWithMultipleReturnPoints", "WeakerAccess"})
    static Optional<ResultSet> query(final String queryString) throws SQLException {
        if (!connected) connect();
        if (getStatement().isPresent()) try {
            return Optional.ofNullable(statement.executeQuery(queryString));
        } catch (final SQLException ignored) {}
        return Optional.empty();
    }

    /**
     * @throws SQLException
     */

    @SuppressWarnings("CallToPrintStackTrace")
    private static void connect() {
        if (!connected) try {
            connection = DriverManager.getConnection(URI);
            statement = connection.createStatement();
            connected = true;
        } catch (final SQLException ignored) {}
    }

    /**
     * @throws SQLException
     */

    public static void close() throws SQLException {
        if (!connected) return;
        statement.close();
        connection.close();
        connected = false;
    }

    public static boolean isConnected() { return connected; }

    @SuppressWarnings({"StaticVariableUsedBeforeInitialization", "WeakerAccess"})
    static Optional<Statement> getStatement() {
        return Optional.ofNullable(statement);
    }

    @SuppressWarnings("StaticVariableUsedBeforeInitialization")
    public static Optional<Connection> getConnection() {
        return Optional.ofNullable(connection);
    }


    public static void main(final String... args) throws SQLException {
        query("CREATE if not exists DATABASE new_database")
                .ifPresent(System.out::println);
    }
}
