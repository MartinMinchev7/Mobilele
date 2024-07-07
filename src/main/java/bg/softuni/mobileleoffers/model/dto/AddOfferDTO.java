package bg.softuni.mobileleoffers.model.dto;

import bg.softuni.mobileleoffers.model.enums.EngineTypeEnum;

public record AddOfferDTO(
        String description,
        Integer mileage,
        Integer price,
        EngineTypeEnum engineType
) {
}
