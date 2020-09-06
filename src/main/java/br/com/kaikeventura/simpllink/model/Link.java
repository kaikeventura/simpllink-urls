package br.com.kaikeventura.simpllink.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Lob
    private String link;
    private String shortedLink;
    private Boolean isActivated;
    private LocalDate createDate;
    private Integer numberOfDaysActive;

    public Link(
            String link,
            String shortedLink,
            Boolean isActivated,
            LocalDate createDate,
            Integer numberOfDaysActive
    ) {
        this.link = link;
        this.shortedLink = shortedLink;
        this.isActivated = isActivated;
        this.createDate = createDate;
        this.numberOfDaysActive = numberOfDaysActive;
    }
}
