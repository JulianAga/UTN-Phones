package com.utn.phones.controllers.web;

import static org.mockito.Mockito.when;

import com.utn.phones.Sessions.SessionManager;
import com.utn.phones.controllers.CallController;
import com.utn.phones.dto.CallRequestDto;
import com.utn.phones.model.Call;
import com.utn.phones.restUtils.RestUtils;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.ResponseEntity;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RestUtils.class)
public class AntennaWebControllerTest {

  @Mock
  SessionManager sessionManager;

  @Mock
  RestUtils restUtils;

  @Mock
  CallController callController;

  AntennaWebController antennaWebController;

  @Before
  public void setUp() {
    antennaWebController = new AntennaWebController(callController, sessionManager);
    PowerMockito.mockStatic(RestUtils.class);
  }

  @Test
  public void saveCallOk() {
    CallRequestDto callRequestDto = CallRequestDto.builder().originNumber("123")
        .destinyNumber("321").date(LocalDate.now())
        .duration(30).build();

    Call callTest = Call.builder().originNumber("123").destinyNumber("321").build();

    when(callController.save(callRequestDto))
        .thenReturn(callTest);

    when(RestUtils.getCallLocation(callTest)).thenReturn(URI.create("page/1"));
    ResponseEntity<?> returnedUri = antennaWebController.saveCall("111", callRequestDto);
    List<String> headers = returnedUri.getHeaders().get("location");
    assert headers != null;
    Assert.assertEquals(headers.get(0), "page/1");
  }

}
