package io.wisoft.iotplatform.cnc.service;

import io.wisoft.iotplatform.cnc.domain.Cnc;
import io.wisoft.iotplatform.cnc.domain.CncStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static io.wisoft.iotplatform.cnc.service.dto.CncDto.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CncServiceTest {

  @Autowired
  private CncService cncService;

  private CncRegister registerCncDto;

  @BeforeAll
  public void setup() {

    this.registerCncDto = CncRegister.builder()
        .serial("C000003")
        .name("Test-Cnc")
        .location("Test")
        .status(CncStatus.ACTIVE)
        .ipAddress("192.168.10.254")
        .groupId(2L).build();
  }

  @Test
  public void equals() throws Exception {
    // Given
    final Cnc cnc = cncService.register(this.registerCncDto);

    // When & Then
    assertTrue(cnc.equals(cnc), "Class equals to itself.");
  }

  @Test
  public void isHashcodeConsistent() throws Exception {
    // Given
    final Cnc cnc = cncService.register(this.registerCncDto);

    // When & // Then
    assertEquals(cnc.hashCode(), cnc.hashCode());
  }
}

