package pet.project.efsemulate.model.entity.soap.policy;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "policy_description")
public class PolicyDescriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String description;
    private Integer contractId;
    private Integer policyNumber;
}
