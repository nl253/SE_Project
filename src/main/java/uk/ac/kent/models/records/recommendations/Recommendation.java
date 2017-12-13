package uk.ac.kent.models.records.recommendations;

import java.text.MessageFormat;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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

@SuppressWarnings("AlibabaAbstractClassShouldStartWithAbstractNaming")
@Entity
@Table(name = "recommendations")
@Access(AccessType.FIELD)
public abstract class Recommendation {

    @Id
    private final int id;

    @Transient
    private static int nextId;

    @SuppressWarnings("AssignmentToStaticFieldFromInstanceMethod")
    protected Recommendation() {
        id = nextId;
        nextId++;
    }

    @SuppressWarnings("DesignForExtension")
    @Override
    public String toString() {
        return MessageFormat.format("BaseRecommendation<id={0}>", id);
    }
}
