package io.wisoft.iotplatform.account.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountDto {

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AccountRegister {

    @NotBlank
    @Size(min = 5, max = 10)
    private String username;

    @NotBlank
    @Size(min = 5, max = 30)
    private String name;

    @NotBlank
    private String password;

    @NotBlank
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(max = 10)
    private String role;

    @Size(max = 250)
    private String description;

  }

  @Getter
  @Setter
  public static class AccountResponse {

    private UUID id;
    private String username;
    private String name;
    private String email;
    private LocalDateTime joined;
    private LocalDateTime updated;
    private String role;
    private String description;

  }

  @Value
  @Builder
  @AllArgsConstructor
  public static class AccountSignIn {

    @NotBlank
    @Size(min = 4, max = 20)
    private String username;

    @NotBlank
    private String password;

  }

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class AccountUpdate {

    @NotBlank
    @Size(min = 2, max = 30)
    private String name;

    @NotBlank
    private String password;

    @NotBlank
    @Size(max = 50)
    private String email;

    @Size(max = 250)
    private String description;

  }

}
