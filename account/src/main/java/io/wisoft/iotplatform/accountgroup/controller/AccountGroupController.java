package io.wisoft.iotplatform.accountgroup.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.wisoft.iotplatform.accountgroup.domain.AccountGroup;
import io.wisoft.iotplatform.accountgroup.service.AccountGroupService;
import io.wisoft.iotplatform.accountgroup.service.dto.AccountGroupDto.AccountGroupRegister;
import io.wisoft.iotplatform.accountgroup.service.dto.AccountGroupDto.AccountGroupResponse;
import io.wisoft.iotplatform.accountgroup.service.dto.AccountGroupDto.AccountGroupUpdate;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/accountGroups")
@Api(value = "account_groups")
@AllArgsConstructor
public class AccountGroupController {

  private AccountGroupService accountGroupService;
  private ModelMapper modelMapper;

  @ApiOperation(value = "사용자의 그룹 등록", notes = "사용자의 그룹을 시스템에 등록합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @ResponseStatus(CREATED)
  @PostMapping
  public AccountGroupResponse register(@RequestBody @Valid final AccountGroupRegister registerDto) {
    final AccountGroup accountGroup = accountGroupService.register(registerDto);

    return modelMapper.map(accountGroup, AccountGroupResponse.class);
  }

  @ApiOperation(value = "사용자/그룹 정보 갱신", notes = "시스템에 등록되어 있는 사용자/그룹 정보를 갱신합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @ResponseStatus(OK)
  @PutMapping(value = "{id}")
  public AccountGroupResponse update(@PathVariable final UUID id,
                                     @RequestBody @Valid final AccountGroupUpdate updateDto) {
    final AccountGroup updatedAccountGroup = accountGroupService.update(id, updateDto);

    return modelMapper.map(updatedAccountGroup, AccountGroupResponse.class);
  }

  @ApiOperation(value = "사용자/그룹 정보 삭제", notes = "시스템에서 특정 사용자/그룹의 정보를 삭제합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK")
  })
  @ResponseStatus(NO_CONTENT)
  @DeleteMapping(value = "{id}")
  public void remove(@PathVariable final UUID id) {
    accountGroupService.remove(id);
  }

  @ApiOperation(value = "전체 사용자/그룹 조회", notes = "시스템에 등록되어 있는 전체 사용자/그룹을 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @GetMapping
  public Page<AccountGroupResponse> getAccountGroups(final Pageable pageable) {
    final Page<AccountGroup> accountGroups = accountGroupService.getAccountGroups(pageable);
    final List<AccountGroupResponse> content = accountGroups.getContent().stream()
        .map(accountGroup -> modelMapper.map(accountGroup, AccountGroupResponse.class))
        .collect(toList());

    return new PageImpl<>(content, pageable, accountGroups.getTotalElements());
  }

  @ApiOperation(value = "특정 사용자 그룹 조회", notes = "시스템에 등록되어 있는 특정 사용자의 그룹을 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping(value = "{id}")
  public AccountGroupResponse getAccountGroup(@PathVariable final UUID id) {
    final AccountGroup accountGroup = accountGroupService.getAccountGroup(id);

    return modelMapper.map(accountGroup, AccountGroupResponse.class);
  }

  @ApiOperation(value = "특정 사용자의 그룹 조회", notes = "'사용자의 ID'가 포함되어 있는 사용자 그룹을 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping(value = "search/account-id")
  public List<AccountGroupResponse> getAccountGroupByAccountId(@RequestParam("q") final UUID accountId) {
    final List<AccountGroup> accountGroups = accountGroupService.getAccountGroupByAccountId(accountId);
    return accountGroups.stream()
        .map(accountGroup -> modelMapper.map(accountGroup, AccountGroupResponse.class))
        .collect(toList());
  }

}
