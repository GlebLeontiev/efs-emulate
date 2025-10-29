package pet.project.efsemulate.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pet.project.efsemulate.model.entity.soap.LocationEntity;
import pet.project.efsemulate.model.entity.soap.transaction.TransactionEntity;
import pet.project.efsemulate.model.entity.soap.card.CardEntity;
import pet.project.efsemulate.model.entity.soap.card.CardInfoEntity;
import pet.project.efsemulate.model.entity.soap.card.embeded.CardHeader;
import pet.project.efsemulate.model.entity.soap.card.embeded.CardLimit;
import pet.project.efsemulate.model.entity.soap.card.embeded.CardTimeRestriction;
import pet.project.efsemulate.model.entity.soap.policy.PolicyDescriptionEntity;
import pet.project.efsemulate.model.entity.soap.policy.PolicyEntity;
import pet.project.efsemulate.model.entity.soap.policy.embeded.PolicyHeader;
import pet.project.efsemulate.model.entity.soap.policy.embeded.PolicyLimit;
import pet.project.efsemulate.model.entity.soap.policy.embeded.PolicyTimeRestriction;
import pet.project.efsemulate.model.entity.soap.transaction.TransactionLineItemEntity;
import service.cards.tch.com.types.*;
import service.cards.tch.com.types.arrays.com.tch.cards.model.WSCardSummaryArray;
import service.cards.tch.com.types.arrays.com.tch.cards.model.WSLocationArray;
import service.cards.tch.com.types.arrays.com.tch.cards.model.WSPolicyDescriptionArray;
import service.cards.tch.com.types.arrays.com.tch.cards.model.WSTransactionArray;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WSMapper {

    // ----------------- helpers -----------------

    private static XMLGregorianCalendar toXmlCal(ZonedDateTime odt) {
        if (odt == null) return null;
        GregorianCalendar cal = GregorianCalendar.from(odt);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static ZonedDateTime toOffset(XMLGregorianCalendar x) {
        if (x == null) return null;
        return x.toGregorianCalendar().toZonedDateTime();
    }

    private static String joinCsv(List<String> list) {
        if (list == null || list.isEmpty()) return null;
        String joined = list.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(","));
        return joined.isEmpty() ? null : joined;
    }

    private static List<String> splitCsv(String csv) {
        if (csv == null || csv.isBlank()) return List.of();
        return Arrays.stream(csv.split(","))
                .map(s -> s == null ? null : s.trim())
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.toList());
    }

    // ----------------- Card -----------------

    public WSCard toWsCard(CardEntity e) {
        if (e == null) return null;

        WSCard ws = new WSCard();
        ws.setCardNumber(e.getCardNumber());

        // Header
        if (e.getHeader() != null) {
            WSCardHeader h = new WSCardHeader();
            h.setCompanyXRef(e.getHeader().getCompanyXRef());
            if (e.getHeader().getPolicyNumber() != null) {
                h.setPolicyNumber(e.getHeader().getPolicyNumber());
            }
            h.setStatus(e.getHeader().getStatus());
            h.setLastUsedDate(toXmlCal(e.getHeader().getLastUsedDate()));
            ws.setHeader(h);
        }

        // Infos (List)
        if (e.getInfo() != null && !e.getInfo().isEmpty()) {
            for (CardInfoEntity ci : e.getInfo()) {
                WSCardInfo wi = new WSCardInfo();
                wi.setInfoId(String.valueOf(ci.getInfoId()));
                wi.setLengthCheck(Boolean.TRUE.equals(ci.getLengthCheck()));
                wi.setMatchValue(ci.getMatchValue());
                if (ci.getMaximum() != null) wi.setMaximum(ci.getMaximum());
                if (ci.getMinimum() != null) wi.setMinimum(ci.getMinimum());
                wi.setReportValue(ci.getReportValue());
                wi.setNumericMatchValue(ci.getNumericMatchValue());
                wi.setValidationType(ci.getValidationType());
                if (ci.getValue() != null) wi.setValue(ci.getValue());
                ws.getInfos().add(wi);
            }
        }

        // Limits (single -> first in list)
        if (e.getLimit() != null) {
            WSCardLimit wl = new WSCardLimit();
            if (e.getLimit().getHours() != null) wl.setHours(e.getLimit().getHours());
            if (e.getLimit().getLimit() != null) wl.setLimit(e.getLimit().getLimit());
            wl.setLimitId(e.getLimit().getLimitId());
            if (e.getLimit().getMinHours() != null) wl.setMinHours(e.getLimit().getMinHours());
            ws.getLimits().add(wl);
        }

        // TimeRestrictions (single -> first in list)
        if (e.getTimeRestriction() != null) {
            WSCardTimeRestriction wt = new WSCardTimeRestriction();
            wt.setDay(e.getTimeRestriction().getDay() != null ? e.getTimeRestriction().getDay() : 0);
            wt.setBeginTime(toXmlCal(e.getTimeRestriction().getBeginTime()));
            wt.setEndTime(toXmlCal(e.getTimeRestriction().getEndTime()));
            ws.getTimeRestrictions().add(wt);
        }

        return ws;
    }

    public CardEntity toCardEntity(WSCard ws) {
        if (ws == null) return null;

        CardEntity card = CardEntity.builder()
                .cardNumber(ws.getCardNumber())
                .build();

        // Header
        if (ws.getHeader() != null) {
            CardHeader h = CardHeader.builder()
                    .companyXRef(ws.getHeader().getCompanyXRef())
                    .policyNumber(ws.getHeader().getPolicyNumber())
                    .status(ws.getHeader().getStatus())
                    .lastUsedDate(toOffset(ws.getHeader().getLastUsedDate()))
                    .build();
            card.setHeader(h);
        }

        // Infos (List)
        if (ws.getInfos() != null && !ws.getInfos().isEmpty()) {
            List<CardInfoEntity> list = new ArrayList<>();
            for (WSCardInfo wi : ws.getInfos()) {
                CardInfoEntity ci =
                        CardInfoEntity.builder()
                                .infoId(wi.getInfoId())
                                .lengthCheck(wi.isLengthCheck())
                                .matchValue(wi.getMatchValue())
                                .maximum(wi.getMaximum())
                                .minimum(wi.getMinimum())
                                .reportValue(wi.getReportValue())
                                .numericMatchValue(wi.getNumericMatchValue())
                                .validationType(wi.getValidationType())
                                .value(wi.getValue())
                                .build();
                // Поддерживаем обратную связь JPA
                ci.setCardEntity(card);
                list.add(ci);
            }
            card.setInfo(list);
        }

        // Limits (first in list -> single)
        if (ws.getLimits() != null && !ws.getLimits().isEmpty()) {
            WSCardLimit wl = ws.getLimits().get(0);
            CardLimit cl = CardLimit.builder()
                    .hours(wl.getHours())
                    .limit(wl.getLimit())
                    .limitId(wl.getLimitId())
                    .minHours(wl.getMinHours())
                    .build();
            card.setLimit(cl);
        }

        // TimeRestrictions (first in list -> single)
        if (ws.getTimeRestrictions() != null && !ws.getTimeRestrictions().isEmpty()) {
            WSCardTimeRestriction wt = ws.getTimeRestrictions().get(0);
            CardTimeRestriction tr = CardTimeRestriction.builder()
                    .day(wt.getDay())
                    .beginTime(toOffset(wt.getBeginTime()))
                    .endTime(toOffset(wt.getEndTime()))
                    .build();
            card.setTimeRestriction(tr);
        }

        return card;
    }

    // ----------------- Policy -----------------

    public WSPolicy toWsPolicy(PolicyEntity e) {
        if (e == null) return null;
        WSPolicy ws = new WSPolicy();

        ws.setPolicyNumber(e.getPolicyNumber());

        if (e.getHeader() != null) {
            WSPolicyHeader h = new WSPolicyHeader();
            if (e.getHeader().getContractId() != null) h.setContractId(e.getHeader().getContractId());
            h.setDescription(e.getHeader().getDescription());
            h.setHandEnter(Boolean.TRUE.equals(e.getHeader().getHandEnter()));
            ws.setHeader(h);
        }

        if (e.getLimit() != null) {
            WSPolicyLimit wl = new WSPolicyLimit();
            if (e.getLimit().getHours() != null) wl.setHours(e.getLimit().getHours());
            if (e.getLimit().getLimit() != null) wl.setLimit(e.getLimit().getLimit());
            wl.setLimitId(e.getLimit().getLimitId());
            if (e.getLimit().getMinHours() != null) wl.setMinHours(e.getLimit().getMinHours());
            if (e.getLimit().getAutoRollMap() != null) wl.setAutoRollMap(e.getLimit().getAutoRollMap());
            if (e.getLimit().getAutoRollMax() != null) wl.setAutoRollMax(e.getLimit().getAutoRollMax());
            ws.getLimits().add(wl);
        }

//        List<String> groups = splitCsv(e.getLocationGroupsCsv());
//        if (!groups.isEmpty()) ws.getLocationGroups().addAll(groups);
//
//        List<String> locations = splitCsv(e.getLocationsCsv());
//        if (!locations.isEmpty()) ws.getLocations().addAll(locations);

        if (e.getTimeRestriction() != null) {
            WSCardTimeRestriction wt = new WSCardTimeRestriction();
            wt.setDay(e.getTimeRestriction().getDay() != null ? e.getTimeRestriction().getDay() : 0);
            wt.setBeginTime(toXmlCal(e.getTimeRestriction().getBeginTime()));
            wt.setEndTime(toXmlCal(e.getTimeRestriction().getEndTime()));
            ws.getTimeRestrictions().add(wt);
        }

        return ws;
    }

    public PolicyEntity toPolicyEntity(WSPolicy ws) {
        if (ws == null) return null;
        PolicyEntity p = PolicyEntity.builder()
                .policyNumber(ws.getPolicyNumber())
                .build();

        if (ws.getHeader() != null) {
            PolicyHeader h = PolicyHeader.builder()
                    .contractId(ws.getHeader().getContractId())
                    .description(ws.getHeader().getDescription())
                    .handEnter(ws.getHeader().isHandEnter())
                    .build();
            p.setHeader(h);
        }

        if (ws.getLimits() != null && !ws.getLimits().isEmpty()) {
            WSPolicyLimit wl = ws.getLimits().get(0);
            PolicyLimit pl = PolicyLimit.builder()
                    .hours(wl.getHours())
                    .limit(wl.getLimit())
                    .limitId(wl.getLimitId())
                    .minHours(wl.getMinHours())
                    .autoRollMap(wl.getAutoRollMap())
                    .autoRollMax(wl.getAutoRollMax())
                    .build();
            p.setLimit(pl);
        }

//        if (ws.getLocationGroups() != null && !ws.getLocationGroups().isEmpty())
//            p.setLocationGroupsCsv(joinCsv(ws.getLocationGroups()));
//        if (ws.getLocations() != null && !ws.getLocations().isEmpty())
//            p.setLocationsCsv(joinCsv(ws.getLocations()));

        if (ws.getTimeRestrictions() != null && !ws.getTimeRestrictions().isEmpty()) {
            WSCardTimeRestriction wt = ws.getTimeRestrictions().get(0);
            PolicyTimeRestriction tr = PolicyTimeRestriction.builder()
                    .day(wt.getDay())
                    .beginTime(toOffset(wt.getBeginTime()))
                    .endTime(toOffset(wt.getEndTime()))
                    .build();
            p.setTimeRestriction(tr);
        }

        return p;
    }

    // ----------------- Location -----------------

    public WSLocation toWsLocation(LocationEntity e) {
        if (e == null) return null;
        WSLocation w = new WSLocation();
        w.setLocId(String.valueOf(e.getId()));
        w.setState(e.getState());
        w.setCity(e.getCity());
        w.setName(e.getName());
        w.setCountry(e.getCountry());
        w.setChainId(e.getChainId());
        w.setAddr1(e.getAddr1());
        w.setAddr2(e.getAddr2());
        w.setPhone(e.getPhone());
        return w;
    }

    public LocationEntity toLocationEntity(WSLocation w) {
        if (w == null) return null;
        return LocationEntity.builder()
                .id(Long.valueOf(w.getLocId()))
                .state(w.getState())
                .city(w.getCity())
                .name(w.getName())
                .country(w.getCountry())
                .chainId(w.getChainId())
                .addr1(w.getAddr1())
                .addr2(w.getAddr2())
                .phone(w.getPhone())
                .build();
    }

    public WSLocationArray toWsLocationArray(List<LocationEntity> list) {
        WSLocationArray arr = new WSLocationArray();
        if (list != null) {
            for (LocationEntity e : list) arr.getValue().add(toWsLocation(e));
        }
        return arr;
    }

    // ----------------- PolicyDescription -----------------

    public WSPolicyDescription toWsPolicyDescription(PolicyDescriptionEntity e) {
        if (e == null) return null;
        WSPolicyDescription d = new WSPolicyDescription();
        d.setContractId(e.getContractId() != null ? e.getContractId() : 0);
        d.setDescription(e.getDescription());
        d.setPolicyNumber(e.getPolicyNumber() != null ? e.getPolicyNumber() : 0);
        return d;
    }

    public WSPolicyDescriptionArray toWsPolicyDescriptionArray(List<PolicyDescriptionEntity> list) {
        WSPolicyDescriptionArray arr = new WSPolicyDescriptionArray();
        if (list != null) for (PolicyDescriptionEntity e : list) arr.getValue().add(toWsPolicyDescription(e));
        return arr;
    }


// ----------------- Transactions -----------------

    private WSTransactionLineItem toWsLineItem(TransactionLineItemEntity li) {
        if (li == null) return null;
        WSTransactionLineItem w = new WSTransactionLineItem();
        w.setAmount(li.getAmount() != null ? li.getAmount().doubleValue() : 0.0);
        w.setBillingFlag(li.getBillingFlag() != null ? li.getBillingFlag() : 0);
        w.setCategory(li.getCategory());
        w.setCmptAmount(li.getCmptAmount() != null ? li.getCmptAmount().doubleValue() : 0.0);
        w.setCmptPPU(li.getCmptPPU() != null ? li.getCmptPPU().doubleValue() : 0.0);
        w.setDiscAmount(li.getDiscAmount() != null ? li.getDiscAmount().doubleValue() : 0.0);
        w.setFuelType(li.getFuelType() != null ? li.getFuelType() : 0);
        w.setGroupCategory(li.getGroupCategory());
        w.setGroupNumber(li.getGroupNumber() != null ? li.getGroupNumber() : 0);
        w.setIssuerDeal(li.getIssuerDeal() != null ? li.getIssuerDeal().doubleValue() : 0.0);
        w.setIssuerDealPPU(li.getIssuerDealPPU() != null ? li.getIssuerDealPPU().doubleValue() : 0.0);
        w.setLineNumber(li.getLineNumber() != null ? li.getLineNumber() : 0);
        w.setNumber(li.getNumber() != null ? li.getNumber() : 0);
        w.setPpu(li.getPpu() != null ? li.getPpu().doubleValue() : 0.0);
        w.setProdCD(li.getProdCD());
        w.setQuantity(li.getQuantity() != null ? li.getQuantity().doubleValue() : 0.0);
        w.setRetailPPU(li.getRetailPPU() != null ? li.getRetailPPU().doubleValue() : 0.0);
        w.setServiceType(li.getServiceType() != null ? li.getServiceType() : 0);
        w.setUseType(li.getUseType() != null ? li.getUseType() : 0);
        return w;
    }

    private TransactionLineItemEntity toLineItemEntity(WSTransactionLineItem w, TransactionEntity owner) {
        if (w == null) return null;
        TransactionLineItemEntity li = TransactionLineItemEntity.builder()
                .amount(BigDecimal.valueOf(w.getAmount()))
                .billingFlag(w.getBillingFlag())
                .category(w.getCategory())
                .cmptAmount(BigDecimal.valueOf(w.getCmptAmount()))
                .cmptPPU(BigDecimal.valueOf(w.getCmptPPU()))
                .discAmount(BigDecimal.valueOf(w.getDiscAmount()))
                .fuelType(w.getFuelType())
                .groupCategory(w.getGroupCategory())
                .groupNumber(w.getGroupNumber())
                .issuerDeal(BigDecimal.valueOf(w.getIssuerDeal()))
                .issuerDealPPU(BigDecimal.valueOf(w.getIssuerDealPPU()))
                .lineNumber(w.getLineNumber())
                .number(w.getNumber())
                .ppu(BigDecimal.valueOf(w.getPpu()))
                .prodCD(w.getProdCD())
                .quantity(BigDecimal.valueOf(w.getQuantity()))
                .retailPPU(BigDecimal.valueOf(w.getRetailPPU()))
                .serviceType(w.getServiceType())
                .useType(w.getUseType())
                .build();
        li.setTransaction(owner);
        return li;
    }

    public WSTransaction toWsTransaction(TransactionEntity e) {
        if (e == null) return null;
        WSTransaction t = new WSTransaction();

        // Денежные итоги
        t.setTransactionId(Math.toIntExact(e.getId()));
        if (e.getNetTotal() != null) t.setNetTotal(e.getNetTotal().doubleValue());
        if (e.getFundedTotal() != null) t.setFundedTotal(e.getFundedTotal().doubleValue());
        if (e.getPrefTotal() != null) t.setPrefTotal(e.getPrefTotal().doubleValue());
        if (e.getSettleAmount() != null) t.setSettleAmount(e.getSettleAmount().doubleValue());

        // Карта и локация
        if (e.getCard() != null) t.setCardNumber(e.getCard().getCardNumber());
        if (e.getLocation() != null && e.getLocation().getId() != null) {
            t.setLocationId(Math.toIntExact(e.getLocation().getId()));
        }

        // Дата
        t.setTransactionDate(toXmlCal(e.getTransactionDate()));

        // Позиции
        if (e.getLineItems() != null && !e.getLineItems().isEmpty()) {
            for (TransactionLineItemEntity li : e.getLineItems()) {
                t.getLineItems().add(toWsLineItem(li));
            }
        }

        return t;
    }

    public TransactionEntity toTransactionEntity(WSTransaction w) {
        if (w == null) return null;

        TransactionEntity tx = TransactionEntity.builder()
                .amount(BigDecimal.valueOf(w.getNetTotal())) // при отсутствии отдельного поля amount берём netTotal
                .fundedTotal(BigDecimal.valueOf(w.getFundedTotal()))
                .netTotal(BigDecimal.valueOf(w.getNetTotal()))
                .prefTotal(BigDecimal.valueOf(w.getPrefTotal()))
                .settleAmount(BigDecimal.valueOf(w.getSettleAmount()))
                .transactionDate(toOffset(w.getTransactionDate()))
                .build();

        // Привязки по ключам (карта по номеру, локация по id)
        if (w.getCardNumber() != null) {
            tx.setCard(CardEntity.builder().cardNumber(w.getCardNumber()).build());
        }
        if (w.getLocationId() != 0) {
            tx.setLocation(LocationEntity.builder().id((long) w.getLocationId()).build());
        }

        // Позиции
        if (w.getLineItems() != null && !w.getLineItems().isEmpty()) {
            List<TransactionLineItemEntity> list = new ArrayList<>();
            for (WSTransactionLineItem wi : w.getLineItems()) {
                list.add(toLineItemEntity(wi, tx));
            }
            tx.setLineItems(list);
        }

        return tx;
    }

    public WSTransactionArray toWsTransactionArray(List<TransactionEntity> list) {
        WSTransactionArray arr = new WSTransactionArray();
        if (list != null) for (TransactionEntity e : list) arr.getValue().add(toWsTransaction(e));
        return arr;
    }

    // ----------------- Card summaries -----------------

    public WSCardSummary toWsCardSummary(CardEntity e) {
        if (e == null) return null;
        WSCardSummary s = new WSCardSummary();
        s.setCardNumber(e.getCardNumber());
        s.setPolicyNumber(e.getHeader() != null && e.getHeader().getPolicyNumber() != null ? e.getHeader().getPolicyNumber() : 0);
        s.setOverride(0);
        s.setBeingOverridden(false);
        s.setStatus(e.getHeader() != null ? e.getHeader().getStatus() : null);
        s.setPayrollStatus(null);
        return s;
    }

    public WSCardSummaryArray toWsCardSummaryArray(List<CardEntity> list) {
        WSCardSummaryArray arr = new WSCardSummaryArray();
        if (list != null) for (CardEntity e : list) arr.getValue().add(toWsCardSummary(e));
        return arr;
    }
}
