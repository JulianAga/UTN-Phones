package com.utn.phones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OriginCityAndDestinyCityDto {

  private String originCity;

  private String destinyCity;

}
