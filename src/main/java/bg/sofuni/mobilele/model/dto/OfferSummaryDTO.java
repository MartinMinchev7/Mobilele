package bg.sofuni.mobilele.model.dto;

import bg.sofuni.mobilele.model.enums.EngineTypeEnum;

public record OfferSummaryDTO(Long id,
                              String description,
                              Integer mileage,
                              EngineTypeEnum engineType) {
}
