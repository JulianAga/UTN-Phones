package com.utn.phones.restUtils;

import com.utn.phones.model.Call;
import com.utn.phones.model.Client;
import com.utn.phones.model.PhoneLine;
import java.net.URI;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class RestUtils {
  public static URI getCallLocation(Call call) {
    return ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(call.getId())
        .toUri();
  }

  public static URI getClientLocation(Client client) {
    return ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(client.getId())
        .toUri();
  }

  public static URI getPhoneLineLocation(PhoneLine phoneLine) {
    return ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(phoneLine.getId())
        .toUri();
  }
}
