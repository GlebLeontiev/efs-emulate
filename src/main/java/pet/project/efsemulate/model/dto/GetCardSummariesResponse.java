package pet.project.efsemulate.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import service.cards.tch.com.types.arrays.com.tch.cards.model.WSCardSummaryArray;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "getCardSummariesResponse", namespace = "http://com.tch.cards.service")
public class GetCardSummariesResponse {
    @XmlElement
    private WSCardSummaryArray result;

    public WSCardSummaryArray getResult() { return result; }
    public void setResult(WSCardSummaryArray result) { this.result = result; }
}
