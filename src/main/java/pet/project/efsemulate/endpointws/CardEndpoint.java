package pet.project.efsemulate.endpointws;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pet.project.efsemulate.model.dto.*;
import pet.project.efsemulate.service.AuthService;
import pet.project.efsemulate.service.FuelCardServiceImpl;
import service.cards.tch.com.types.WSCard;
import service.cards.tch.com.types.arrays.com.tch.cards.model.WSCardSummaryArray;

@Endpoint
@Slf4j
@RequiredArgsConstructor
public class CardEndpoint {

    private static final String NS = "http://com.tch.cards.service";

    private final FuelCardServiceImpl fuelCardService;
    private final AuthService authService;

    @PayloadRoot(namespace = NS, localPart = "getCard")
    @ResponsePayload
    public GetCardResponse getCard(@RequestPayload GetCardRequest request) {
        authService.validateClientId(request.getClientId());
        log.info("CardEndpoint: Rcv request to getCard with number {}", request.getCardNumber());
        WSCard card = fuelCardService.getCard(request.getClientId(), request.getCardNumber());
        GetCardResponse resp = new GetCardResponse();
        resp.setResult(card);
        return resp;
    }

    @PayloadRoot(namespace = NS, localPart = "setCard")
    @ResponsePayload
    public SetCardResponse setCard(@RequestPayload SetCardRequest request) {
        log.info("CardEndpoint: Rcv request to setCard with number {}",
            request.getCard() != null ? request.getCard().getCardNumber() : "null");
        fuelCardService.setCard(request.getClientId(), request.getCard());
        return new SetCardResponse();
    }

    @PayloadRoot(namespace = NS, localPart = "getCardSummaries")
    @ResponsePayload
    public GetCardSummariesResponse getCardSummaries(@RequestPayload GetCardSummariesRequest request) {
        log.info("CardEndpoint: Rcv request to getCardSummaries");
        WSCardSummaryArray arr = fuelCardService.getCardSummaries(request.getClientId(), request.getRequest());
        GetCardSummariesResponse resp = new GetCardSummariesResponse();
        resp.setResult(arr);
        log.info("CardEndpoint: Send response to getCardSummaries with size {}", arr != null && arr.getValue() != null ? arr.getValue().size() : "null");
        return resp;
    }
}
