package com.utn.phones.services;

import com.utn.phones.exceptions.NoGarciasLinesFoundException;
import com.utn.phones.model.PhoneLine;
import com.utn.phones.repositories.PhoneLineRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneLineServiceTest {

    @Mock
    PhoneLineRepository phoneLineRepository;

    PhoneLineService phoneLineService;

    @Before
    public void setUp(){
        initMocks(this);
        phoneLineService= new PhoneLineService(phoneLineRepository);
    }

    @Test
    public void testGetGarciasPhoneLines() throws NoGarciasLinesFoundException {
        List<PhoneLine> garciasLines= new ArrayList<PhoneLine>();
        when(phoneLineRepository.getGarciasPhoneLines()).thenReturn(garciasLines);
        garciasLines.add(new PhoneLine());
        List<PhoneLine> garciaTest= this.phoneLineService.getGarciasPhoneLines();
        assertEquals(garciaTest.size(), garciasLines.size());
        verify(phoneLineRepository, times(1)).getGarciasPhoneLines();
    }

}
