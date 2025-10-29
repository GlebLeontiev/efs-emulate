package pet.project.efsemulate.model.entity.soap.policy.embeded;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PolicyHeader {
    private Integer contractId;
    private String description;
    private Boolean handEnter;
}
