package com.utn.phones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDto {

  public Integer code;

  public String description;

}
