package issuetracker.dto;

import lombok.Data;

@Data
public class IssueView {

    private String title;

    private String content;

    private String ownerName;

}
