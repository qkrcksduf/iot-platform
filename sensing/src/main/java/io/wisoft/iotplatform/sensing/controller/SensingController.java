package io.wisoft.iotplatform.sensing.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.wisoft.iotplatform.sensing.domain.Sensing;
import io.wisoft.iotplatform.sensing.service.SensingService;
import io.wisoft.iotplatform.sensing.service.dto.SensingDto.SensingRegister;
import io.wisoft.iotplatform.sensing.service.dto.SensingDto.SensingResponse;
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
@RequestMapping("/sensings")
@Api(value = "sensings")
@AllArgsConstructor
public class SensingController {

  private SensingService sensingService;
  private ModelMapper modelMapper;

  @ApiOperation(value = "센서 수집 값 등록", notes = "센서 수집 값을 시스템에 등록합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @ResponseStatus(CREATED)
  @PostMapping
  public SensingResponse register(@RequestBody @Valid final SensingRegister registerDto) {
    final Sensing newDeviceSensor = sensingService.register(registerDto);

    return modelMapper.map(newDeviceSensor, SensingResponse.class);
  }

  @ApiOperation(value = "센싱 수집 값 정보 삭제", notes = "센싱 수집 값 정보를 삭제합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(NO_CONTENT)
  @DeleteMapping(value = "{id}")
  public void remove(@PathVariable final UUID id) {
    sensingService.remove(id);
  }

  @ApiOperation(value = "센서 수집 값 조회", notes = "센서 수집 값을 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping(value = "{id}")
  public SensingResponse getSensing(@PathVariable final UUID id) {
    final Sensing sensing = sensingService.getSensing(id);

    return modelMapper.map(sensing, SensingResponse.class);
  }

  @ApiOperation(value = "특정 센서의 수집 값 조회", notes = "특정 센서의 수집 값을 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping("search/sensor-id")
  public List<SensingResponse> getSensingBySensorId(@RequestParam final UUID sensorId) {
    final List<Sensing> sensings = sensingService.getSensingsBySensorId(sensorId);

    return sensings.stream()
        .map(sensing -> modelMapper.map(sensing, SensingResponse.class))
        .collect(Collectors.toList());
  }

  @ApiOperation(value = "전체 센서 수집 값 조회", notes = "전체 센서의 수집 값을 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping
  public Page<SensingResponse> getSensings(final Pageable pageable) {
    final Page<Sensing> sensingValues = sensingService.getSensings(pageable);
    final List<SensingResponse> content =
        sensingValues.getContent()
            .stream()
            .map(sensingValue -> modelMapper.map(sensingValue, SensingResponse.class))
            .collect(Collectors.toList());

    return new PageImpl<>(content, pageable, sensingValues.getTotalElements());
  }

}
