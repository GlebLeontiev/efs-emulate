package pet.project.efsemulate.model.entity.soap.transaction;

import jakarta.persistence.*;
import lombok.*;
import pet.project.efsemulate.model.entity.soap.LocationEntity;
import pet.project.efsemulate.model.entity.soap.card.CardEntity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "funded_total")
    private BigDecimal fundedTotal;

    @Column(name = "net_total")
    private BigDecimal netTotal;

    @Column(name = "pref_total")
    private BigDecimal prefTotal;

    @Column(name = "settle_amount")
    private BigDecimal settleAmount;

    @Column(name = "transaction_date")
    private ZonedDateTime transactionDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cards_id")
    private CardEntity card;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "locations_id")
    private LocationEntity location;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<TransactionLineItemEntity> lineItems;


    // TODO
}
