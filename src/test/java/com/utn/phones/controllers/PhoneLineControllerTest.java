package com.utn.phones.controllers;

import com.utn.phones.model.PhoneLine;
import com.utn.phones.services.PhoneLineService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneLineControllerTest {

    PhoneLineController phoneLineController;

    @Mock
    PhoneLineService phoneLineService;

    @Before
    public void setUp(){
        initMocks(this);
        phoneLineController= new PhoneLineController(phoneLineService);
    }

  /*  @Test(expected = NoGarciasLinesFoundException.class )
    public void testGarciasWithNoLines() throws NoGarciasLinesFoundException {
        when(phoneLineService.getGarciasPhoneLines()).thenThrow(new NoGarciasLinesFoundException());
        phoneLineController.getGarciasPhoneLines();
    }
*/
    @Test
    public void testGetGarciasPhoneLines(){
        List<PhoneLine> garciasLines= new ArrayList<PhoneLine>();
        garciasLines.add(new PhoneLine());
        when(phoneLineService.getGarciasPhoneLines()).thenReturn(garciasLines);
        List<PhoneLine> garciaTest= new ArrayList<PhoneLine>();
        garciaTest.add(new PhoneLine());
        assertEquals(phoneLineService.getGarciasPhoneLines(), garciaTest);
    }
}
