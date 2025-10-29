package pet.project.efsemulate.model.entity.soap.card;

import jakarta.persistence.*;
import lombok.*;
import pet.project.efsemulate.model.entity.soap.policy.PolicyEntity;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "card_info")
public class CardInfoEntity {

    public static final String UNIT_INFO = "UNIT";
    public static final String DRID_INFO = "DRID";
    public static final String NAME_INFO = "NAME";

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "info_id")
    private String infoId;

    @Column(name = "length_check")
    private Boolean lengthCheck;

    @Column(name = "match_value")
    private String matchValue;

    @Column(name = "maximum")
    private Integer maximum;

    @Column(name = "minimum")
    private Integer minimum;

    @Column(name = "report_value")
    private String reportValue;

    @Column(name = "numeric_match_value")
    private String numericMatchValue;

    @Column(name = "validation_type")
    private String validationType;

    @Column(name = "value_int")
    private Integer value;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "efs_fuel_cards_id", nullable = false)
    private CardEntity cardEntity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "policies_id", nullable = false)
    private PolicyEntity policyEntity;

}
