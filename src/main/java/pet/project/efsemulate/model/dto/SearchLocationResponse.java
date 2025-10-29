package pet.project.efsemulate.model.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import service.cards.tch.com.types.arrays.com.tch.cards.model.WSLocationArray;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "searchLocationResponse", namespace = "http://com.tch.cards.service")
public class SearchLocationResponse {
    @XmlElement
    private WSLocationArray result;

    public WSLocationArray getResult() { return result; }
    public void setResult(WSLocationArray result) { this.result = result; }
}
