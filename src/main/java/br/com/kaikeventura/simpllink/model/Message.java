package br.com.kaikeventura.simpllink.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;
    @Email
    private String email;
    private String message;
    private LocalDate messageDate;

    public Message(String name, String email, String message, LocalDate messageDate) {
        this.name = name;
        this.email = email;
        this.message = message;
        this.messageDate = messageDate;
    }
}