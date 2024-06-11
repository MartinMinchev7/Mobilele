package bg.sofuni.mobilele.service;

import bg.sofuni.mobilele.model.AddOfferDTO;
import bg.sofuni.mobilele.model.OfferDetailsDTO;

public interface OfferService {
    long createOrder(AddOfferDTO addOfferDTO);

    OfferDetailsDTO getOfferDetails(Long id);
}
