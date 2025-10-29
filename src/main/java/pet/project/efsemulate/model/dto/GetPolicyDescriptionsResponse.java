package pet.project.efsemulate.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import service.cards.tch.com.types.arrays.com.tch.cards.model.WSPolicyDescriptionArray;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "getPolicyDescriptionsResponse", namespace = "http://com.tch.cards.service")
public class GetPolicyDescriptionsResponse {
    @XmlElement
    private WSPolicyDescriptionArray result;

    public WSPolicyDescriptionArray getResult() { return result; }
    public void setResult(WSPolicyDescriptionArray result) { this.result = result; }
}
