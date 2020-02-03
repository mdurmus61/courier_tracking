package md.ct.repository;

import md.ct.domain.Courier;
import md.ct.domain.Store;
import md.ct.domain.StoreTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StoreTimeRepo extends JpaRepository<StoreTime, Long> {
    List<StoreTime> findAllByStoreAndCourierAndTimeIsGreaterThanEqual(Store store, Courier courier, LocalDateTime time);
}
