package md.ct.service;

import md.ct.domain.Courier;

import java.time.LocalDateTime;

public interface CourierTrackingService {
    Long streamGeolocationOfCourier(LocalDateTime time, Long courierId, Double latitude, Double longitude);
    Double getTotalTravelDistance(Long courierId);
    Courier createCourier(Long courierId, Double latitude, Double longitude);
}
