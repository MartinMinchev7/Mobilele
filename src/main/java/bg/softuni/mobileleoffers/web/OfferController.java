package bg.softuni.mobileleoffers.web;

import bg.softuni.mobileleoffers.model.dto.AddOfferDTO;
import bg.softuni.mobileleoffers.model.dto.OfferDTO;
import bg.softuni.mobileleoffers.service.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class OfferController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OfferController.class);
    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity
                .ok(offerService.getOfferById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OfferDTO> deleteById(@PathVariable("id") Long id) {
        offerService.deleteOffer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<OfferDTO>> getAllOffers() {
        return ResponseEntity.ok(
                offerService.getAllOffers()
        );
    }

    @PostMapping
    public ResponseEntity<OfferDTO> createOffer(@RequestBody AddOfferDTO addOfferDTO) {
        LOGGER.info("Going to create an offer {}", addOfferDTO);

        OfferDTO offerDTO = offerService.createOffer(addOfferDTO);
        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(offerDTO.id())
                        .toUri()
        ).body(offerDTO);
    }
}
