package com.utn.phones.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OriginCityAndDestinyCityDto {

  private String originCity;

  private String destinyCity;

}
