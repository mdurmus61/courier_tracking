package md.ct.repository;


import md.ct.domain.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepo extends JpaRepository<Courier, Long> {
    Courier findOneById(Long id);
}
