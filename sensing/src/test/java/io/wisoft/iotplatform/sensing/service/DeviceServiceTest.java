package io.wisoft.iotplatform.sensing.service;

import io.wisoft.iotplatform.sensing.domain.Device;
import io.wisoft.iotplatform.sensing.domain.Device.DeviceStatus;
import io.wisoft.iotplatform.sensing.domain.Device.DeviceType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static io.wisoft.iotplatform.sensing.service.dto.DeviceDto.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeviceServiceTest {

  @Autowired
  private DeviceService deviceService;

  private DeviceRegister registerDeviceDto;

  @BeforeAll
  public void setup() {

    this.registerDeviceDto = DeviceRegister.builder()
        .serial("D000003")
        .name("test-device")
        .deviceType(DeviceType.SN)
        .location("N5-503")
        .status(DeviceStatus.ACTIVE)
        .battery(100)
        .ipAddress("192.168.10.254")
        .protocol("HTTP")
        .sleep(false)
        .description("This is Test Device")
        .cncId(2L)
        .build();
  }

  @Test
  public void equals() throws Exception {
    // Given
    final Device device = deviceService.register(this.registerDeviceDto);

    // When & Then
    assertTrue("Class equals to itself.", device.equals(device));
  }

  @Test
  public void isHashcodeConsistent() throws Exception {
    // Given

    final Device device = deviceService.register(this.registerDeviceDto);

    // When & Then
    assertEquals(device.hashCode(), device.hashCode());
  }

}