package br.com.kaikeventura.simpllink.resource;

import br.com.kaikeventura.simpllink.dto.CustomLinkDto;
import br.com.kaikeventura.simpllink.dto.Days;
import br.com.kaikeventura.simpllink.dto.LinkDto;
import br.com.kaikeventura.simpllink.dto.ValidLink;
import br.com.kaikeventura.simpllink.error.exception.LinkNotFoundException;
import br.com.kaikeventura.simpllink.model.Link;
import br.com.kaikeventura.simpllink.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.internal.constraintvalidators.hv.URLValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.net.MalformedURLException;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class LinkResource {

    private static final String SUCCESS_MESSAGE = "Successfully shortened link!";
    private static final String CUSTOM_SHORTED_SUCCESS_MESSAGE = "Successfully custom shortened link!";
    private static final String UNSUCCESSFUL_MESSAGE = "The shortened link entered cannot be used, please try with another link.";
    private static final String UNSUCCESSFUL_TRY_AGAIN_MESSAGE = "Try again with a different shortened link.";
    private static final String VALID_LINK_MESSAGE = "The link below is valid.";
    private static final String INVALID_LINK_MESSAGE = "The link below is invalid.";

    private final LinkService linkService;

    @GetMapping
    public ModelAndView home(LinkDto linkDto, Link link, CustomLinkDto customLinkDto, ValidLink validLink) {
        return new ModelAndView("index");
    }

    @PostMapping("random")
    public String createRandomShortenedLink(
            @Valid LinkDto linkDto,
            ModelMap modelMap,
            Model model
    ) {
        modelMap.addAttribute("link", linkService.createShortedLink(linkDto));
        model.addAttribute("success", SUCCESS_MESSAGE);
        model.addAttribute("customLinkDto", new CustomLinkDto());
        model.addAttribute("validLink", new ValidLink());

        return "index";
    }

    @PostMapping("custom")
    public String createCustomShortenedLink(
            @Valid CustomLinkDto customLinkDto,
            ModelMap modelMap,
            Model model
            ) {
        Link link = linkService.createCustomShortedLink(customLinkDto);

        if(link != null) {
            modelMap.addAttribute("link", link);
            model.addAttribute("success", CUSTOM_SHORTED_SUCCESS_MESSAGE);
        }
        else {
            model.addAttribute("unsuccessful", UNSUCCESSFUL_MESSAGE);
            model.addAttribute("try_again_message", UNSUCCESSFUL_TRY_AGAIN_MESSAGE);
        }
        model.addAttribute("linkDto", new LinkDto());
        model.addAttribute("validLink", new ValidLink());
        return "index";
    }

    @GetMapping("{linkShorted}")
    public String redirectLink(@PathVariable("linkShorted") String linkShorted) throws LinkNotFoundException {
        return "redirect:" + linkService.getOriginalLink(linkShorted);
    }

    @ModelAttribute("amountDays")
    public Days[] amountDays() {
        return Days.values();
    }

    @PostMapping("validator")
    public String linkValidator(@Valid ValidLink validLink, Model model, ModelMap modelMap) {
        boolean isValid = linkService.checkIfLinkIsValid(validLink.getValidLink());
        if(isValid) {
            model.addAttribute("valid_link", VALID_LINK_MESSAGE);
        }
        else {
            model.addAttribute("invalid_link", INVALID_LINK_MESSAGE);
        }
        modelMap.addAttribute("validLink", validLink);
        model.addAttribute("customLinkDto", new CustomLinkDto());
        model.addAttribute("linkDto", new LinkDto());
        return "index";
    }

}