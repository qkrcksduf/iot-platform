package io.wisoft.iotplatform.device.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.wisoft.iotplatform.device.domain.Actuator;
import io.wisoft.iotplatform.device.domain.Device;
import io.wisoft.iotplatform.device.domain.Sensor;
import io.wisoft.iotplatform.device.service.DeviceService;
import io.wisoft.iotplatform.device.service.dto.ActuatorDto.ActuatorResponse;
import io.wisoft.iotplatform.device.service.dto.DeviceDto;
import io.wisoft.iotplatform.device.service.dto.DeviceDto.DeviceInitialize;
import io.wisoft.iotplatform.device.service.dto.DeviceDto.DeviceResponse;
import io.wisoft.iotplatform.device.service.dto.SensorDto.SensorResponse;
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
@AllArgsConstructor
@RequestMapping("/devices")
@Api(value = "devices")
public class DeviceController {

  private DeviceService deviceService;
  private ModelMapper modelMapper;

  @ApiOperation(value = "디바이스 등록", notes = "디바이스를 시스템에 등록합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @ResponseStatus(CREATED)
  @PostMapping
  public DeviceResponse register(@RequestBody @Valid final DeviceDto.DeviceRegister registerDto) {
    final Device newDevice = deviceService.register(registerDto);

    return modelMapper.map(newDevice, DeviceResponse.class);
  }

  @ApiOperation(value = "디바이스 정보 갱신", notes = "시스템에 등록되어 있는 디바이스 정보를 갱신합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @ResponseStatus(OK)
  @PutMapping(value = "{id}")
  public DeviceResponse update(@PathVariable final UUID id,
                               @RequestBody @Valid final DeviceDto.DeviceUpdate updateDto) {
    final Device updateDevice = deviceService.update(id, updateDto);

    return modelMapper.map(updateDevice, DeviceResponse.class);
  }

  @ApiOperation(value = "디바이스 초기 정보 설정", notes = "디바이스의 초기 정보를 설정합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @ResponseStatus(OK)
  @PutMapping(value = "{id}/initialization")
  public DeviceResponse initialize(@PathVariable final UUID id,
                                   @RequestBody @Valid final DeviceInitialize dto) {
    final Device updateDevice = deviceService.initialize(id, dto);

    return modelMapper.map(updateDevice, DeviceResponse.class);
  }

  @ApiOperation(value = "디바이스 정보 삭제", notes = "시스템에서 특정 디바이스의 정보를 삭제합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(NO_CONTENT)
  @DeleteMapping(value = "{id}")
  public void remove(@PathVariable final UUID id) {
    deviceService.remove(id);
  }

  @ApiOperation(value = "특정 디바이스 조회", notes = "시스템에 등록되어 있는 특정 디바이스를 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping(value = "{id}")
  public DeviceResponse getDevice(@PathVariable final UUID id) {
    final Device device = deviceService.getDevice(id);

    return modelMapper.map(device, DeviceResponse.class);
  }

  @ApiOperation(value = "특정 디바이스 조회: Serial",
      notes = "'Serial Number'로 시스템에 등록되어 있는 특정 디바이스를 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping(value = "search/serial")
  public DeviceResponse getDeviceBySerial(@RequestParam("q") final String serial) {
    final Device device = deviceService.getDeviceBySerial(serial);

    return modelMapper.map(device, DeviceResponse.class);
  }

  @ApiOperation(value = "특정 디바이스 조회: Status",
      notes = "'Status'로 시스템에 등록되어 있는 특정 디바이스를 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping(value = "search/status")
  public List<DeviceResponse> getDeviceByStatus(@RequestParam("q") final String status) {
    final List<Device> devices = deviceService.getDeviceByStatus(status);
    return devices.stream()
        .map(device -> modelMapper.map(device, DeviceResponse.class))
        .collect(toList());
  }

  @ApiOperation(value = "특정 디바이스 조회: Actuator Id", notes = "'Actuator Id'로 시스템에 등록되어 있는 특정 디바이스를 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping(value = "search/actuator-id")
  public DeviceResponse getDeviceByActuatorId(@RequestParam("q") final UUID actuatorId) {
    final Device device = deviceService.getDeviceByActuatorId(actuatorId);
    return modelMapper.map(device, DeviceResponse.class);
  }

  @ApiOperation(value = "특정 디바이스와 연관된 센서 조회", notes = "특정 디바이스에 연결되어 있는 센서 목록을 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping(value = "{id}/sensors")
  public List<SensorResponse> getSensorsByDeviceId(@PathVariable final UUID id) {
    final List<Sensor> sensors = deviceService.getSensorsByDeviceId(id);
    return sensors.stream()
        .map(sensor -> modelMapper.map(sensor, SensorResponse.class))
        .collect(toList());
  }

  @ApiOperation(value = "특정 디바이스와 연관된 액추에이터 조회", notes = "특정 디바이스에 연결되어 있는 액추에이터 목록을 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping(value = "{id}/actuators")
  public List<ActuatorResponse> getActuatorsByDeviceId(@PathVariable final UUID id) {
    final List<Actuator> actuators = deviceService.getActuatorsByDeviceId(id);
    return actuators.stream()
        .map(actuator -> modelMapper.map(actuator, ActuatorResponse.class))
        .collect(toList());
  }

  @ApiOperation(value = "전체 디바이스 조회", notes = "시스템에 등록되어 있는 전체 디바이스를 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping
  public Page<DeviceResponse> getDevices(final Pageable pageable) {
    final Page<Device> devices = deviceService.getDevices(pageable);
    final List<DeviceResponse> content =
        devices.getContent()
            .stream()
            .map(device -> modelMapper.map(device, DeviceResponse.class))
            .collect(toList());

    return new PageImpl<>(content, pageable, devices.getTotalElements());
  }

  @ApiOperation(value = "특정 디바이스 조회: Name", notes = "시스템에 등록되어 있는 특정 디바이스를 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping("search/name")
  public DeviceResponse getDeviceByName(@RequestParam("q") final String name) {
    final Device device = deviceService.getDeviceName(name);

    return modelMapper.map(device, DeviceResponse.class);
  }

  @ApiOperation(value = "특정 디바이스 조회: CncId", notes = "'CnC Id'를 활용하여 시스템에 등록되어 있는 특정 디바이스를 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping("search/cnc-id")
  public List<DeviceResponse> getDevicesByCncId(@RequestParam("q") final UUID cncId) {
    final List<Device> devices = deviceService.getDeviceByCncId(cncId);

    return devices.stream()
                  .map(device -> modelMapper.map(device, DeviceResponse.class))
                  .collect(toList());
  }

}
