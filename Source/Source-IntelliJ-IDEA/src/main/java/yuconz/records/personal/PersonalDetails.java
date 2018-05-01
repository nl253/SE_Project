package yuconz.records.personal;

import yuconz.records.AmendableRecords;
import yuconz.records.CreatableRecords;
import yuconz.records.ReadableRecords;

/**
 * The interface Personal details.
 */
@SuppressWarnings("ClassNamePrefixedWithPackageName")
@FunctionalInterface
public interface PersonalDetails extends ReadableRecords<PersonalDetailRecord>, AmendableRecords<PersonalDetailRecord>, CreatableRecords<PersonalDetailRecord> {}
