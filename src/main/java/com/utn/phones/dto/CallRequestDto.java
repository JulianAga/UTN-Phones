package com.utn.phones.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import javax.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CallRequestDto {

  @JsonProperty(value = "origin_number")
  private String originNumber;

  @JsonProperty(value = "destiny_number")
  private String destinyNumber;

  private Integer duration;

  @PastOrPresent
  private LocalDate date;
}
