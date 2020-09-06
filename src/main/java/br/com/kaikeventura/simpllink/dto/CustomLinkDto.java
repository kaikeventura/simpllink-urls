package br.com.kaikeventura.simpllink.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CustomLinkDto {
    @NotBlank @NotNull
    private String longLink;
    @NotBlank @NotNull @Size(min = 10, max = 10)
    private String customShortedLink;
    @NotNull
    private Days days;
}
