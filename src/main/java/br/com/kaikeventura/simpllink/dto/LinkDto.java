package br.com.kaikeventura.simpllink.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LinkDto {
    @NotBlank @NotNull
    private String longLink;
    @NotNull
    private Days days;
}