package yuconz.records.credentials;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import yuconz.exceptions.ValidationException;
import yuconz.records.Records;

/**
 * Mock credentials.
 */
@SuppressWarnings({"Singleton", "NonThreadSafeLazyInitialization"})
public final class MockCredentials implements Records<CredentialsRecord> {

    @SuppressWarnings("StaticVariableOfConcreteClass")
    private static MockCredentials self;

    private final Collection<CredentialsRecord> credentials;

    @SuppressWarnings({"ValueOfIncrementOrDecrementUsed", "UnusedAssignment", "CallToPrintStackTrace", "CallToSystemExit"})
    private MockCredentials() {
        credentials = new LinkedList<>();
        //noinspection OverlyBroadCatchBlock
        try {
            int i = 1;
            final String p = "password";
            // @formatter:off
            credentials.add(new CredentialsRecord(i++, "sde123", p));
            credentials.add(new CredentialsRecord(i++, "sdm123", p));
            credentials.add(new CredentialsRecord(i++, "sdd123", p));
            credentials.add(new CredentialsRecord(i++, "hre123", p));
            credentials.add(new CredentialsRecord(i++, "hrm123", p));
            credentials.add(new CredentialsRecord(i++, "hrd123", p));
            credentials.add(new CredentialsRecord(i++, "ade123", p));
            credentials.add(new CredentialsRecord(i++, "adm123", p));
            credentials.add(new CredentialsRecord(i++, "add123", p));
            credentials.add(new CredentialsRecord(i++, "sme123", p));
            credentials.add(new CredentialsRecord(i++, "smm123", p));
            credentials.add(new CredentialsRecord(i++, "smd123", p));
            // @formatter:on
        } catch (final ValidationException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    @SuppressWarnings("StaticVariableUsedBeforeInitialization")
    public static Records<CredentialsRecord> getInstance() {
        if (self == null) self = new MockCredentials();
        return self;
    }

    @Override
    public Collection<CredentialsRecord> get() {
        return credentials;
    }

    @Override
    public String toString() {

        final StringBuilder builder = new StringBuilder(50);
        final Iterator<CredentialsRecord> iterator = credentials.iterator();
        builder.append(getClass().getSimpleName());
        builder.append('<');

        if (!credentials.isEmpty()) {
            int i = 0;
            for (CredentialsRecord a = iterator.next(); (i < 3) && iterator.hasNext(); a = iterator.next()) {
                builder.append(a)
                       .append(", ");
                i++;
            }
            builder.append(" ... ");
        }
        builder.append('>');
        return builder.toString();
    }
}
