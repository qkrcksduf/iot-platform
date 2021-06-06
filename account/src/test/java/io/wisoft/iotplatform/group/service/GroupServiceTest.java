package io.wisoft.iotplatform.group.service;

import io.wisoft.iotplatform.group.domain.Group;
import io.wisoft.iotplatform.group.service.dto.GroupDto.GroupRegister;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static io.wisoft.iotplatform.fixtures.Fixtures.registerGroupDto;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GroupServiceTest {

  @Autowired
  private GroupService groupService;

  private GroupRegister registerGroupDto;

  @BeforeAll
  public void setup() {
    this.registerGroupDto = registerGroupDto();
  }

  @Test
  public void equalsAccount() throws Exception {
    // Given
    final Group group = groupService.register(this.registerGroupDto);

    // When & Then
    assertTrue(group.equals(group), "Class equals to itself.");
  }

  @Test
  public void isHashcodeConsistent() throws Exception {
    // Given
    final Group group = groupService.register(this.registerGroupDto);

    // When & Then
    assertEquals(group.hashCode(), group.hashCode());
  }

}