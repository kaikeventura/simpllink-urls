package br.com.kaikeventura.simpllink.error;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class ErrorPages implements ErrorViewResolver {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> map) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("status", status.value());

        switch (status.value()) {
            case 400:
                modelAndView.addObject("error", "Something was filled in incorrectly.");
                modelAndView.addObject("message",  "Check the fields and try again.");
                break;
            case 403:
                modelAndView.addObject("error", "The link has expired.");
                modelAndView.addObject("message",  "Please continue using Simpllink and generate another shortened link.");
                break;
            case 404:
                modelAndView.addObject("error", "Shortened link not found.");
                modelAndView.addObject("message", "The shortened link '" + map.get("path") + "' was not found.");
                break;
            case 500:
                modelAndView.addObject("error", "An internal server error has occurred.");
                modelAndView.addObject("message", "An unexpected error occurred, please try later.");
                break;
            default:
                modelAndView.addObject("error", map.get("error"));
                modelAndView.addObject("message", map.get("message"));
                break;
        }

        return modelAndView;
    }
}
