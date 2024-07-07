package bg.softuni.mobileleoffers.model.dto;

import bg.softuni.mobileleoffers.model.enums.EngineTypeEnum;

public record OfferDTO(
        Long id,
        String description,
        Integer mileage,
        Integer price,
        EngineTypeEnum engineType
) {
}
