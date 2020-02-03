package md.ct.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import md.ct.domain.Courier;
import md.ct.domain.Store;
import md.ct.domain.StoreTime;
import md.ct.dto.StoreDTO;
import md.ct.exception.CourierTrackingException;
import md.ct.repository.CourierRepo;
import md.ct.repository.StoreRepo;
import md.ct.repository.StoreTimeRepo;
import md.ct.service.CourierTrackingService;
import md.ct.util.Dto2Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class CourierTrackingServiceImpl implements CourierTrackingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourierTrackingServiceImpl.class);
    private final StoreRepo storeRepo;
    private final CourierRepo courierRepo;
    private final StoreTimeRepo storeTimeRepo;
    private final Dto2Entity dto2Entity;

    private static boolean initialized = false;

    @Autowired
    public CourierTrackingServiceImpl(StoreRepo storeRepo, CourierRepo courierRepo, StoreTimeRepo storeTimeRepo, Dto2Entity dto2Entity) {
        this.storeRepo = storeRepo;
        this.courierRepo = courierRepo;
        this.storeTimeRepo = storeTimeRepo;
        this.dto2Entity = dto2Entity;
        if(!initialized)
            initialize();
    }

    @Override
    public Long streamGeolocationOfCourier(LocalDateTime time, Long courierId, Double latitude, Double longitude) {
        Courier courier = getCourier(courierId, latitude, longitude);
        List<Store> stores = storeRepo.findAll();
        for(Store store: stores) {
            Double distance = calculateDistance(store.getLatitude(), courier.getLatitude(), store.getLongitude(), courier.getLongitude());
            if(distance<100) {
                countEntrance(store, courier, time);
            }
        }
        calculateCourierNewPosition(courier, latitude, longitude);
        return 1L;
    }

    @Override
    public Double getTotalTravelDistance(Long courierId) {
        Courier courier = courierRepo.findOneById(courierId);
        if(courier == null)
            throw new CourierTrackingException("There is any Courier for : " + courierId);

        return courier.getTotalDistance();
    }

    @Override
    public Courier createCourier(Long courierId, Double latitude, Double longitude) {
        Courier courier = new Courier();
        courier.setId(courierId);
        courier.setLongitude(longitude);
        courier.setLatitude(latitude);
        courier.setTotalDistance((double)0);
        return courier;
    }

    private void initialize() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<StoreDTO> storeDTOs = Arrays.asList(objectMapper.readValue(new File("src/main/resources/stores.json"), StoreDTO[].class));
            for(StoreDTO storeDTO: storeDTOs)
                storeRepo.save(dto2Entity.fromStoreDTO(storeDTO));

            initialized = true;
        } catch (IOException e) {
            LOGGER.error("There is no file which is name stores.json ");
            System.exit(1);
        }
    }

    private Courier getCourier(Long courierId, Double latitude, Double longitude) {
        Courier courier = courierRepo.findOneById(courierId);
        if(courier == null)
            courier = createCourier(courierId, latitude, longitude);

        return courier;
    }

    private Double calculateDistance(Double firstLatitude, Double firstLongitude, Double secondLatitude, Double secondLongitude) {
        return Math.abs(Math.sqrt(Math.pow((secondLatitude - firstLatitude), 2) + Math.pow((secondLongitude - firstLongitude), 2)));
    }

    private void countEntrance(Store store, Courier courier, LocalDateTime time) {
        List<StoreTime> storeTimes = storeTimeRepo.findAllByStoreAndCourierAndTimeIsGreaterThanEqual(store, courier, time.minusMinutes(1));
        StoreTime storeTime = new StoreTime();
        storeTime.setTime(time);
        storeTime.setCourier(courier);
        storeTime.setStore(store);
        storeTimeRepo.save(storeTime);
        if(!storeTimes.isEmpty())
            return;

        LOGGER.info(String.format("Courier : %d , Store : %s", courier.getId(), store.getName()));
    }

    private void calculateCourierNewPosition(Courier courier, Double latitude, Double longitude) {
        courier.setTotalDistance(courier.getTotalDistance() + calculateDistance(courier.getLatitude(), courier.getLongitude(), latitude, longitude));
        courier.setLatitude(latitude);
        courier.setLongitude(longitude);
    }

}
