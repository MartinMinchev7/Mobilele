package bg.sofuni.mobilele.web;

import bg.sofuni.mobilele.model.dto.ConversionResultDTO;
import bg.sofuni.mobilele.service.ExRateService;
import bg.sofuni.mobilele.service.exception.ApiObjectNotFoundException;
import bg.sofuni.mobilele.web.aop.WarnIfExecutionExceed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class CurrencyController {
    private final ExRateService exRateService;

    public CurrencyController(ExRateService exRateService) {
        this.exRateService = exRateService;
    }

    @WarnIfExecutionExceed(
            threshold = 800
    )
    @GetMapping("/api/convert")
    public ResponseEntity<ConversionResultDTO> convert(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("amount") BigDecimal amount
    ) {

        BigDecimal result = exRateService.convert(from, to, amount);

        return ResponseEntity.ok(new ConversionResultDTO(
                from,
                to,
                amount,
                result
        ));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ApiObjectNotFoundException.class)
    @ResponseBody
    public NotFoundErrorInfo handleApiObjectNotFoundException(ApiObjectNotFoundException ex) {
        return new NotFoundErrorInfo("NOT_FOUND", ex.getId());
    }



    public record NotFoundErrorInfo(String code, Object id) {

    }
}
