package issuetracker.service.specification;

public interface Specification<T> {

    boolean isSatisfiedBy(T entity);

}
