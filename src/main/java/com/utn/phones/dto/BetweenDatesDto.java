package com.utn.phones.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.Data;

@Data
public class BetweenDatesDto {

  @JsonProperty
  @JsonFormat(pattern = "dd-MM-yyyy")
  LocalDate start;
  @JsonProperty
  @JsonFormat(pattern = "dd-MM-yyyy")
  LocalDate end;
}
