package io.wisoft.iotplatform.group.domain;

import io.wisoft.iotplatform.group.repository.GroupRepository;
import io.wisoft.iotplatform.group.service.dto.GroupDto.GroupRegister;
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
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GroupTest {

  @Autowired
  private GroupRepository groupRepository;

  private Group group;

  @BeforeAll
  void setUp() {
    group = groupRepository.save(Group.builder()
//        .id(3L)
        .name("TestGroup")
        .joined(LocalDateTime.now())
        .updated(LocalDateTime.now())
        .description("Test하기 위한 Group입니다.").build());
  }

  @Test
  void Given_Group_When_GroupSaveOne_Then_ReturnValueWillOne() {

    assertNotNull(group);
    assertThat(group.getId()).isNotNull();
  }

  @Test
  void Given_Group_When_GroupFindAll_Then_ReturnGroupList() {
    List<Group> groups = groupRepository.findAll();

    assertThat(groups).hasSize(3);
  }

  @Test
  void Given_Group_When_GroupUpdateOne_Then_WillUpdateGroup() {
    group.setName("testGroup");
    group.setDescription("Test하기 위한 Group2입니다.");
    Group changeGroup = groupRepository.save(group);

    assertThat(changeGroup.getName()).isEqualTo("testGroup");
    assertThat(changeGroup.getDescription()).isEqualTo("Test하기 위한 Group2입니다.");
  }

  @Test
  void Given_Group_When_GroupDeleteOne_Then_ReturnNotEmpty() {
    groupRepository.delete(group);
    List<Group> groups = groupRepository.findAll();

    assertThat(groups).isNotEmpty();
  }

}