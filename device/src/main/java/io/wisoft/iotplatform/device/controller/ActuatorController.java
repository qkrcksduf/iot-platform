package io.wisoft.iotplatform.device.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.wisoft.iotplatform.device.domain.Actuator;
import io.wisoft.iotplatform.device.service.ActuatorService;
import io.wisoft.iotplatform.device.service.dto.ActuatorDto.ActuatorRegister;
import io.wisoft.iotplatform.device.service.dto.ActuatorDto.ActuatorResponse;
import io.wisoft.iotplatform.device.service.dto.ActuatorDto.ActuatorUpdate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/actuators")
@Api(value = "actuators")
@AllArgsConstructor
@Slf4j
public class ActuatorController {

  private ActuatorService actuatorService;
  private ModelMapper modelMapper;

  @ApiOperation(value = "액츄에이터 등록", notes = "액츄에이터를 시스템에 등록합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @ResponseStatus(CREATED)
  @PostMapping
  public ActuatorResponse register(@RequestBody @Valid final ActuatorRegister registerDto) {
    log.debug("actuatorController register test");
    final Actuator newActuator = actuatorService.register(registerDto);
    return modelMapper.map(newActuator, ActuatorResponse.class);
  }

  @ApiOperation(value = "액츄에이터 정보 갱신", notes = "시스템에 등록되어 있는 액츄에이터 정보를 갱신합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @ResponseStatus(OK)
  @PutMapping(value = "{id}")
  public ActuatorResponse update(@PathVariable final UUID id, @RequestBody @Valid final ActuatorUpdate updateDto) {
    final Actuator updateActuator = actuatorService.update(id, updateDto);
    return modelMapper.map(updateActuator, ActuatorResponse.class);
  }

  @ApiOperation(value = "액츄에이터 정보 삭제", notes = "시스템에서 특정 액츄에이터의 정보를 삭제합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @DeleteMapping(value = "{id}")
  public void remove(@PathVariable final UUID id) {
    actuatorService.remove(id);
  }

  @ApiOperation(value = "특정 액츄에이터 조회", notes = "시스템에 등록되어 있는 특정 액츄에이터를 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping(value = "{id}")
  public ActuatorResponse getActuator(@PathVariable final UUID id) {
    final Actuator actuator = actuatorService.getActuator(id);

    return modelMapper.map(actuator, ActuatorResponse.class);
  }

  @ApiOperation(value = "특정 액츄에이터 조회", notes = "시스템에 등록되어 있는 특정 액츄에이터를 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping(value = "/search/serial")
  public ActuatorResponse getActuatorBySerial(@RequestParam("q") final String serial) {
    final Actuator actuator = actuatorService.getActuatorBySerial(serial);

    return modelMapper.map(actuator, ActuatorResponse.class);
  }

  @ApiOperation(value = "전체 액츄에이터 조회", notes = "시스템에 등록되어 있는 전체 액츄에이터를 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping
  public Page<ActuatorResponse> getActuators(final Pageable pageable) {
    final Page<Actuator> actuators = actuatorService.getActuators(pageable);
    final List<ActuatorResponse> content =
        actuators.getContent()
            .stream()
            .map(actuator -> modelMapper.map(actuator, ActuatorResponse.class))
            .collect(Collectors.toList());

    return new PageImpl<>(content, pageable, actuators.getTotalElements());
  }

  @ApiOperation(value = "특정 액츄에이터 조회: Name", notes = "시스템에 등록되어 있는 특정 액츄에이터를 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping("search/name")
  public ActuatorResponse getActuatorByName(@RequestParam("q") final String name) {
    final Actuator actuator = actuatorService.getActuatorByName(name);

    return modelMapper.map(actuator, ActuatorResponse.class);
  }

}
