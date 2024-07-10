package bg.sofuni.mobilele.model.dto;

import bg.sofuni.mobilele.model.enums.EngineTypeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record AddOfferDTO(
        @NotNull(message = "{add.offer.description.not.length}")
        @Size(message = "{add.offer.description.not.length}", min = 5, max = 500)
        String description,
        @NotNull
        @PositiveOrZero
        Integer mileage,
        @NotNull
        @PositiveOrZero
        Integer price,
        @NotNull
        EngineTypeEnum engineType
) {

    public static AddOfferDTO empty() {
        return new AddOfferDTO(null, null, null,null);
    }
}
