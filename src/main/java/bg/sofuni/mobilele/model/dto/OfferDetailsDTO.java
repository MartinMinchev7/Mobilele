package bg.sofuni.mobilele.model.dto;

import bg.sofuni.mobilele.model.enums.EngineTypeEnum;

import java.util.List;

public record OfferDetailsDTO(Long id,
                              String description,
                              Integer mileage,
                              Integer price,
                              EngineTypeEnum engineType,
                              List<String> allCurrencies) {
}
