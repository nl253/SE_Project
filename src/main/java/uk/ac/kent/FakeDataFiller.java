package uk.ac.kent;

import com.github.javafaker.Faker;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import uk.ac.kent.models.other.Address;
import uk.ac.kent.models.people.Director;
import uk.ac.kent.models.people.Employee;
import uk.ac.kent.models.people.Manager;
import uk.ac.kent.models.people.Relative;
import uk.ac.kent.models.records.BaseRecord;
import uk.ac.kent.models.records.EmploymentDetailsRecord;
import uk.ac.kent.models.records.PersonalDetailsRecord;
import uk.ac.kent.models.records.ProbationRecord;
import uk.ac.kent.models.records.PromotionRecord;
import uk.ac.kent.models.records.SalaryIncreaseRecord;
import uk.ac.kent.models.records.TerminationRecord;
import uk.ac.kent.models.records.recommendations.RemainRecommendation;
import uk.ac.kent.models.yuconz.Department;
import uk.ac.kent.models.yuconz.Position;


/**
 * @author Norbert
 */

@SuppressWarnings({"FeatureEnvy", "ImplicitNumericConversion", "WeakerAccess"})
public final class FakeDataFiller {

    public static final int SALARY_UPPER_BOUND = 100_000;
    public static final int SALARY_LOWER_BOUND = 15_000;

    /** Random data generators. */
    private static final Random random = new SecureRandom();
    private static final Faker faker = new Faker(new Locale("en-GB"));
    private static final com.github.javafaker.Address addressFaker = faker
            .address();
    private static final com.github.javafaker.Name nameFaker = faker.name();
    private static final com.github.javafaker.GameOfThrones gotFaker = faker
            .gameOfThrones();

    private FakeDataFiller() {}

    /**
     * @param record a {@link PromotionRecord}
     */

    public static void fillPromotionRecord(final PromotionRecord record) {
        fillRecord(record);
        record.setNewDepartment(randomDepartment());
        record.setNewPosition(randomPosition());
        record.setNewSalary(randomSalary());
        record.setStartDate(record.getModificationDate()
                                    .plusMonths(random.nextInt(44)));
    }

    /**
     * @param relative a {@link Relative}
     */

    public static void fillRelative(final Relative relative) {
        relative.setFirstName(nameFaker.firstName());
        relative.setLastName(nameFaker.lastName());
        relative.setPhoneNumber(faker.phoneNumber().cellPhone());
    }

    /**
     * @param personalDetails a {@link PersonalDetailsRecord}
     * @param address an {@link Address}
     * @param relative a {@link Relative}
     */

    public static void fillPersonalDetailsRecord(final PersonalDetailsRecord personalDetails, final Address address, final Relative relative) {
        personalDetails.setAddress(address);
        personalDetails.setEmail(faker.internet().emailAddress());
        personalDetails.setRelative(relative);
        personalDetails.setFirstName(nameFaker.firstName());
        personalDetails.setLastName(nameFaker.lastName());
    }

    /**
     * @return a random quote ({@link String})
     */

    @SuppressWarnings("ImplicitNumericConversion")
    private static String randomQuote() {
        return gotFaker.quote();
    }

    /**
     * @return a random {@link Department}
     */

    @SuppressWarnings("ImplicitNumericConversion")
    private static Department randomDepartment() {
        return Department.values()[random.nextInt(Department.values().length)];
    }

    /**
     * @param record a {@link TerminationRecord}
     */

    @SuppressWarnings({"AccessingNonPublicFieldOfAnotherObject", "LocalVariableOfConcreteClass", "MagicNumber", "Duplicates"})
    public static void fillTerminationRecord(final TerminationRecord record) {
        fillRecord(record);
        record.setReason(randomQuote());
        record.setEndOfEmployment(record.getModificationDate()
                                          .plusMonths(random.nextInt(44)));
    }

    /**
     * @return a random {@link Position}
     */

    @SuppressWarnings("ImplicitNumericConversion")
    private static Position randomPosition() {
        return Position.values()[random.nextInt(Position.values().length)];
    }

    /**
     * @param lowerBound lower bound of the salary
     * @param upperBound upper bound of the salary
     * @return random salary (long)
     */

    @SuppressWarnings("ImplicitNumericConversion")
    private static long randomSalary(final long lowerBound, final long upperBound) {
        return lowerBound + random.nextInt((int) upperBound);
    }

    /**
     * @return random salary (long)
     */

    private static long randomSalary() {
        return randomSalary(SALARY_LOWER_BOUND, SALARY_UPPER_BOUND);
    }

    /**
     * @param employee an {@link Employee}
     * @param personalDetails a {@link PersonalDetailsRecord}
     * @param employmentDetails an {@link EmploymentDetailsRecord}
     */

    public static void fillEmployee(final Employee employee, final PersonalDetailsRecord personalDetails, final EmploymentDetailsRecord employmentDetails) {
        employee.setPassword(Employee.generatePassword());
        employee.setPersonalDetails(personalDetails);
        employee.setEmploymentDetails(employmentDetails);
    }

    /**
     * @param manager a {@link Manager}
     * @param personalDetails a {@link PersonalDetailsRecord}
     * @param employmentDetails an {@link EmploymentDetailsRecord}
     * @param employees a {@link java.util.List} of {@link Employee}s
     */

    public static void fillManager(final Manager manager, final PersonalDetailsRecord personalDetails, final EmploymentDetailsRecord employmentDetails, final Collection<Employee> employees) {
        manager.setPassword(Employee.generatePassword());
        manager.setPersonalDetails(personalDetails);
        manager.setEmploymentDetails(employmentDetails);
        manager.setEmployees(employees);
    }

    /**
     * @param director a {@link Director}
     * @param personalDetails a {@link PersonalDetailsRecord}
     * @param employmentDetails an {@link EmploymentDetailsRecord}
     */

    public static void fillDirector(final Director director, final PersonalDetailsRecord personalDetails, final EmploymentDetailsRecord employmentDetails) {
        fillEmployee(director, personalDetails, employmentDetails);
    }

    /**
     * @param record {@link EmploymentDetailsRecord}
     */

    public static void fillEmploymentDetailsRecord(final EmploymentDetailsRecord record) {
        record.setPosition(randomPosition());
        record.setDepartment(randomDepartment());
        record.setDateEmployed(randomLocalDate());
        record.setSalary(randomSalary());
    }

    /**
     * Generate a random {@link Integer} between those two values and finally  convert it back to a {@link LocalDate}.
     *
     * @return a random {@link LocalDate}
     */

    private static LocalDate randomLocalDate() {
        final long minDay = LocalDate.now().minusYears(10).toEpochDay();
        final long maxDay = LocalDate.now().plusYears(10).toEpochDay();
        final long randomDay = ThreadLocalRandom.current()
                .nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }


    /**
     * @param record {@link ProbationRecord}
     */

    public static void fillProbationRecord(final ProbationRecord record) {
        fillRecord(record);
        final LocalDate start = randomLocalDate();
        record.setStartDate(start);
        record.setEndDate(start.plusMonths(random.nextInt(44)));
        record.setReason(randomQuote());
    }

    /**
     * @param record a {@link BaseRecord}
     */

    public static void fillRecord(final BaseRecord record) {
        final LocalDate created = randomLocalDate();
        record.setCreationDate(created);
        record.setModificationDate(created.plusMonths(random.nextInt(55)));
        record.setSigned(random.nextBoolean());
    }

    /**
     * @param address {@link Address}
     */

    public static void fillAddress(final Address address) {
        address.setStreet(addressFaker.streetName());
        address.setHouseNumber(addressFaker.buildingNumber());
        address.setCity(addressFaker.city());
        address.setPostCode(addressFaker.zipCode());
    }

    /**
     * @param recommendation {@link RemainRecommendation}
     */

    public static void fillRemainRecommendation(final RemainRecommendation recommendation) {
        recommendation.setModificationDate(randomLocalDate());
    }

    /**
     * @param record {@link SalaryIncreaseRecord}
     */

    public static void fillSalaryIncreaseRecord(final SalaryIncreaseRecord record) {
        record.setNewSalary(randomSalary());
        record.setStartDate(randomLocalDate());
    }
}

