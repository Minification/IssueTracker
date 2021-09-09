package issuetracker.domain.dto;

import lombok.Data;

@Data
public class IssueView {

    private String title;

    private String content;

    private String ownerName;

}
