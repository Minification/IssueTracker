package issuetracker.domain.model;

public enum ObjectType {

    ACCOUNT("ACCOUNT"), ISSUE("ISSUE"), PROJECT("PROJECT");

    private final String uniqueId;

    ObjectType(final String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUniqueId() {
        return uniqueId;
    }
}
