package bg.softuni.mobileleoffers.service.impl;

import bg.softuni.mobileleoffers.model.dto.AddOfferDTO;
import bg.softuni.mobileleoffers.model.dto.OfferDTO;
import bg.softuni.mobileleoffers.model.entity.OfferEntity;
import bg.softuni.mobileleoffers.repository.OfferRepository;
import bg.softuni.mobileleoffers.service.OfferService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;

    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public void createOffer(AddOfferDTO addOfferDTO) {
        offerRepository.save(map(addOfferDTO));
    }

    @Override
    public void deleteOffer(Long offerId) {
        offerRepository.deleteById(offerId);
    }

    @Override
    public OfferDTO getOfferById(Long id) {
        return offerRepository
                .findById(id)
                .map(OfferServiceImpl::map)
                .orElseThrow(() -> new IllegalArgumentException("Not found!"));
    }

    @Override
    public List<OfferDTO> getAllOffers() {
        return offerRepository
                .findAll()
                .stream()
                .map(OfferServiceImpl::map)
                .toList();
    }

    private static OfferDTO map(OfferEntity offerEntity) {
        return new OfferDTO(
                offerEntity.getId(),
                offerEntity.getDescription(),
                offerEntity.getMileage(),
                offerEntity.getPrice(),
                offerEntity.getEngine()
        );
    }

    private static OfferEntity map(AddOfferDTO addOfferDTO) {
        return new OfferEntity()
                .setDescription(addOfferDTO.description())
                .setEngine(addOfferDTO.engineType())
                .setMileage(addOfferDTO.mileage())
                .setPrice(addOfferDTO.price());
    }
}
