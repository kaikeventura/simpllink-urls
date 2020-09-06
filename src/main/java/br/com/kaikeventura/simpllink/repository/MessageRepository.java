package br.com.kaikeventura.simpllink.repository;

import br.com.kaikeventura.simpllink.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
