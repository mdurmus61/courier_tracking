package md.ct;

import md.ct.domain.Courier;
import md.ct.exception.CourierTrackingException;
import md.ct.repository.CourierRepo;
import md.ct.repository.StoreRepo;
import md.ct.repository.StoreTimeRepo;
import md.ct.request.StreamGeolocationRequest;
import md.ct.service.CourierTrackingService;
import md.ct.service.impl.CourierTrackingServiceImpl;
import md.ct.service.impl.DistanceLoggerService;
import md.ct.util.Dto2Entity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)

@DataJpaTest(includeFilters = @ComponentScan.Filter(classes = {Service.class, Component.class}))
public class LogServiceTest {

    private CourierTrackingService service;
    private DistanceLoggerService loggerService;

    @Autowired
    private CourierRepo courierRepo;

    @Autowired
    private StoreRepo storeRepo;

    @Autowired
    private StoreTimeRepo storeTimeRepo;

    @Autowired
    private Dto2Entity dto2Entity;

    @Before
    public void setUp() {
        loggerService = Mockito.mock(DistanceLoggerService.class);
        service = new CourierTrackingServiceImpl(loggerService, storeRepo, courierRepo, storeTimeRepo, dto2Entity);
    }

    @Test
    public void get_total_distance_by_courier() {
        StreamGeolocationRequest request = new StreamGeolocationRequest();
        request.setCourierId(2L);
        request.setLatitude(45.0);
        request.setLongitude(35.0);
        request.setTime(LocalDateTime.parse("2020-02-03T19:33:49"));

        service.streamGeolocationOfCourier(request.getTime(), request.getCourierId(), request.getLatitude(), request.getLongitude());

        verify(loggerService, times(5)).log(Mockito.anyLong(), Mockito.anyString());

        request = new StreamGeolocationRequest();
        request.setCourierId(2L);
        request.setLatitude(44.0);
        request.setLongitude(35.0);
        request.setTime(LocalDateTime.parse("2020-02-03T19:33:54"));

        service.streamGeolocationOfCourier(request.getTime(), request.getCourierId(), request.getLatitude(), request.getLongitude());

        verify(loggerService, times(5)).log(Mockito.anyLong(), Mockito.anyString());

        request = new StreamGeolocationRequest();
        request.setCourierId(2L);
        request.setLatitude(44.0);
        request.setLongitude(35.0);
        request.setTime(LocalDateTime.parse("2020-02-03T19:34:49"));

        service.streamGeolocationOfCourier(request.getTime(), request.getCourierId(), request.getLatitude(), request.getLongitude());

        verify(loggerService, times(5)).log(Mockito.anyLong(), Mockito.anyString());

        request = new StreamGeolocationRequest();
        request.setCourierId(2L);
        request.setLatitude(44.0);
        request.setLongitude(35.0);
        request.setTime(LocalDateTime.parse("2020-02-03T19:35:50"));

        service.streamGeolocationOfCourier(request.getTime(), request.getCourierId(), request.getLatitude(), request.getLongitude());

        verify(loggerService, times(10)).log(Mockito.anyLong(), Mockito.anyString());

    }



}