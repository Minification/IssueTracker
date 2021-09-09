package issuetracker.service.specification;

import java.util.List;

public class AndSpecification<T> implements Specification<T> {

    private final List<Specification<T>> subSpecifications;

    public AndSpecification(final List<Specification<T>> subSpecifications) {
        this.subSpecifications = subSpecifications;
    }

    @Override
    public boolean isSatisfiedBy(final T entity) {
        return subSpecifications.stream().allMatch(specification -> specification.isSatisfiedBy(entity));
    }

}
