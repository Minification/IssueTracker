package issuetracker.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateIssueRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

}
