package yuconz.records.staff;

import java.time.LocalDate;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import yuconz.exceptions.DateException;
import yuconz.exceptions.StaffNoException;
import yuconz.exceptions.ValidationException;
import yuconz.records.MockDataGenerator;

import static java.lang.System.exit;
import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The type User test.
 *
 * @param <A> the type parameter
 * @param <U> the type parameter
 */
final class UserTest<A, U extends User> extends MockDataGenerator<A> {

    /**
     * Gets staff number.
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    @Tag("AnnualPerformanceReviews")
    @Tag("Validation")
    final void getStaffNo() {
        assertNotNull(randUser().getStaffNo());
    }

    /**
     * Negative staff number.
     */
    @RepeatedTest(10)
    @Tag("Authentication")
    @Tag("Validation")
    final void negStaffNo() {
        // check different combinations
        assertThrows(ValidationException.class, () -> new Employee(-randStaffNo(), randSection(), randEmploymentDate()));
        assertThrows(ValidationException.class, () -> new Manager(-randStaffNo(), randSection(), randEmploymentDate()));
        assertThrows(ValidationException.class, () -> new Director(-randStaffNo(), randSection(), randEmploymentDate()));
    }

    /**
     * Gets employment start.
     */
    @RepeatedTest(10)
    @Tag("AnnualPerformanceReviews")
    void getEmploymentStart() {
        assertNotNull(randUser().getEmploymentStart());
    }

    /**
     * Future employment date.
     */
    @Test
    @Tag("AnnualPerformanceReviews")
    @Tag("Validation")
    void futureEmploymentDate() {
        assertThrows(ValidationException.class, () -> new Employee(randStaffNo(), randSection(), now().plusYears(1 + RAND.nextInt(10))));
        assertThrows(ValidationException.class, () -> new Manager(randStaffNo(), randSection(), now().plusYears(1 + RAND.nextInt(10))));
        assertThrows(ValidationException.class, () -> new Director(randStaffNo(), randSection(), now().plusYears(1 + RAND.nextInt(10))));
    }

    /**
     * Gets section.
     */
    @Test
    @Tag("Authentication")
    @Tag("AnnualPerformanceReviews")
    @Tag("PersonalDetails")
    void getSection() {
        assertNotNull(randUser().getSection());
    }

    /**
     * Gets job title.
     */
    @Test
    @Tag("AnnualPerformanceReviews")
    void getJobTitle() {
        assertNotNull(randUser().getJobTitle());
    }

    /**
     * Rand user u.
     *
     * @return the u
     */
    U randUser() {

        try {

            final Section section = randSection();
            final int staffNo = randStaffNo();
            final LocalDate employmentStart = randEmploymentDate();

            switch (RAND.nextInt(3)) {
                case 0:
                    return (U) new Employee(staffNo, section, employmentStart);

                case 1:
                    return (U) new Manager(staffNo, section, employmentStart);

                case 2:
                    return (U) new Director(staffNo, section, employmentStart);
            }
        } catch (final StaffNoException | DateException e) {
            e.printStackTrace();
            exit(1);
            return null;
        }

        exit(1);
        return null;
    }

}
