package io.wisoft.iotplatform.group.service.dto;

import io.wisoft.iotplatform.otherservice.dto.CncResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class GroupDto {

  private GroupDto() {
  }

  @Getter
  @Setter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class GroupRegister {

    @NotBlank
    @Size(min = 4, max = 30)
    private String name;

    @Size(max = 250)
    private String description;

  }

  @Getter
  @Setter
  public static class GroupResponse {

    private UUID id;
    private String name;
    private LocalDateTime joined;
    private LocalDateTime updated;
    private String description;

  }

  @Getter
  @Setter
  public static class GroupCncResponse {

    private UUID id;
    private String name;
    private List<CncResponse> cncs;

  }

  @Getter
  @Setter
  public static class GroupUpdate {

    @NotBlank
    @Size(min = 5, max = 15)
    private String name;

    @Size(max = 250)
    private String description;

  }

}
