package issuetracker.specification;

public interface Specification<T> {

    boolean isSatisfiedBy(T entity);

}
