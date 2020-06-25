package com.utn.phones.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BetweenDatesDto {

  @JsonProperty
  private LocalDate start;
  @JsonProperty
  private LocalDate end;
}
