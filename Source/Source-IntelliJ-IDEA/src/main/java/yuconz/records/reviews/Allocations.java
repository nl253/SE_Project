package yuconz.records.reviews;

import yuconz.records.CreatableRecords;
import yuconz.records.ReadableRecords;
import yuconz.records.RemovableRecords;

/**
 * The interface Allocations.
 */
@FunctionalInterface
public interface Allocations extends CreatableRecords<Allocation>, ReadableRecords<Allocation>, RemovableRecords<Allocation> {} 
