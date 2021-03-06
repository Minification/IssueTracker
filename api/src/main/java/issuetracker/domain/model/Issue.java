package issuetracker.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Issue implements Serializable, Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String content;

    private Long ownerId;

    private Long projectId;

}
