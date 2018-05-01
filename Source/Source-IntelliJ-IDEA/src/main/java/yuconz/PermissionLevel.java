package yuconz;

/**
 * The enum Permission level.
 */
public enum PermissionLevel {

    /**
     * Manager permission level.
     */
    MANAGER, /**
     * Director permission level.
     */
    DIRECTOR, /**
     * Employee permission level.
     */
    EMPLOYEE, /**
     * Reviewer permission level.
     */
    REVIEWER, /**
     * User permission level.
     */
    USER;

    @Override
    public String toString() {
        return name();
    }
}
