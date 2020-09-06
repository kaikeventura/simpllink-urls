package br.com.kaikeventura.simpllink.resource;

import br.com.kaikeventura.simpllink.dto.MessageDto;
import br.com.kaikeventura.simpllink.model.Message;
import br.com.kaikeventura.simpllink.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/contact")
@RequiredArgsConstructor
public class MessageResource {

    private static final String SUCCESS_MESSAGE = "Your message was successfully sent, thank you!";
    private final MessageService messageService;

    @GetMapping
    public String homeContact(MessageDto messageDto, Message message) {
        return "contact";
    }

    @PostMapping
    public String saveMessage(@Valid MessageDto messageDto, RedirectAttributes attr) {
        attr.addFlashAttribute("success", SUCCESS_MESSAGE);
        messageService.save(messageDto);
        return "redirect:/contact";
    }
}
