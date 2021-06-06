package io.wisoft.iotplatform.cnc.domain;

import io.wisoft.iotplatform.cnc.repository.CncRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CncTest {

  @Autowired
  private CncRepository cncRepository;

  private Cnc cnc;

  @BeforeAll
  void setUp() {
    cnc = cncRepository.save(Cnc.builder()
        .serial("C000003")
        .name("test-cnc")
        .location("N5-503")
        .status(CncStatus.ACTIVE)
        .ipAddress("192.168.10.254")
        .joined(LocalDateTime.now())
        .updated(LocalDateTime.now())
        .description("This is Test Cnc")
        .groupId(UUID.randomUUID()).build());
  }

  @Test
  void Given_Cnc_When_CncSaveOne_Then_ReturnValueWillOne() {

    assertNotNull(cnc);
    assertThat(cnc).isNotNull();
  }

  @Test
  void Given_Cnc_When_CncFindByAll_Then_ReturnCncList() {
    List<Cnc> cncs = cncRepository.findAll();

    assertThat(cncs).hasSize(3);
  }

  @Test
  void Given_Cnc_When_CncUpdateOne_Then_WillUpdateCnc() {
    cnc.setName("cnc-test");
    cnc.setLocation("N5-511");
    cnc.setStatus(CncStatus.INACTIVE);
    cnc.setJoined(LocalDateTime.now());
    cnc.setUpdated(LocalDateTime.now());

    Cnc changeCnc = cncRepository.save(cnc);

    assertThat(changeCnc.getName()).isEqualTo("cnc-test");
    assertThat(changeCnc.getLocation()).isEqualTo("N5-511");
    assertThat(changeCnc.getStatus()).isEqualTo(CncStatus.INACTIVE);
  }

  @Test
  void Given_Cnc_When_CncDeleteOne_Then_ReturnNotEmpty() {
    cncRepository.delete(cnc);
    List<Cnc> cncs = cncRepository.findAll();
    assertThat(cncs).isNotEmpty();
  }

}