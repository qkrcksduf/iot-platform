package io.wisoft.iotplatform.actuating.domain;

import io.wisoft.iotplatform.actuating.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeviceTest {

  @Autowired
  private DeviceRepository deviceRepository;

  private Device device;

  @BeforeAll
  void setUp() {
    device = deviceRepository.save(Device.builder()
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
        .cncid(2L)
        .build()
    );
  }

  @Test
  void Given_Device_When_DeviceSaveOne_Then_ReturnValueWillOne() {
    assertNotNull(device);
    assertThat(device).isNotNull();
  }

  @Test
  void Given_Device_When_DeviceFindByAll_Then_ReturnDeviceList() {
    List<Device> devices = deviceRepository.findAll();

    assertThat(devices).hasSize(3);
  }

  @Test
  void Given_Device_When_DeviceUpdateOne_Then_WillUpdateDevice() {
    device.setName("device-test");
    device.setLocation("N5-511");
    device.setStatus(DeviceStatus.INACTIVE);
    device.setJoined(LocalDateTime.now());
    device.setUpdated(LocalDateTime.now());

    Device changeDevice = deviceRepository.save(device);

    assertThat(changeDevice.getName()).isEqualTo("device-test");
    assertThat(changeDevice.getLocation()).isEqualTo("N5-511");
    assertThat(changeDevice.getStatus()).isEqualTo(DeviceStatus.INACTIVE);
  }

  @Test
  void Given_Device_When_DeviceDeleteOne_Then_ReturnNotEmpty() {
    deviceRepository.delete(device);
    List<Device> devices = deviceRepository.findAll();
    assertThat(devices).isNotEmpty();
  }

}