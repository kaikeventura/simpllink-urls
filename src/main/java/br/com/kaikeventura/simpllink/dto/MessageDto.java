package br.com.kaikeventura.simpllink.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MessageDto {

    @NotBlank(message = "The name field cannot be empty")
    @NotNull(message = "The name field cannot be empty")
    private String name;
    @NotBlank(message = "The email field cannot be empty")
    @NotNull(message = "The email field cannot be empty")
    private String email;
    @NotBlank(message = "The message field cannot be empty")
    @NotNull(message = "The message field cannot be empty")
    private String message;
}
