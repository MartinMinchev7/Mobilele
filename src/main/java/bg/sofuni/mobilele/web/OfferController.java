package bg.sofuni.mobilele.web;

import bg.sofuni.mobilele.model.dto.AddOfferDTO;
import bg.sofuni.mobilele.model.enums.EngineTypeEnum;
import bg.sofuni.mobilele.service.OfferService;
import bg.sofuni.mobilele.service.exception.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }


    @ModelAttribute("allEngineTypes")
    public EngineTypeEnum[] allEngineTypes() {
        return EngineTypeEnum.values();
    }

    @GetMapping("add")
    public String newOffer(Model model) {

        if (!model.containsAttribute("addOfferDTO")) {
            model.addAttribute("addOfferDTO", AddOfferDTO.empty());
        }

        return "offer-add";
    }

    @PostMapping("add")
    public String createOffer(@Valid AddOfferDTO addOfferDTO,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addOfferDTO", addOfferDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.addOfferDTO", bindingResult);
            return "redirect:/offers/add";
        }


        offerService.createOffer(addOfferDTO);

        return "redirect:/offers/all";
    }

    @GetMapping("/{id}")
    public String offerDetails(@PathVariable("id") Long id, Model model) {

        model.addAttribute("offerDetails", offerService.getOfferDetails(id));

        return "details";
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    public ModelAndView handleObjectNotFound(ObjectNotFoundException onfe) {
        ModelAndView modelAndView = new ModelAndView("object-not-found");
        modelAndView.addObject("offerId", onfe.getId());

        return modelAndView;
    }

    @DeleteMapping("/{id}")
    public String deleteOffer(@PathVariable("id") Long id) {
        offerService.deleteOffer(id);

        return "redirect:/offers/all";
    }
}
