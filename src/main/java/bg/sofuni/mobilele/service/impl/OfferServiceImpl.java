package bg.sofuni.mobilele.service.impl;

import bg.sofuni.mobilele.model.dto.AddOfferDTO;
import bg.sofuni.mobilele.model.dto.OfferDetailsDTO;
import bg.sofuni.mobilele.model.dto.OfferSummaryDTO;
import bg.sofuni.mobilele.model.entity.OfferEntity;
import bg.sofuni.mobilele.repository.OfferRepository;
import bg.sofuni.mobilele.service.ExRateService;
import bg.sofuni.mobilele.service.OfferService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final ExRateService exRateService;

    public OfferServiceImpl(OfferRepository offerRepository, ExRateService exRateService) {
        this.offerRepository = offerRepository;
        this.exRateService = exRateService;
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
                .map(this::offerDetailsDTO)
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

    private OfferDetailsDTO offerDetailsDTO(OfferEntity offerEntity) {
        return new OfferDetailsDTO(offerEntity.getId(),
                offerEntity.getDescription(),
                offerEntity.getMileage(),
                offerEntity.getPrice(),
                offerEntity.getEngine(),
                exRateService.allSupportedCurrencies());
    }

    private static OfferEntity map(AddOfferDTO addOfferDTO) {

        OfferEntity offerEntity = new OfferEntity();
        offerEntity.setDescription(addOfferDTO.description());
        offerEntity.setEngine(addOfferDTO.engineType());
        offerEntity.setMileage(addOfferDTO.mileage());
        offerEntity.setPrice(addOfferDTO.price());

        return offerEntity;
    }
}
