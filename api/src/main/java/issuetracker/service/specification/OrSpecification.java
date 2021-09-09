package issuetracker.service.specification;

import java.util.List;

public class OrSpecification<T> implements Specification<T> {

    private final List<Specification<T>> subSpecifications;

    public OrSpecification(final List<Specification<T>> subSpecifications) {
        this.subSpecifications = subSpecifications;
    }

    @Override
    public boolean isSatisfiedBy(final T entity) {
        return subSpecifications.stream().anyMatch(specification -> specification.isSatisfiedBy(entity));
    }

}
