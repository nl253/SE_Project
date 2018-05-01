package yuconz.records;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Random;
import java.util.stream.IntStream;
import yuconz.PermissionLevel;
import yuconz.exceptions.AlreadySignedException;
import yuconz.exceptions.DateException;
import yuconz.exceptions.NoSuchRecordException;
import yuconz.exceptions.NotSignedException;
import yuconz.exceptions.NullValueException;
import yuconz.exceptions.ReviewersNotDiffException;
import yuconz.exceptions.StaffNoException;
import yuconz.exceptions.StringFormatException;
import yuconz.records.credentials.CredentialsRecord;
import yuconz.records.personal.BasePersonalDetailsBuilder;
import yuconz.records.personal.ValidatingPersonalDetailsBuilder;
import yuconz.records.reviews.Allocation;
import yuconz.records.reviews.CompletedReview;
import yuconz.records.reviews.Recommendation;
import yuconz.records.reviews.ReviewBuilder;
import yuconz.records.staff.Section;

import static java.lang.System.exit;
import static java.time.LocalDate.now;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * The Mock data generator.
 *
 * @param <A> the type parameter
 */
@SuppressWarnings({"MethodMayBeStatic", "WeakerAccess", "StringToUpperCaseOrToLowerCaseWithoutLocale", "AlibabaAbstractClassShouldStartWithAbstractNaming", "unchecked"})
public abstract class MockDataGenerator<A> {

    /**
     * The constant MIN_AGE.
     */
    protected static final int MIN_AGE = 20;
    /**
     * The constant MAX_AGE.
     */
    protected static final int MAX_AGE = 60;
    /**
     * The constant CAP_WORD_LEN.
     */
    protected static final int CAP_WORD_LEN = 10;
    /**
     * The constant REVIEWERS.
     */
    protected static final Integer[] REVIEWERS = {2, 3, 5, 6, 8, 9, 11, 12};
    protected static final String[] LAST_NAMES = {"Roberts", "Curtis", "Wilkinson", "Parsons", "Robinson", "Hill", "Harvey", "Phillips", "Holmes", "Farrell", "Mason", "Wright", "Griffin", "Akhtar", "Payne", "Sheppard", "Stephenson", "Smart", "Daniels", "Woods", "Cox", "Pearce", "Ball", "Lee", "Cox", "Holden", "Pugh", "Bird", "Thomson", "Burns", "Woodward", "Francis", "Freeman", "Walker", "Ward", "Taylor", "Thomas", "Hardy", "Gill", "Hughes", "Roberts", "Williams", "Jones", "Craig", "Lewis", "Hayes", "Jones", "Davis", "Connor", "Jackson", "Kemp", "Holden", "Patel", "Powell", "Taylor", "Taylor", "Chapman", "Wall", "Parker", "Brown", "Hall", "Vaughan", "Hayward", "Pritchard", "George", "Cox", "Martin", "Parsons", "Smith", "Thompson", "Wilson", "Ford", "Taylor", "Archer", "Storey", "Ellis", "Bradley", "Baxter", "Ward", "Parkinson", "Anderson", "Davis", "Whitehead", "Lees", "Foster", "Hudson", "Fletcher", "Holden", "Murphy", "Begum", "Mills", "Richards", "Sanders", "Allan", "Reynolds", "Jones", "Holmes", "Watkins", "Edwards", "Khan"};
    protected static final String[] ADDRESSES = {"Flat 5 Davies brook Lewismouth B5 5JH", "Studio 61w Lynch drives Lake George GL8R 9JS", "58 Robinson coves West Francesca TD7Y 9NS", "Flat 7 Young knolls Susanside GU32 7EQ", "50 Naomi walk Powellborough HD7 9PF", "530 Owen hills Thorpeshire L8 7PA", "793 Christine tunnel Malcolmfort L09 0AE", "Studio 98X Gallagher valleys Joneston WD44 1RX", "Flat 85d Justin corners North Markfort BS0H 8LJ", "Studio 8 Amelia forge Robertschester W59 2LP", "492 Graham gardens Port Mohammad KA56 5BS", "Studio 7 Dorothy port Alexanderburgh EN03 4TX", "Flat 98 Newton valleys West Danielle W4J 8FP", "5 Kieran road West Caroline SL0B 0EU", "259 Hart corner Christineberg DL0N 1AQ", "Studio 76r Wright drives West Clifford TF7E 4ZZ", "597 Cook shoals Port Harriet LL9P 2NP", "Flat 2 Jackson stream Lake Reecebury L5U 7JW", "Studio 56 Hawkins prairie Robertsfort JE18 6XP", "6 Mitchell plaza New Leighshire DG47 6BU", "Studio 9 Kimberley knoll Joycetown PR0N 6SL", "09 Dixon prairie Elizabethport WC4H 7UJ", "Flat 68 Edwards terrace Deanchester NW9H 3DD", "Studio 3 Karen drives Hughesview WV3 6BN", "060 Robertson shoals Hallland CT45 1PN", "Flat 7 Jeremy keys South Nigelborough S4T 1WT", "45 Holloway manors Port Leslie W7D 7BT", "88 Jack inlet Natashaview E6 5SF", "3 William village Barrymouth G3 2HJ", "4 Natalie inlet Rosemarymouth G7 8UE", "62 Donnelly harbors Powerfurt W9 8QU", "2 Jones drive Stephanieberg KA68 0TH", "Studio 3 Geoffrey alley West Owen E3 2DE", "Flat 34J Grace crest Tommouth SR56 9XN", "Studio 57z Taylor knolls Ameliahaven B6 6AR", "889 Clarke greens Barkertown B5 6PP", "Flat 07f Josephine manors Turnbullville DT6 5LX", "Studio 79y Jones lakes Port Cameron B1T 2EW", "Flat 21 Charles corner East Norman TW27 6TY", "Flat 13 George burgs Carolynhaven N4 9UX", "18 Marcus field Cliveport TN0 6HU", "Flat 11D Collins trail Woodwardburgh M5 2BG", "3 Mills trafficway Port Russell TN0P 3FU", "Flat 75I Joshua cliffs East Naomi B1 0RR", "Studio 40 Jade forks Joneschester GL58 4SL", "Studio 27c Brown fall Russellborough LE96 5SL", "7 Rees locks Patrickfurt B2 2FF", "05 Howe pine North Francisfurt NP90 3EN", "Studio 96 Anna plain Claytonside W72 6BZ", "96 Briggs square New Glen E6C 0LG", "Studio 5 Hawkins garden New Josephineborough W3 0GY", "Studio 23 Joyce tunnel Bensonchester CM8 5TH", "Studio 3 Cartwright island Brownville N67 1LA", "46 Gerard stream Williamsmouth B4G 7PG", "3 Robinson plaza South Damian W5K 0UJ", "Flat 34z Cox place Lake Richard W0 5GH", "2 Rees park Andersonport B62 9JW", "99 Moore isle Lake Elizabethville HR6 6HF", "Studio 88W Carl burgs Lisaside M58 2QE", "2 Tracey forest Phillipschester PA2M 3FW", "Studio 60 Bird overpass North Guymouth N1J 0DL", "Flat 19N Randall fields East Nigel WC43 4TB", "5 Bennett rapid New Jaystad LE3W 9GE", "Flat 33 Joanna roads West Denisechester M84 5GP", "Studio 57 Owen drives Allantown N3 9NP", "Flat 70 Smith rue Marionmouth W54 5FN", "Flat 6 White hill Mathewshire L8F 4JR", "Studio 23R Jessica loaf Fullerhaven S6 0DL", "Studio 21 Blake roads Zoehaven S71 1AP", "Studio 84a Katie flat South Jane W8 9AA", "Flat 92 Donna row Huntland M86 7WB", "Flat 77b Armstrong common Gerardside TN3X 6SN", "Flat 14b Jonathan ferry New Albert L65 4ZR", "Flat 79N Palmer road South Edward LL6 8UF", "0 Clive trail Wrighttown G6H 3JB", "68 Carole point Kyleport N6F 3TJ", "Studio 8 Mohamed view East Frances B68 4PD", "51 Phillips mountain Brooksfort M10 3SQ", "Flat 62e Conor harbors Parkesside N3 2US", "550 Burton station Lake Markstad DN3 8PS", "36 Ali club Abbottstad M0 2DY", "6 Williams river West Nicoleport HG2 2TB", "Studio 09C Louis avenue Rileyburgh DN1V 4LE", "Studio 19 Victoria branch Port Leonardside L2S 0LA", "768 Hughes branch Rebeccamouth B1 0UL", "0 Webb rest Jonesmouth S7S 4SF", "Flat 72 Marion via Maureenchester M9W 4TG", "9 Davis brooks Morganville PA8 6TF", "Studio 15 Carl pass Leighborough AL18 7WE", "Studio 03 Garry prairie Jamesfort M7 2DG", "98 Adams via Woodwardside PR09 7ZS", "150 Webster estates New Chloe DE38 2HE", "Flat 85X Smith lights Richardsonside B0 0LN", "8 Dawn tunnel Kieranshire W9 7HB", "Flat 84 Warren brook Colesport SW67 8SF", "Flat 50 Tracey ramp Ashleighmouth AL3 4UJ", "Studio 9 Burton inlet New Jason S0 8PF", "Studio 86U Webb branch Port Phillipberg G7 8NW", "35 Dennis locks North Jodieberg N5B 8BW", "031 Terence key New Abigail SE29 3RU"};
    protected static final String[] JOBS = {"Librarian, academic", "Water quality scientist", "Geophysical data processor", "Research scientist (medical)", "Programmer, multimedia", "Furniture designer", "Dramatherapist", "Scientist, research (medical)", "Textile designer", "Scientist, research (maths)", "Retail manager", "Programmer, systems", "Archaeologist", "Therapeutic radiographer", "Engineer, aeronautical", "Geologist, engineering", "Associate Professor", "Editorial assistant", "Retail buyer", "Secretary, company", "Production assistant, television", "Aid worker", "Programmer, applications", "Chartered loss adjuster", "Engineer, maintenance (IT)", "Merchandiser, retail", "Surveyor, hydrographic", "Armed forces logistics/support/administrative officer", "Magazine journalist", "Hospital pharmacist", "Ophthalmologist", "Glass blower/designer", "Scientist, audiological", "Clinical scientist, histocompatibility and immunogenetics", "Biochemist, clinical", "Administrator, charities/voluntary organisations", "Prison officer", "Publishing rights manager", "English as a second language teacher", "Engineer, control and instrumentation", "Product designer", "Curator", "Art therapist", "Medical illustrator", "Operations geologist", "Social researcher", "Soil scientist", "Sports development officer", "Electrical engineer", "Teacher, primary school", "Broadcast engineer", "Commercial horticulturist", "Theatre director", "Psychologist, counselling", "Medical secretary", "Structural engineer", "Accountant, chartered certified", "Exhibition designer", "Customer service manager", "Doctor, general practice", "Accommodation manager", "Data scientist", "Retail buyer", "Herbalist", "Drilling engineer", "Toxicologist", "Sales executive", "Programme researcher, broadcasting/film/video", "Producer, television/film/video", "Pension scheme manager", "Teaching laboratory technician", "Maintenance engineer", "Patent examiner", "Research officer, government", "Quality manager", "Geochemist", "Radio broadcast assistant", "Event organiser", "Civil engineer, contracting", "Office manager", "Psychotherapist, child", "Waste management officer", "Therapist, music", "Publishing copy", "Teacher, music", "Musician", "Operational investment banker", "Museum/gallery conservator", "Chief Marketing Officer", "Local government officer", "Hotel manager", "Restaurant manager, fast food", "Housing manager/officer", "Nurse, mental health", "Psychologist, forensic", "Engineer, chemical", "Company secretary", "Passenger transport manager", "Customer service manager", "Barrister"};
    protected static final String[] CITIES = {"Lynnetown", "Deniseburgh", "Christopherfort", "Teresastad", "North Amandahaven", "Melissaview", "West Emilybury", "Ruthburgh", "New Zoe", "Georginashire", "New Bethan", "East Jake", "Port Geraldineborough", "Jamestown", "Leeview", "Gibbschester", "Graemeport", "Grahamshire", "West Abigail", "South Edwardchester", "Port Dorothyton", "New Lynne", "Lake Anntown", "East Leannebury", "Lake Gordonton", "New Nathanville", "West Leonshire", "Bernardborough", "Hayleyfort", "New Sophiestad", "Smithchester", "New Geraldine", "West Stanleyhaven", "South Hazel", "New Christian", "South Dawntown", "South Carolynland", "Garethland", "Eleanorton", "New Andrewbury", "New Robert", "Tobyberg", "West Chelseaton", "Lawrenceton", "North Jordanside", "Millstown", "New Glenn", "Russellhaven", "Northburgh", "Port Gavinton", "Martinbury", "Donaldbury", "North Marcport", "East Leeview", "New Henry", "New Brandon", "Foxbury", "West Owenview", "Buckleymouth", "Fisherport", "Davisonmouth", "Jonesborough", "Brucetown", "Lawrencebury", "Lake Reece", "Georgeberg", "North Joe", "Dobsonland", "South Patrickmouth", "South Francesca", "East Charlottemouth", "North Paulport", "Williamsburgh", "Bakershire", "Abdulland", "Port Claire", "Port Jeffrey", "Frankberg", "Burkeburgh", "East Annette", "Mitchellland", "Rachaelmouth", "North Keith", "Leanneview", "Lake Leahville", "East Marilynland", "West Harrietfurt", "Denisemouth", "South Christineview", "Braystad", "Lake Hughmouth", "East Yvonne", "Brianport", "Fosterland", "Abbotttown", "Burgesstown", "Davisfurt", "Andreaville", "Stuartport", "Brayville"};

    /**
     * The constant EMPLOYEES.
     */
    protected static final Integer[] EMPLOYEES = {1, 2, 4, 5, 7, 8, 10, 11};
    /**
     * The constant RAND.
     */
    protected static final Random RAND = new SecureRandom();
    /**
     * The constant MAX_STAFF_NO.
     */
    protected static final int MAX_STAFF_NO = 999999;
    /**
     * The constant MIN_PASS_LEN.
     */
    protected static final int MIN_PASS_LEN = 10;
    /**
     * The constant MAX_PASS_LEN.
     */
    protected static final int MAX_PASS_LEN = 20;
    /**
     * The constant ASCII_LOWER_A.
     */
    protected static final int ASCII_LOWER_A = 97;
    /**
     * The constant ASCII_LOWER_Z.
     */
    protected static final int ASCII_LOWER_Z = 122;
    /**
     * The constant ASCII_UPPER_A.
     */
    protected static final int ASCII_UPPER_A = 65;
    /**
     * The constant ASCII_UPPER_Z.
     */
    protected static final int ASCII_UPPER_Z = 90;
    /**
     * The constant MIN_STAFF_NO.
     */
    protected static final int MIN_STAFF_NO = 1;
    /**
     * The constant POSTCODE_LEN.
     */
    protected static final int POSTCODE_LEN = 6;
    /**
     * The constant PHONE_NO_LEN.
     */
    protected static final int PHONE_NO_LEN = 11;
    protected static final String[] FIRST_NAMES = {"Natasha", "Bryan", "Hannah", "Mathew", "Elliott", "Shannon", "Mark", "Patrick", "Laura", "Guy", "Glen", "Leah", "Mitchell", "Jeffrey", "Phillip", "Brandon", "James", "Vanessa", "Matthew", "Jeremy", "Amy", "Elliot", "Shirley", "Hilary", "Cheryl", "Gavin", "Katie", "Lewis", "Conor", "Graeme", "Julie", "Paul", "Valerie", "Glen", "Sophie", "Louis", "Emma", "Stephanie", "Allan", "Ian", "Harry", "Glen", "Lindsey", "Natalie", "Andrea", "Mary", "Albert", "Joanna", "Grace", "Garry", "Marilyn", "Donald", "Lorraine", "Donna", "Emily", "Lucy", "Naomi", "Oliver", "Joshua", "Charlie", "Dylan", "Leah", "Melanie", "Conor", "Ross", "Cheryl", "Jeremy", "Cheryl", "Lynda", "Brian", "Brian", "Stephen", "Alexandra", "Max", "Paul", "Christine", "Beverley", "Adam", "Derek", "Gregory", "Derek", "Suzanne", "Frances", "Marcus", "Ian", "Charles", "Russell", "Amber", "Ruth", "Harry", "Tracey", "Gerard", "Aimee", "Natasha", "Sharon", "Nicole", "Linda", "Gail", "Mandy", "Joanne", "Trevor"};
    private static final String LOREM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

    /**
     * Gets random.
     *
     * @return the random
     */
    @SuppressWarnings("SameReturnValue")
    protected static Random getRandom() {
        return RAND;
    }

    /**
     * Random credentials credentials record.
     *
     * @param staffNo the staff number
     * @return the credentials record
     *
     * @throws StringFormatException on badly formatted input
     * @throws StaffNoException when the staff number is negative or it's parsing fails due to bad format
     */
    protected final CredentialsRecord randCredentialsRecord(final int staffNo) throws StringFormatException, StaffNoException {
        return new CredentialsRecord(staffNo, randUsername(), randPassword());
    }

    /**
     * Rand credentials record credentials record.
     *
     * @return the credentials record
     */
    protected final CredentialsRecord randCredentialsRecord() {
        try {
            return new CredentialsRecord(randStaffNo(), randUsername(), randPassword());
        } catch (StringFormatException | StaffNoException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Rand reviewer staff no int.
     *
     * @return the int
     */
    protected final int randReviewerStaffNo() {
        return (Integer) randArrayElement((A[]) REVIEWERS);
    }

    /**
     * Rand array element a.
     *
     * @param array the array
     * @return the a
     */
    protected final A randArrayElement(final A[] array) {
        return array[RAND.nextInt(array.length)];
    }

    /**
     * Rand section section.
     *
     * @return the section
     */
    protected final Section randSection() {
        return (Section) randArrayElement((A[]) Section.values());
    }

    /**
     * Rand city.
     *
     * @return the section
     */
    protected final String randCity() {
        return (String) randArrayElement((A[]) CITIES);
    }

    /**
     * Rand fullname.
     *
     * @return the section
     */
    protected final String randFullName() {
        return randFirstName() + ' ' + randLastName();
    }

    /**
     * Rand job.
     *
     * @return the section
     */
    protected final String randJob() {
        return (String) randArrayElement((A[]) JOBS);
    }

    /**
     * Rand password string.
     *
     * @return the string
     */
    protected final String randPassword() {
        return IntStream.range(MIN_PASS_LEN, MAX_PASS_LEN)
                        .mapToObj(String::valueOf)
                        .collect(joining());
    }

    /**
     * Rand username string.
     *
     * @return the string
     */
    @SuppressWarnings("StringConcatenationMissingWhitespace")
    protected final String randUsername() {
        return IntStream.range(0, 3)
                        .mapToObj((int x) -> String.valueOf(((char) (ASCII_LOWER_A + RAND.nextInt(ASCII_LOWER_Z - ASCII_LOWER_A)))))
                        .collect(joining()) + IntStream.range(0, 3)
                                                       .mapToObj(x -> Integer.toString(RAND.nextInt(10)))
                                                       .collect(joining());
    }

    /**
     * Rand permission level permission level.
     *
     * @return the permission level
     */
    protected final PermissionLevel randPermissionLevel() {
        return (PermissionLevel) randArrayElement((A[]) PermissionLevel.values());
    }

    /**
     * Rand personal details builder base personal details builder.
     *
     * @return the base personal details builder
     */
    protected final BasePersonalDetailsBuilder randPersonalDetailsBuilder() {
        return randPersonalDetailsBuilder(randStaffNo());
    }

    /**
     * Rand staff no int.
     *
     * @return the int
     */
    protected final int randStaffNo() {
        return randStaffNo(MAX_STAFF_NO - MIN_STAFF_NO);
    }

    /**
     * Rand staff no int.
     *
     * @param max the max
     * @return the int
     */
    protected final int randStaffNo(final int max) {
        return MIN_STAFF_NO + RAND.nextInt(max);
    }

    /**
     * Rand personal details builder base personal details builder.
     *
     * @param staffNo the staff number
     * @return the base personal details builder
     */
    protected final BasePersonalDetailsBuilder randPersonalDetailsBuilder(final int staffNo) {
        try {
            return new ValidatingPersonalDetailsBuilder(staffNo).setFirstName(randFirstName())
                                                                .setLastName(randLastName())
                                                                .setMobileNo(randPhoneNo())
                                                                .setTelNo(randPhoneNo())
                                                                .setCity(randCity())
                                                                .setCounty(randCapWord())
                                                                .setEmergencyContactNo(randPhoneNo())
                                                                .setDateOfBirth(randDateOfBirth())
                                                                .setAddress(randAddress())
                                                                .setPostcode(randPostcode());
        } catch (final NullValueException | StaffNoException | StringFormatException | DateException e) {
            e.printStackTrace();
            exit(1);
            return null;
        }
    }

    /**
     * Rand phone no string.
     *
     * @return the string
     */
    protected final String randPhoneNo() {
        return IntStream.range(0, PHONE_NO_LEN)
                        .map(x -> RAND.nextInt(10))
                        .mapToObj(Integer::toString)
                        .collect(joining());
    }

    /**
     * Rand date of birth local date.
     *
     * @return the local date
     */
    @SuppressWarnings("ImplicitNumericConversion")
    protected final LocalDate randDateOfBirth() {
        return now().minusYears(MIN_AGE + RAND.nextInt(MAX_AGE - MIN_AGE));
    }

    /**
     * Rand address string.
     *
     * @return the string
     */
    protected final String randAddress() {
        return (String) randArrayElement((A[]) ADDRESSES);
    }

    /**
     * Rand cap word string.
     *
     * @return the string
     */
    protected final String randCapWord() {
        final StringBuilder builder = new StringBuilder(CAP_WORD_LEN + 1);
        builder.append((char) (ASCII_UPPER_A + RAND.nextInt(ASCII_UPPER_Z - ASCII_UPPER_A)));

        //noinspection MethodCallInLoopCondition
        while (builder.length() != CAP_WORD_LEN) {
            builder.append((char) (ASCII_LOWER_A + RAND.nextInt(ASCII_LOWER_Z - ASCII_LOWER_A)));
        }
        return builder.toString();
    }

    /**
     * Rand name string.
     *
     * @return the string
     */
    protected final String randFirstName() {
        return (String) randArrayElement((A[]) FIRST_NAMES);
    }

    /**
     * Rand name string.
     *
     * @return the string
     */
    protected final String randLastName() {
        return (String) randArrayElement((A[]) LAST_NAMES);
    }

    /**
     * Rand postcode string.
     *
     * @return the string
     */
    @SuppressWarnings("MethodCallInLoopCondition")
    protected final String randPostcode() {
        final StringBuilder builder = new StringBuilder(POSTCODE_LEN + 2);

        // lowercase ASCII char
        while (builder.length() != POSTCODE_LEN)
            builder.append((char) (ASCII_LOWER_A + RAND.nextInt(ASCII_LOWER_Z - ASCII_LOWER_A)));

        return builder.toString()
                      .toUpperCase();
    }

    /**
     * Rand recommendation recommendation.
     *
     * @return the recommendation
     */
    @SuppressWarnings("unchecked")
    protected final Recommendation randRecommendation() {
        return (Recommendation) randArrayElement((A[]) Recommendation.values());
    }

    /**
     * Random paragraph string.
     *
     * @return the string
     */
    @SuppressWarnings("MethodReturnAlwaysConstant")
    protected final String randomParagraph() {
        return LOREM;
    }

    /**
     * Rand review builder review builder.
     *
     * @return the review builder
     */
    protected final ReviewBuilder randReviewBuilder() {
        try {

            int empStaffNo = randStaffNo(), rev1StaffNo = randStaffNo(), rev2StaffNo = randStaffNo();

            while ((empStaffNo == rev1StaffNo) || (empStaffNo == rev2StaffNo) || (rev1StaffNo == rev2StaffNo)) {
                empStaffNo = randStaffNo();
                rev1StaffNo = randStaffNo();
                rev2StaffNo = randStaffNo();
            }

            return new ReviewBuilder(empStaffNo, randFullName(), randJob(), rev1StaffNo, randFullName(), rev2StaffNo, randFullName());

        } catch (final StaffNoException | ReviewersNotDiffException | NullValueException | StringFormatException e) {
            e.printStackTrace();
            exit(1);
            return null;
        }
    }

    /**
     * Rand completed review completed review.
     *
     * @return the completed review
     */
    protected final CompletedReview randCompletedReview() {
        try {
            final ReviewBuilder builder = requireNonNull(randReviewBuilder()).setObjectivesAndAchievements(randomParagraph())
                                                                             .setRecommendation(randRecommendation())
                                                                             .setPerformanceSummary(randomParagraph())
                                                                             .setReviewerComments(randomParagraph())
                                                                             .setGoalsAndPlannedOutcomes(randomParagraph());
            builder.signAndDate(builder.employeeStaffNo);
            builder.signAndDate(builder.reviewer1StaffNo);
            builder.signAndDate(builder.reviewer2StaffNo);
            return builder.create();
        } catch (final AlreadySignedException | NotSignedException | NullValueException | NoSuchRecordException e) {
            e.printStackTrace();
            exit(1);
            return null;
        }
    }

    /**
     * Rand allocation allocation.
     *
     * @return the allocation
     */
    protected final Allocation randAllocation() {
        try {
            return new Allocation(randStaffNo(), randStaffNo(), randStaffNo());
        } catch (final StaffNoException e) {
            e.printStackTrace();
            exit(1);
            return null;
        } catch (final ReviewersNotDiffException ignored) {
            //noinspection TailRecursion
            return randAllocation();
        }
    }

    protected final LocalDate randEmploymentDate() {
        return now().minusYears(1 + RAND.nextInt(10));
    }
}
