package pet.project.efsemulate.model.entity.soap.transaction;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transaction_line_items")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionLineItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transactions_id", nullable = false)
    private TransactionEntity transaction;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "billing_flag")
    private Integer billingFlag;

    @Column(name = "category")
    private String category;

    @Column(name = "cmpt_amount")
    private Double cmptAmount;

    @Column(name = "cmpt_ppu")
    private Double cmptPPU;

    @Column(name = "disc_amount")
    private Double discAmount;

    @Column(name = "fuel_type")
    private Integer fuelType;

    @Column(name = "group_category")
    private String groupCategory;

    @Column(name = "group_number")
    private Integer groupNumber;

    @Column(name = "issuer_deal")
    private Double issuerDeal;

    @Column(name = "issuer_deal_ppu")
    private Double issuerDealPPU;

    @Column(name = "line_number")
    private Integer lineNumber;

    @Column(name = "number_val") // "number" — зарезервировано в ряде БД, лучше переименовать столбец
    private Integer number;

    @Column(name = "ppu")
    private Double ppu;

    @Column(name = "prod_cd")
    private String prodCD;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "retail_ppu")
    private Double retailPPU;

    @Column(name = "service_type")
    private Integer serviceType;

    @Column(name = "use_type")
    private Integer useType;
        //    0 — NON_FUEL_ITEM_TYPE (не топливо)
        //    2 — REEFER_PRODUCTS_ITEM_TYPE (рефрижераторные/охлаждённые продукты)
        //    любое другое значение — FUEL_ITEM_TYPE (топливо)
}
