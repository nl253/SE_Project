package uk.ac.kent.records.recommendations;

import java.text.MessageFormat;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Base recommendation is the superclass for all types
 * of recommendations kept in the system.
 * <p>
 * It equips all subclasses with basic fields and
 * getters / setters. Because it is abstract, it cannot
 * be directly instantiated.
 *
 * @author norbert
 */

@MappedSuperclass
public abstract class BaseRecommendation {

    @Id
    private final int id;

    private static int nextId;

    @SuppressWarnings("AssignmentToStaticFieldFromInstanceMethod")
    BaseRecommendation() {
        id = nextId;
        nextId++;
    }

    @Override
    public String toString() {
        return MessageFormat.format("BaseRecommendation<id={0}>", id);
    }
}
