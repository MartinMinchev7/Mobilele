package bg.sofuni.mobilele.service;

import bg.sofuni.mobilele.model.AddOfferDTO;
import bg.sofuni.mobilele.model.OfferDetailsDTO;
import bg.sofuni.mobilele.model.OfferSummaryDTO;

import java.util.List;

public interface OfferService {
    long createOffer(AddOfferDTO addOfferDTO);

    void deleteOffer(long offerId);

    OfferDetailsDTO getOfferDetails(Long id);

    List<OfferSummaryDTO> getAllOffersSummary();
}
