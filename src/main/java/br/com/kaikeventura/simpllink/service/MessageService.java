package br.com.kaikeventura.simpllink.service;

import br.com.kaikeventura.simpllink.dto.MessageDto;
import br.com.kaikeventura.simpllink.error.exception.NumberOfCharactersExceededException;
import br.com.kaikeventura.simpllink.model.Message;
import br.com.kaikeventura.simpllink.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public Message save(final MessageDto messageDto) {
        Message message = new Message(
                messageDto.getName(),
                messageDto.getEmail(),
                messageDto.getMessage(),
                LocalDate.now()
        );
        try {
            return messageRepository.save(message);
        }
        catch (DataIntegrityViolationException e) {
            throw new NumberOfCharactersExceededException();
        }
    }
}
