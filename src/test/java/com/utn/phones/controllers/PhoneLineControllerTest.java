package com.utn.phones.controllers;

import com.utn.phones.exceptions.NoGarciasLinesFoundException;
import com.utn.phones.model.Client;
import com.utn.phones.model.LineType;
import com.utn.phones.model.PhoneLine;
import com.utn.phones.services.PhoneLineService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
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

    @Test(expected= NoGarciasLinesFoundException.class)
    public void testGarciasWithNoLines() throws NoGarciasLinesFoundException {
        doNothing().when(phoneLineService.getGarciasPhoneLines());
        phoneLineController.getGarciasPhoneLines();
    }

  @Test
  public void testGarciasPhoneLines() throws NoGarciasLinesFoundException{
      PhoneLine p1= new PhoneLine(50, "2235484899", new LineType(), new Client());
      PhoneLine p2= new PhoneLine(55, "115588449", new LineType(), new Client());

      List<PhoneLine> phoneLines = Arrays.asList(p1,p2);
      when(this.phoneLineService.getGarciasPhoneLines()).thenReturn(phoneLines);

      List<PhoneLine> phoneLines2 = this.phoneLineService.getGarciasPhoneLines();

      Assert.assertEquals(phoneLines.size(), phoneLines2.size());
      Assert.assertEquals(phoneLines.get(0).getId(), phoneLines2.get(0).getId());
  }

}
