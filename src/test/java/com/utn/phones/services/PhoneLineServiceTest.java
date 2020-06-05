package com.utn.phones.services;

import com.utn.phones.model.PhoneLine;
import com.utn.phones.repositories.PhoneLineRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneLineServiceTest {

    @Mock
    PhoneLineRepository phoneLineRepository;

    @Before
    public void setUp(){
        initMocks(this);
    }

    @Test
    public void testGetGarciasPhoneLines(){
        List<PhoneLine> garciasLines= new ArrayList<PhoneLine>();
        garciasLines.add(new PhoneLine());
        when(phoneLineRepository.getGarciasPhoneLines()).thenReturn(garciasLines);
        assertEquals(phoneLineRepository.getGarciasPhoneLines(), garciasLines);
    }

}
