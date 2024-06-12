package bg.sofuni.mobilele.service.impl;

import bg.sofuni.mobilele.model.AddOfferDTO;
import bg.sofuni.mobilele.model.OfferDetailsDTO;
import bg.sofuni.mobilele.model.OfferSummaryDTO;
import bg.sofuni.mobilele.model.entity.OfferEntity;
import bg.sofuni.mobilele.repository.OfferRepository;
import bg.sofuni.mobilele.service.OfferService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;

    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public long createOffer(AddOfferDTO addOfferDTO) {
        return offerRepository.save(map(addOfferDTO)).getId();
    }

    @Override
    public void deleteOffer(long offerId) {
        offerRepository.deleteById(offerId);
    }

    @Override
    public OfferDetailsDTO getOfferDetails(Long id) {

        return this.offerRepository
                .findById(id)
                .map(OfferServiceImpl::offerDetailsDTO)
                .orElseThrow();
    }

    @Override
    public List<OfferSummaryDTO> getAllOffersSummary() {
        return offerRepository
                .findAll()
                .stream()
                .map(OfferServiceImpl::offerSummaryDTO)
                .toList();
    }

    private static OfferSummaryDTO offerSummaryDTO(OfferEntity offerEntity) {
        return new OfferSummaryDTO(offerEntity.getId(),
                offerEntity.getDescription(),
                offerEntity.getMileage(),
                offerEntity.getEngine());
    }

    private static OfferDetailsDTO offerDetailsDTO(OfferEntity offerEntity) {
        return new OfferDetailsDTO(offerEntity.getId(),
                offerEntity.getDescription(),
                offerEntity.getMileage(),
                offerEntity.getEngine());
    }

    private static OfferEntity map(AddOfferDTO addOfferDTO) {

        OfferEntity offerEntity = new OfferEntity();
        offerEntity.setDescription(addOfferDTO.description());
        offerEntity.setEngine(addOfferDTO.engineType());
        offerEntity.setMileage(addOfferDTO.mileage());

        return offerEntity;
    }
}
