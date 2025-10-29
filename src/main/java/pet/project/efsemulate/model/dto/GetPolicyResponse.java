package pet.project.efsemulate.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import service.cards.tch.com.types.WSPolicy;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "getPolicyResponse", namespace = "http://com.tch.cards.service")
public class GetPolicyResponse {
    @XmlElement
    private WSPolicy result;

    public WSPolicy getResult() { return result; }
    public void setResult(WSPolicy result) { this.result = result; }
}
