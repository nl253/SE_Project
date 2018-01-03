package uk.ac.kent.models.records.recommendations;

import java.text.MessageFormat;
import java.time.LocalDate;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.UpdateTimestamp;

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
public abstract class BaseRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final int id;

    @Transient
    private static int nextId;

    @SuppressWarnings("AssignmentToStaticFieldFromInstanceMethod")
    protected BaseRecommendation() {
        id = nextId;
        nextId++;
    }

    // @Column(name = "modified_date")
    @UpdateTimestamp
    private LocalDate modifiedDate;

    public final LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public final void setModifiedDate(final LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @SuppressWarnings("DesignForExtension")
    @Override
    public String toString() {
        return MessageFormat.format("BaseRecommendation<id={0}>", id);
    }
}
