package com.utn.phones.dto;

import com.utn.phones.model.LineType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneLineDto {

  private Boolean suspended;

  private String number;

  private LineType lineType;

}
