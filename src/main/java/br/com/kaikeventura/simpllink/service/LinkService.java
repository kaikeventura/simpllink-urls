package br.com.kaikeventura.simpllink.service;

import br.com.kaikeventura.simpllink.dto.CustomLinkDto;
import br.com.kaikeventura.simpllink.dto.LinkDto;
import br.com.kaikeventura.simpllink.error.exception.LinkNotFoundException;
import br.com.kaikeventura.simpllink.error.exception.ShortenedLinkIsExpiredException;
import br.com.kaikeventura.simpllink.model.Link;
import br.com.kaikeventura.simpllink.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;

    private static final String EXPRESSION_PROTOCOLS = "https?.*|ftp.*|ssh.*|\\b(?:[0-9]{1,3}\\.).*|ssl.*|telnet.*";
    private static final String HTTPS = "https://";
    private static final String ERROR_MESSAGE_LINK_NOT_FOUND = "Link not found!";
    private static final String ERROR_MESSAGE_LINK_IS_EXPIRED = "Your shortened link has expired!";
    private static final String EXPRESSION_VALID_LINK = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    public Link createShortedLink(final LinkDto linkDto) {
        checkIfTheLinkIsValid(linkDto);

        return save(
                new Link(
                linkDto.getLongLink(),
                generatedShortedLink(),
                true,
                LocalDate.now(Clock.systemUTC()),
                linkDto.getDays().getAmountDays())
        );
    }

    public Link createCustomShortedLink(final CustomLinkDto customLinkDto) {
        Optional<Link> originalLink = findLinkByShortedLink(customLinkDto.getCustomShortedLink());
        if (originalLink.isEmpty()) {
            checkIfTheCustomLinkIsValid(customLinkDto);
            return save(
              new Link(
                      customLinkDto.getLongLink(),
                      customLinkDto.getCustomShortedLink(),
                      true,
                      LocalDate.now(Clock.systemUTC()),
                      customLinkDto.getDays().getAmountDays()
              )
            );
        }
        return null;
    }

    public String getOriginalLink(final String linkShorted) {
        Optional<Link> originalLink = findLinkByShortedLink(linkShorted);
        if(originalLink.isPresent()) {
            checksIfTheShortenedLinkIsActive(originalLink.get());
            return originalLink.get().getLink();
        }
        else {
            throw new LinkNotFoundException(ERROR_MESSAGE_LINK_NOT_FOUND);
        }
    }

    public Boolean checkIfLinkIsValid(String link) {
        return Pattern.matches(EXPRESSION_VALID_LINK, link);
    }

    private String generatedShortedLink() {

        String[] characters = {
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
            "r", "s", "t", "u", "v", "x", "w", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "X", "W", "Y",
            "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
        };

        String randomCharacters = "";

        for(int i=0; i<10; i++) {
            randomCharacters += characters[(int) (Math.random()*characters.length)];
        }

        checkIfShortedLinkIsExists(randomCharacters);

        return randomCharacters;
    }

    private void checkIfShortedLinkIsExists(String shortedLink) {
        if (randomShortedLinkIsExists(shortedLink) == true)
            generatedShortedLink();
    }

    private Boolean randomShortedLinkIsExists(String shortedLink) {
        Optional<Link> actualLink = findLinkByShortedLink(shortedLink);
        return actualLink.isPresent() ? true : false;
    }

    private void checkIfTheLinkIsValid(LinkDto linkDto) {
        if (!checkIfThereIsAProtocol(linkDto.getLongLink()))
            linkDto.setLongLink(HTTPS.concat(linkDto.getLongLink()));
    }

    private void checkIfTheCustomLinkIsValid(CustomLinkDto customLinkDto) {
        if (!checkIfThereIsAProtocol(customLinkDto.getLongLink()))
            customLinkDto.setLongLink(HTTPS.concat(customLinkDto.getLongLink()));
    }

    private Boolean checkIfThereIsAProtocol(String link) {
        return Pattern.matches(EXPRESSION_PROTOCOLS, link);
    }

    private void checksIfTheShortenedLinkIsActive(Link link) {
        if(!link.getIsActivated()) {
            throw new ShortenedLinkIsExpiredException(ERROR_MESSAGE_LINK_IS_EXPIRED);
        }
        LocalDate dueDate = link.getCreateDate().plusDays(link.getNumberOfDaysActive());
        if(dueDate.isBefore(LocalDate.now()) && link.getNumberOfDaysActive() > 0) {
            disableShortenedLink(link);
            throw new ShortenedLinkIsExpiredException(ERROR_MESSAGE_LINK_IS_EXPIRED);
        }
    }

    @Transactional(readOnly = false)
    private Link save(final Link link) {
        return linkRepository.save(link);
    }

    @Transactional(readOnly = true)
    private Optional<Link> findLinkByShortedLink(String linkShorted) {
        return linkRepository.findLinkByShortedLink(linkShorted);
    }

    @Transactional(readOnly = false)
    private void disableShortenedLink(Link link) {
        link.setIsActivated(false);
        linkRepository.save(link);
    }
}