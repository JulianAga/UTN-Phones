package com.utn.phones.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class CallRequestDto {
    @JsonProperty
    @NotNull
    private String originNumber;
    @JsonProperty
    @NotNull
    private String destinyNumber;
    @JsonProperty
    @NotNull
    private Integer duration;

    @JsonProperty
    @NotNull
    private LocalDate date;
}
