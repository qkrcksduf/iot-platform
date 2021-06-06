package io.wisoft.iotplatform.actuating.eventsourcing.fixture;

import io.wisoft.iotplatform.actuating.domain.Device;
import io.wisoft.iotplatform.actuating.domain.Device.DeviceStatus;
import io.wisoft.iotplatform.actuating.domain.Device.DeviceType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static io.wisoft.iotplatform.actuating.service.dto.DeviceDto.*;

@SuppressWarnings("squid:S1118")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Fixtures {

  public static DeviceRegister registerDeviceDto() {
    return new DeviceRegister().builder()
                               .serial("deviceSn1")
                               .name("testDevice")
                               .status(DeviceStatus.INSTALL)
                               .deviceType(DeviceType.SAN)
                               .location("N501-창문")
                               .ipAddress("203.230.123.101")
                               .protocol("mqtt")
                               .sleep(false)
                               .description("테스트 장치입니다.")
                               .build();
  }


}
