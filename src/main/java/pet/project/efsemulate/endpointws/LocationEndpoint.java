package pet.project.efsemulate.endpointws;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pet.project.efsemulate.model.dto.SearchLocationRequest;
import pet.project.efsemulate.model.dto.SearchLocationResponse;
import pet.project.efsemulate.service.AuthService;
import pet.project.efsemulate.service.FuelCardServiceImpl;
import service.cards.tch.com.types.arrays.com.tch.cards.model.WSLocationArray;

@Endpoint
@RequiredArgsConstructor
@Slf4j
public class LocationEndpoint {

    private static final String NS = "http://com.tch.cards.service";

    private final FuelCardServiceImpl fuelCardService;
    private final AuthService authService;

    @PayloadRoot(namespace = NS, localPart = "searchLocation")
    @ResponsePayload
    public SearchLocationResponse searchLocation(@RequestPayload SearchLocationRequest request) {
        log.info("LocationEndpoint: Rcv request to searchLocation with id {}", request.getSearch().getLocId());
        WSLocationArray arr = fuelCardService.searchLocation(request.getClientId(), request.getSearch());
        SearchLocationResponse resp = new SearchLocationResponse();
        resp.setResult(arr);
        return resp;
    }
}
