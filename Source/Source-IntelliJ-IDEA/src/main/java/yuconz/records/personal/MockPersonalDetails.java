package yuconz.records.personal;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import yuconz.exceptions.ValidationException;
import yuconz.records.MockDataGenerator;

import static java.time.LocalDate.now;

/**
 * Mock personal details.
 *
 * @param <A> the type parameter
 */
@SuppressWarnings("FeatureEnvy")
public final class MockPersonalDetails<A> extends MockDataGenerator<A> implements PersonalDetails {

    private static final PersonalDetails SELF = new MockPersonalDetails();
    private static final int NO_REC = 50;
    private Collection<PersonalDetailRecord> records;

    @SuppressWarnings({"ObjectAllocationInLoop", "CallToSystemExit"})
    private MockPersonalDetails() {
        try {
            records = new LinkedList<>();

            for (int i = 1; i < (NO_REC - 1); i++) {

                // @formatter:off
                @SuppressWarnings("ImplicitNumericConversion") final PersonalDetailRecord details = new ValidatingPersonalDetailsBuilder(i)
                        .setFirstName(randFirstName())
                        .setLastName(randFirstName())
                        .setAddress(randAddress())
                        .setDateOfBirth(now().minusYears(MIN_AGE + RAND.nextInt(MAX_AGE - MIN_AGE)))
                        .setMobileNo(randPhoneNo())
                        .setTelNo(randPhoneNo())
                        .setPostcode(randPostcode())
                        .setCounty(randFirstName())
                        .setCity(randFirstName())
                        .setEmergencyContact(String.format("%s %s", randFirstName(), randFirstName()))
                        .setEmergencyContactNo(randPhoneNo())
                        .create();
                records.add(details);
                // @formatter:on
            }
        } catch (@SuppressWarnings("OverlyBroadCatchBlock")
        final ValidationException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static PersonalDetails getInstance() {
        return SELF;
    }

    @SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
    @Override
    public final Collection<PersonalDetailRecord> get() {
        return records;
    }

    @Override
    public String toString() {

        final StringBuilder builder = new StringBuilder(50);
        final Iterator<PersonalDetailRecord> iterator = records.iterator();
        builder.append(getClass().getSimpleName());
        builder.append('<');

        if (!records.isEmpty()) {
            int i = 0;
            //noinspection ForLoopThatDoesntUseLoopVariable,ForLoopThatDoesntUseLoopVariable
            for (PersonalDetailRecord a = iterator.next(); (i < 3) && iterator.hasNext(); a = iterator.next()) {
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
