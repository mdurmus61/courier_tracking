package md.ct;

import md.ct.domain.Courier;
import md.ct.repository.CourierRepo;
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

import java.util.List;

@RunWith(SpringRunner.class)

@DataJpaTest(includeFilters = @ComponentScan.Filter(classes = {Service.class, Component.class}))
public class MainServiceTest {

    @Autowired
    private CourierRepo courierRepo;

    //private final TestFactory testFactory;

    public MainServiceTest() {
      //  this.testFactory = new TestFactory();
    }

    @Autowired
    private CourierTrackingService service;

    @Test
    public void create_user()  {
        Courier c = service.createCourier(4L, 10.0, 10.0);

        Assert.assertNotNull(c);
        Assert.assertEquals(4, courierRepo.findAll().size());
    }



}