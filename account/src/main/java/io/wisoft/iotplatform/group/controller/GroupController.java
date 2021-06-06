package io.wisoft.iotplatform.group.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.wisoft.iotplatform.group.domain.Group;
import io.wisoft.iotplatform.group.service.GroupService;
import io.wisoft.iotplatform.group.service.dto.GroupDto.GroupCncResponse;
import io.wisoft.iotplatform.group.service.dto.GroupDto.GroupRegister;
import io.wisoft.iotplatform.group.service.dto.GroupDto.GroupResponse;
import io.wisoft.iotplatform.group.service.dto.GroupDto.GroupUpdate;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/groups")
@Api(value = "groups")
@AllArgsConstructor
public class GroupController {

  private GroupService groupService;
  private ModelMapper modelMapper;

  @ApiOperation(value = "그룹 등록", notes = "그룹을 시스템에 등록합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @ResponseStatus(CREATED)
  @PostMapping
  public GroupResponse register(@RequestBody @Valid final GroupRegister registerDto) {
    final Group newGroup = groupService.register(registerDto);

    return modelMapper.map(newGroup, GroupResponse.class);
  }

  @ApiOperation(value = "그룹 정보 갱신", notes = "시스템에 등록되어 있는 그룹 정보를 갱신합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @ResponseStatus(OK)
  @PutMapping(value = "{id}")
  public GroupResponse update(@PathVariable final UUID id,
                              @RequestBody @Valid final GroupUpdate updateDto) {
    final Group updatedGroup = groupService.update(id, updateDto);

    return modelMapper.map(updatedGroup, GroupResponse.class);
  }

  @ApiOperation(value = "그룹 정보 삭제", notes = "시스템에서 특정 그룹의 정보를 삭제합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(NO_CONTENT)
  @DeleteMapping(value = "{id}")
  public void remove(@PathVariable final UUID id) {
    groupService.remove(id);
  }

  @ApiOperation(value = "전체 그룹 조회", notes = "시스템에 등록되어 있는 전체 그룹을 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @GetMapping
  public Page<GroupResponse> getGroups(final Pageable pageable) {
    final Page<Group> groups = groupService.getGroups(pageable);
    final List<GroupResponse> content = groups.getContent()
        .stream()
        .map(group -> modelMapper.map(group, GroupResponse.class))
        .collect(Collectors.toList());

    return new PageImpl<>(content, pageable, groups.getTotalElements());
  }

  @ApiOperation(value = "특정 그룹 조회", notes = "그룹에 등록되어 있는 특정 사용자를 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping(value = "{id}")
  public GroupResponse getGroup(@PathVariable final UUID id) {
    final Group group = groupService.getGroup(id);

    return modelMapper.map(group, GroupResponse.class);
  }

  @ApiOperation(value = "특정 그룹에 속한 CnC 목록 조회",
      notes = "'Group Id'를 활용하여 시스템에 등록되어 있는 CnC 목록을 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping(value = "{id}/cncs")
  public GroupCncResponse getCncListInGroup(@PathVariable final UUID id) {
    return groupService.getCncsListInGroup(id);
  }

}
