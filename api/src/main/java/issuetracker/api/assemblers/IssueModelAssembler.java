package issuetracker.api.assemblers;

import issuetracker.api.controllers.issue.IssueController;
import issuetracker.domain.model.Issue;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class IssueModelAssembler implements RepresentationModelAssembler<Issue, EntityModel<Issue>> {

    @Override
    public EntityModel<Issue> toModel(Issue entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(IssueController.class).one(entity.getId())).withSelfRel()
                );
    }

}
