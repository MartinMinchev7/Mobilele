package bg.sofuni.mobilele.service;

import bg.sofuni.mobilele.model.dto.AddOfferDTO;
import bg.sofuni.mobilele.model.dto.OfferDetailsDTO;
import bg.sofuni.mobilele.model.dto.OfferSummaryDTO;

import java.util.List;

public interface OfferService {
    long createOffer(AddOfferDTO addOfferDTO);

    void deleteOffer(long offerId);

    OfferDetailsDTO getOfferDetails(Long id);

    List<OfferSummaryDTO> getAllOffersSummary();
}
