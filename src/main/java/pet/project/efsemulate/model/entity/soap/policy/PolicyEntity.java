package pet.project.efsemulate.model.entity.soap.policy;

import jakarta.persistence.*;
import lombok.*;
import pet.project.efsemulate.model.entity.soap.card.CardInfoEntity;
import pet.project.efsemulate.model.entity.soap.policy.embeded.PolicyDescription;
import pet.project.efsemulate.model.entity.soap.policy.embeded.PolicyHeader;
import pet.project.efsemulate.model.entity.soap.policy.embeded.PolicyLimit;
import pet.project.efsemulate.model.entity.soap.policy.embeded.PolicyTimeRestriction;

import java.util.List;

@Entity
@Table(name = "policies")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PolicyEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "policy_number")
    private Integer policyNumber;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "contractId",  column = @Column(name = "header_contract_id")),
            @AttributeOverride(name = "description", column = @Column(name = "header_description")),
            @AttributeOverride(name = "handEnter",   column = @Column(name = "header_hand_enter"))
    })
    private PolicyHeader header;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "contractId",  column = @Column(name = "desc_contract_id")),
            @AttributeOverride(name = "description", column = @Column(name = "desc_description"))
    })
    private PolicyDescription description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "hours",       column = @Column(name = "limit_hours")),
            @AttributeOverride(name = "limit",       column = @Column(name = "limit_amount")),
            @AttributeOverride(name = "limitId",     column = @Column(name = "limit_id")),
            @AttributeOverride(name = "minHours",    column = @Column(name = "limit_min_hours")),
            @AttributeOverride(name = "autoRollMap", column = @Column(name = "limit_auto_roll_map")),
            @AttributeOverride(name = "autoRollMax", column = @Column(name = "limit_auto_roll_max"))
    })
    private PolicyLimit limit;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "day",       column = @Column(name = "trs_day_of_week")),
            @AttributeOverride(name = "beginTime", column = @Column(name = "trs_begin_time")),
            @AttributeOverride(name = "endTime",   column = @Column(name = "trs_end_time"))
    })
    private PolicyTimeRestriction timeRestriction;

    // TODO analyze this relation is needed
    @OneToMany(mappedBy = "policyEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardInfoEntity> info;
}
