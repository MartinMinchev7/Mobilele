package bg.sofuni.mobilele.service.impl;

import bg.sofuni.mobilele.model.dto.AddOfferDTO;
import bg.sofuni.mobilele.model.dto.OfferDetailsDTO;
import bg.sofuni.mobilele.model.dto.OfferSummaryDTO;
import bg.sofuni.mobilele.model.entity.OfferEntity;
import bg.sofuni.mobilele.repository.OfferRepository;
import bg.sofuni.mobilele.service.ExRateService;
import bg.sofuni.mobilele.service.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {
    private final Logger LOGGER = LoggerFactory.getLogger(OfferServiceImpl.class);
    private final RestClient offerRestClient;
    private final OfferRepository offerRepository;
    private final ExRateService exRateService;

    public OfferServiceImpl(
            @Qualifier("offersRestClient") RestClient offerRestClient,
            OfferRepository offerRepository,
                            ExRateService exRateService) {

        this.offerRestClient = offerRestClient;
        this.offerRepository = offerRepository;
        this.exRateService = exRateService;
    }

    @Override
    public void createOffer(AddOfferDTO addOfferDTO) {
        LOGGER.debug("Creating new offer...");

        offerRestClient
                .post()
                .uri("http://localhost:8081/offers")
                .body(addOfferDTO)
                .retrieve();
    }

    @Override
    public void deleteOffer(long offerId) {
        offerRepository.deleteById(offerId);
    }

    @Override
    public OfferDetailsDTO getOfferDetails(Long id) {
        return offerRestClient
                .get()
                .uri("http://localhost:8081/offers/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(OfferDetailsDTO.class);
    }

    @Override
    public List<OfferSummaryDTO> getAllOffersSummary() {
        LOGGER.debug("Get all offers...");

        return offerRestClient
                .get()
                .uri("http://localhost:8081/offers")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

}
