package io.wisoft.iotplatform.model.service.dto;

import io.wisoft.iotplatform.model.domain.ModelType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@SuppressWarnings("squid:S1118")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelDto {

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ModelRegister {

    @NotBlank
    @Size(min = 4, max = 30)
    private String name;

    @NotNull
    private UUID modelTypeId;

    @Size(max = 250)
    private String description;

  }

  @Getter
  @Setter
  public static class ModelResponse {

    private UUID id;
    private String name;
    private ModelType modelType;
    private Date joined;
    private Date updated;
    private String description;

  }

  @Getter
  @Setter
  public static class ModelUpdate {

    @NotBlank
    @Size(min = 4, max = 30)
    private String name;

    @NotNull
    private UUID modelTypeId;

    @Size(max = 250)
    private String description;

  }

}
