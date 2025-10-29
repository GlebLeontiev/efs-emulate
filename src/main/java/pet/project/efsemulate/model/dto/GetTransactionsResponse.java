package pet.project.efsemulate.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import service.cards.tch.com.types.arrays.com.tch.cards.model.WSTransactionArray;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "getTransactionsResponse", namespace = "http://com.tch.cards.service")
public class GetTransactionsResponse {
    @XmlElement
    private WSTransactionArray result;

    public WSTransactionArray getResult() { return result; }
    public void setResult(WSTransactionArray result) { this.result = result; }
}
