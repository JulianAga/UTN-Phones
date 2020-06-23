package com.utn.phones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MostCalledDto {

  private String cityName;

  private Integer cant;

}
