package io.wisoft.iotplatform.device.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.wisoft.iotplatform.device.domain.Sensor;
import io.wisoft.iotplatform.device.service.SensorService;
import io.wisoft.iotplatform.device.service.dto.SensorDto.SensorRegister;
import io.wisoft.iotplatform.device.service.dto.SensorDto.SensorResponse;
import io.wisoft.iotplatform.device.service.dto.SensorDto.SensorUpdate;
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
@RequestMapping("/sensors")
@Api(value = "sensors")
@AllArgsConstructor
public class SensorController {

  private SensorService sensorService;
  private ModelMapper modelMapper;

  @ApiOperation(value = "센서 등록", notes = "센서를 시스템에 등록합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @ResponseStatus(CREATED)
  @PostMapping
  public SensorResponse register(@RequestBody @Valid final SensorRegister registerDto) {
    final Sensor newSensor = sensorService.register(registerDto);

    return modelMapper.map(newSensor, SensorResponse.class);
  }

  @ApiOperation(value = "센서 정보 갱신", notes = "시스템에 등록되어 있는 센서 정보를 갱신합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @ResponseStatus(OK)
  @PutMapping(value = "{id}")
  public SensorResponse update(@PathVariable final UUID id,
                               @RequestBody @Valid final SensorUpdate updateDto) {
    final Sensor updateSensor = sensorService.update(id, updateDto);

    return modelMapper.map(updateSensor, SensorResponse.class);
  }

  @ApiOperation(value = "센서 정보 삭제", notes = "시스템에서 특정 센서의 정보를 삭제합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(NO_CONTENT)
  @DeleteMapping(value = "{id}")
  public void remove(@PathVariable final UUID id) {
    sensorService.remove(id);
  }

  @ApiOperation(value = "특정 센서 조회", notes = "시스템에 등록되어 있는 특정 센서를 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping(value = "{id}")
  public SensorResponse getSensor(@PathVariable final UUID id) {
    final Sensor sensor = sensorService.getSensor(id);

    return modelMapper.map(sensor, SensorResponse.class);
  }

  @ApiOperation(value = "특정 센서 조회: Serial", notes = "시스템에 등록되어 있는 특정 센서를 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping(value = "search/serial")
  public SensorResponse getSensorBySerial(@RequestParam("q") final String serial) {
    final Sensor sensor = sensorService.getSensorBySerial(serial);

    return modelMapper.map(sensor, SensorResponse.class);
  }

  @ApiOperation(value = "전체 센서 조회", notes = "시스템에 등록되어 있는 전체 센서를 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping
  public Page<SensorResponse> getSensors(final Pageable pageable) {
    final Page<Sensor> sensors = sensorService.getSensors(pageable);
    final List<SensorResponse> content =
        sensors.getContent()
            .stream()
            .map(sensor -> modelMapper.map(sensor, SensorResponse.class))
            .collect(Collectors.toList());

    return new PageImpl<>(content, pageable, sensors.getTotalElements());
  }

  @ApiOperation(value = "특정 센서 조회: Name", notes = "시스템에 등록되어 있는 특정 센서를 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping("search/name")
  public SensorResponse getSensorByName(@RequestParam("q") final String name) {
    final Sensor sensor = sensorService.getSensorByName(name);

    return modelMapper.map(sensor, SensorResponse.class);
  }

  @ApiOperation(value = "디바이스 Id로 센서 조회: deviceId", notes = "시스템에 등록되어 있는 특정 센서를 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping("search/device-id")
  public List<SensorResponse> getSensorsByDeviceId(@PathVariable final UUID deviceId) {
    final List<Sensor> sensors = sensorService.getSensorsByDeviceId(deviceId);
    return sensors.stream()
        .map(sensor -> modelMapper.map(sensor, SensorResponse.class))
        .collect(Collectors.toList());
  }

}
