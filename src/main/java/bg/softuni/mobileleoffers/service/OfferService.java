package bg.softuni.mobileleoffers.service;

import bg.softuni.mobileleoffers.model.dto.AddOfferDTO;
import bg.softuni.mobileleoffers.model.dto.OfferDTO;

import java.util.List;

public interface OfferService {
    void createOffer(AddOfferDTO addOfferDTO);

    void deleteOffer(Long offerId);

    OfferDTO getOfferById(Long id);

    List<OfferDTO> getAllOffers();

}
