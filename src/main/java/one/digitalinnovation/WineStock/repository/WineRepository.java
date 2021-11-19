package one.digitalinnovation.WineStock.repository;

import one.digitalinnovation.WineStock.entity.Wine;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WineRepository extends JpaRepository<Wine, Long> {

    Optional<Wine> findByName(String name);
}
