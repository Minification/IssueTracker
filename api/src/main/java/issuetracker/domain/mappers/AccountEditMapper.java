package issuetracker.domain.mappers;

import issuetracker.domain.model.Account;
import issuetracker.domain.dto.CreateAccountRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public abstract class AccountEditMapper {

    public abstract Account create(final CreateAccountRequest request);

}
