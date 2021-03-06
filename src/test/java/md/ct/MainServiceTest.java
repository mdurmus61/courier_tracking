package md.ct;

import md.ct.domain.Courier;
import md.ct.exception.CourierTrackingException;
import md.ct.repository.CourierRepo;
import md.ct.repository.StoreRepo;
import md.ct.repository.StoreTimeRepo;
import md.ct.request.StreamGeolocationRequest;
import md.ct.service.CourierTrackingService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)

@DataJpaTest(includeFilters = @ComponentScan.Filter(classes = {Service.class, Component.class}))
public class MainServiceTest {

    @Autowired
    private CourierTrackingService service;

    @Autowired
    private CourierRepo courierRepo;

    @Autowired
    private StoreRepo storeRepo;

    @Autowired
    private StoreTimeRepo storeTimeRepo;


    @Test
    public void create_courier()  {
        Courier c = service.createCourier(4L, 10.0, 10.0);

        Assert.assertNotNull(c);
        Assert.assertEquals(4, courierRepo.findAll().size());
    }

    @Test
    public void stream_geolocation_of_courier() {
        StreamGeolocationRequest request = new StreamGeolocationRequest();
        request.setCourierId(2L);
        request.setLatitude(45.0);
        request.setLongitude(35.0);
        request.setTime(LocalDateTime.parse("2020-02-03T19:33:49"));

        service.streamGeolocationOfCourier(request.getTime(), request.getCourierId(), request.getLatitude(), request.getLongitude());
        Assert.assertEquals(3, courierRepo.findAll().size());
        Assert.assertEquals(5, storeRepo.findAll().size());
        Assert.assertEquals(5, storeTimeRepo.findAll().size());

        request = new StreamGeolocationRequest();
        request.setCourierId(2L);
        request.setLatitude(44.0);
        request.setLongitude(35.0);
        request.setTime(LocalDateTime.parse("2020-02-03T19:33:54"));

        service.streamGeolocationOfCourier(request.getTime(), request.getCourierId(), request.getLatitude(), request.getLongitude());
        Assert.assertEquals(3, courierRepo.findAll().size());
        Assert.assertEquals(5, storeRepo.findAll().size());
        Assert.assertEquals(10, storeTimeRepo.findAll().size());

    }

    @Test(expected = CourierTrackingException.class)
    public void get_total_distance_by_courier_error() {
        service.getTotalTravelDistance(4L);
    }

    @Test
    public void get_total_distance_by_courier() {
        StreamGeolocationRequest request = new StreamGeolocationRequest();
        request.setCourierId(2L);
        request.setLatitude(45.0);
        request.setLongitude(35.0);
        request.setTime(LocalDateTime.parse("2020-02-03T19:33:49"));

        service.streamGeolocationOfCourier(request.getTime(), request.getCourierId(), request.getLatitude(), request.getLongitude());

        Assert.assertEquals(new Double("0.0"), service.getTotalTravelDistance(1L));
        Assert.assertEquals(new Double("57.0087712549569"), service.getTotalTravelDistance(2L));
        Assert.assertEquals(new Double("0.0"), service.getTotalTravelDistance(3L));

        request = new StreamGeolocationRequest();
        request.setCourierId(2L);
        request.setLatitude(44.0);
        request.setLongitude(35.0);
        request.setTime(LocalDateTime.parse("2020-02-03T19:33:54"));

        service.streamGeolocationOfCourier(request.getTime(), request.getCourierId(), request.getLatitude(), request.getLongitude());

        Assert.assertEquals(new Double("0.0"), service.getTotalTravelDistance(1L));
        Assert.assertEquals(new Double("58.0087712549569"), service.getTotalTravelDistance(2L));
        Assert.assertEquals(new Double("0.0"), service.getTotalTravelDistance(3L));

    }



}