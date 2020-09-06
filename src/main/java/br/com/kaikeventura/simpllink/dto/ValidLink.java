package br.com.kaikeventura.simpllink.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ValidLink {
    @NotBlank @NotNull
    private String validLink;
}
