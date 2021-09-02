package issuetracker.mappers;

import issuetracker.domain.Account;
import issuetracker.dto.CreateAccountRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class AccountEditMapper {

    public abstract Account create(final CreateAccountRequest request);

}
