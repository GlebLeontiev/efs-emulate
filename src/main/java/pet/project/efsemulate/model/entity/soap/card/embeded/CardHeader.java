package pet.project.efsemulate.model.entity.soap.card.embeded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.ZonedDateTime;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CardHeader {

    @Column(name = "company_xref")
    private String companyXRef;

    @Column(name = "policy_number")
    private Integer policyNumber;

    @Column(name = "status")
    private String status;

    @Column(name = "last_used_date")
    private ZonedDateTime lastUsedDate;
}
