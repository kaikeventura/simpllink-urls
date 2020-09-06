package br.com.kaikeventura.simpllink.repository;

import br.com.kaikeventura.simpllink.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    Optional<Link> findLinkByShortedLink(String shortedLink);
}
