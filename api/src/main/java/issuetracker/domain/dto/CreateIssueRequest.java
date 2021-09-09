package issuetracker.domain.dto;

import lombok.Data;

@Data
public class CreateIssueRequest {

    private String title;

    private String content;

}
