package io.wisoft.iotplatform.group.service;

import io.wisoft.iotplatform.group.domain.Group;
import io.wisoft.iotplatform.group.exception.GroupDuplicatedException;
import io.wisoft.iotplatform.group.exception.GroupNotFoundException;
import io.wisoft.iotplatform.group.repository.GroupRepository;
import io.wisoft.iotplatform.otherservice.dto.CncResponse;
import io.wisoft.iotplatform.group.service.dto.GroupDto.GroupCncResponse;
import io.wisoft.iotplatform.group.service.dto.GroupDto.GroupRegister;
import io.wisoft.iotplatform.group.service.dto.GroupDto.GroupUpdate;
import io.wisoft.iotplatform.otherservice.CncService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class GroupService {

  private final GroupRepository groupRepository;
  private final CncService cncService;
  private final ModelMapper modelMapper;

  public Group register(final GroupRegister groupDto) {
    checkDuplicateName(groupDto.getName());
    final Group group = modelMapper.map(groupDto, Group.class);

    return groupRepository.save(group);
  }

  public Group update(final UUID id, final GroupUpdate updateDto) {
    final Group group = getGroup(id);
    group.setName(updateDto.getName());
    group.setDescription(updateDto.getDescription());
    group.setUpdated(LocalDateTime.now());

    return groupRepository.save(group);
  }

  public void remove(final UUID id) {
    groupRepository.delete(getGroup(id));
  }

  public Group getGroup(final UUID id) {
    return groupRepository.findById(id).orElseThrow(() -> new GroupNotFoundException(String.valueOf(id)));
  }

  public GroupCncResponse getCncsListInGroup(final UUID id) {
    final Group group = getGroup(id);
    final List<CncResponse> cncs = cncService.getCncByGroupId(id);

    GroupCncResponse groupCncResponse = modelMapper.map(group, GroupCncResponse.class);
    groupCncResponse.setCncs(cncs);

    return groupCncResponse;
  }

  public Page<Group> getGroups(final Pageable pageable) {
    return groupRepository.findAll(pageable);
  }

  private void checkDuplicateName(final String name) {
    groupRepository.findByName(name).ifPresent(group -> {
      log.error("Group Duplicated Name Exception: {}", group.getName());
      throw new GroupDuplicatedException(group.getName());
    });
  }

}
