package com.utn.phones.services;

import com.utn.phones.model.City;
import com.utn.phones.model.Client;
import com.utn.phones.model.PhoneLine;
import com.utn.phones.model.UserType;
import com.utn.phones.repositories.PhoneLineRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.Before;
import sun.jvm.hotspot.utilities.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
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
