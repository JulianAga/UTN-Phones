package com.utn.phones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {

  private String dni;

  private String password;

  private String name;

  private String surname;

  private String username;

  private Integer city;

}
