package io.wisoft.iotplatform.cnc.fixtures;

import io.wisoft.iotplatform.cnc.domain.CncStatus;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static io.wisoft.iotplatform.cnc.service.dto.CncDto.*;

@SuppressWarnings("squid:S1118")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Fixtures {


  public static CncRegister registerCncDto() {
    return new CncRegister().builder()
        .serial("testCncSn1")
        .name("testCnC")
        .status(CncStatus.INSTALL)
        .location("N501-중앙")
        .ipAddress("localhost")
        .description("테스트 CnC입니다.")
        .build();
  }

}
