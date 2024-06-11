package bg.sofuni.mobilele.model;

import bg.sofuni.mobilele.model.enums.EngineTypeEnum;

public record OfferDetailsDTO(Long id,
                              String description,
                              Integer mileage,
                              EngineTypeEnum engineType) {
}
