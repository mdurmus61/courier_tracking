package md.ct.controller;

import md.ct.request.StreamGeolocationRequest;
import md.ct.response.StreamGeolocationResponse;
import md.ct.response.TotalDistanceResponse;
import md.ct.service.CourierTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/ct", produces = {  MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"}, consumes = {
        MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class CourierTrackingApiController {

    private final CourierTrackingService courierTrackingService;

    @Autowired
    public CourierTrackingApiController(CourierTrackingService courierTrackingService) {
        this.courierTrackingService = courierTrackingService;
    }

    @PostMapping(value = "/stream/geolocation", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public StreamGeolocationResponse streamGeolocationOfCourier(@RequestBody StreamGeolocationRequest request) {
        return new StreamGeolocationResponse(courierTrackingService.streamGeolocationOfCourier(request.getTime(), request.getCourierId(), request.getLatitude(), request.getLongitude()));
    }

    @GetMapping(value = "/total/distance/{COURIER_ID}", consumes = MediaType.ALL_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public TotalDistanceResponse getTotalDistanceByCourier(@PathVariable("COURIER_ID") Long courierId) {
        return new TotalDistanceResponse(courierTrackingService.getTotalTravelDistance(courierId));
    }
}