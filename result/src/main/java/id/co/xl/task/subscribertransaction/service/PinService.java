package id.co.xl.task.subscribertransaction.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import id.co.xl.task.subscribertransaction.model.response.GetPinRs;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Service
public class PinService {

    private WebClient genericWebClient;

    public PinService(WebClient genericWebClient) {
        this.genericWebClient = genericWebClient;
    }

    @SuppressWarnings("null")
    public GetPinRs getPin(String msisdn) {
        GetPinRs getPinRs = new GetPinRs()
                .setStatus("error")
                .setCode("01")
                .setMessage("subscriber not found");
        String uri = "/subscriber/" + msisdn + "/pin";
        try {
            ResponseEntity<GetPinRs> getPinRsResponseEntity = this.genericWebClient.get()
                    .uri(uri)
                    .retrieve()
                    .toEntity(GetPinRs.class)
                    .block();

            log.info("[GET HTTP RESPONSE - SUCCESS][{}][{}][{}]", uri, getPinRsResponseEntity.getStatusCode(),
                    getPinRsResponseEntity.getBody());
            getPinRs = getPinRsResponseEntity.getBody();
        } catch (WebClientResponseException ex) {
            getPinRs.setMessage("Unable to Get PIN");
            log.warn("[GET HTTP RESPONSE - FAILED][{}][{}][{}]", uri, ex.getStatusCode(), ex.getResponseBodyAsString());
        }

        return getPinRs;
    }
}
