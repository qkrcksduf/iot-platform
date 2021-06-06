package io.wisoft.iotplatform.model.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@SuppressWarnings("squid:S1118")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelTypeDto {

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ModelTypeRegister {

    @NotBlank
    @Size(min = 2, max = 30)
    private String name;

    @Size(max = 250)
    private String description;

  }

  @Getter
  @Setter
  public static class ModelTypeResponse {

    private UUID id;
    private String name;
    private Date joined;
    private Date updated;
    private String description;

  }

  @Getter
  @Setter
  public static class ModelTypeUpdate {

    @NotBlank
    @Size(min = 2, max = 30)
    private String name;

    @Size(max = 250)
    private String description;

  }

}
