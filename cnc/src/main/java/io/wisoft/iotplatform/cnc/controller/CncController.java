package io.wisoft.iotplatform.cnc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.wisoft.iotplatform.cnc.service.dto.CncDto.CncResponse;
import io.wisoft.iotplatform.cnc.domain.Cnc;
import io.wisoft.iotplatform.cnc.service.CncService;
import io.wisoft.iotplatform.cnc.otherservice.dto.DeviceResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static io.wisoft.iotplatform.cnc.service.dto.CncDto.*;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/cncs")
@Api(value = "cncs")
@AllArgsConstructor
public class CncController {

  private CncService cncService;
  private ModelMapper modelMapper;

  @ApiOperation(value = "CnC 등록", notes = "CnC를 시스템에 등록합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @ResponseStatus(CREATED)
  @PostMapping
  public CncResponse register(@RequestBody @Valid final CncRegister registerDto) {
    final Cnc newCnc = cncService.register(registerDto);

    return modelMapper.map(newCnc, CncResponse.class);
  }

  @ApiOperation(value = "CnC 정보 갱신", notes = "시스템에 등록되어 있는 CnC 정보를 갱신합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @ResponseStatus(OK)
  @PutMapping(value = "{id}")
  public CncResponse update(@PathVariable final UUID id,
                            @RequestBody @Valid final CncUpdate updateDto) {
    final Cnc updatedCnc = cncService.update(id, updateDto);

    return modelMapper.map(updatedCnc, CncResponse.class);
  }

  @ApiOperation(value = "CnC 초기 정보 설정", notes = "CnC의 초기 정보 설정합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @ResponseStatus(OK)
  @PutMapping(value = "{id}/initialization")
  public CncResponse initialize(@PathVariable final UUID id,
                                @RequestBody @Valid final CncInitialize dto) {
    final Cnc initializedCnc = cncService.initialize(id, dto);
    return modelMapper.map(initializedCnc, CncResponse.class);
  }

  @ApiOperation(value = "CnC 정보 삭제", notes = "시스템에서 특정 CnC의 정보를 삭제합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(NO_CONTENT)
  @DeleteMapping(value = "{id}")
  public void remove(@PathVariable final UUID id) {
    cncService.remove(id);
  }

  @ApiOperation(value = "전체 CnC 조회", notes = "시스템에 등록되어 있는 전체 CnC를 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping
  public Page<CncResponse> getCncs(final Pageable pageable) {
    final Page<Cnc> cncs = cncService.getCncs(pageable);
    final List<CncResponse> content = cncs.getContent()
        .stream()
        .map(cnc -> modelMapper.map(cnc, CncResponse.class))
        .collect(toList());

    return new PageImpl<>(content, pageable, cncs.getTotalElements());
  }

  @ApiOperation(value = "특정 CnC 조회", notes = "시스템에 등록되어 있는 특정 CnC를 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping(value = "{id}")
  public CncResponse getCnc(@PathVariable final UUID id) {
    final Cnc cnc = cncService.getCnc(id);

    return modelMapper.map(cnc, CncResponse.class);
  }

  @ApiOperation(value = "CnC 목록 조회: Group Id", notes = "'Group Id'를 활용하여 시스템에 등록되어 있는 CnC 목록을 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping(value = "search/group")
  public List<CncResponse> getCncListByGroupId(@RequestParam("q") final UUID groupId) {
    return cncService.getCncListByGroupId(groupId).stream()
        .map(cnc -> modelMapper.map(cnc, CncResponse.class))
        .collect(toList());
  }

  @ApiOperation(value = "특정 CnC 조회: Serial", notes = "'Serial Number'를 활용하여 시스템에 등록되어 있는 특정 CnC를 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping(value = "search/serial")
  public CncResponse getCncBySerial(@RequestParam("q") final String serial) {
    System.out.println("serial: " + serial);
    final Cnc cnc = cncService.getCncBySerial(serial);

    return modelMapper.map(cnc, CncResponse.class);
  }

  @ApiOperation(value = "특정 CnC 조회: Actuator Id",
      notes = "'Actuator Id'를 활용하여 시스템에 등록되어 있는 특정 CnC를 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping(value = "search/actuator")
  public CncResponse getCncByActuatorId(@RequestParam("q") final UUID actuatorId) {
    final Cnc cnc = cncService.getCncByActuatorId(actuatorId);

    return modelMapper.map(cnc, CncResponse.class);
  }

//  @ApiOperation(value = "특정 CnC와 연관된 동작중인 액추에이터 조회", notes = "특정 CnC에 연결되어 동작중인 액추에이터 목록을 조회합니다.")
//  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
//  @GetMapping(value = "{id}/actuatings/q")
//  @ResponseStatus(OK)
//  public List<ActuatingResponse> getActuatings(@PathVariable final Long id,
//                                               @RequestParam("result") final String result) {
//    final List<ActuatingDto> actuatings = cncService.getActuatingsById(id, result);
//    return actuatings.stream()
//        .map(actuator -> modelMapper.map(actuator, ActuatingResponse.class))
//        .collect(toList());
//  }

  @ApiOperation(value = "특정 CnC와 연관된 디바이스 조회", notes = "특정 CnC에 연결되어 있는 디바이스 목록을 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping(value = "{id}/devices")
  public List<DeviceResponse> getCncDevices(@PathVariable final UUID id) {
    return cncService.getDeviceListOfCnc(id);
  }

  @ApiOperation(value = "특정 CnC 조회: Name", notes = "이름을 활용하여 시스템에 등록되어 있는 특정 CnC를 조회합니다.")
  @ResponseStatus(OK)
  @GetMapping("search/username")
  public CncResponse getCncByName(@RequestParam(value = "q") final String name) {
    final Cnc cnc = cncService.getCncByName(name);

    return modelMapper.map(cnc, CncResponse.class);
  }

}
