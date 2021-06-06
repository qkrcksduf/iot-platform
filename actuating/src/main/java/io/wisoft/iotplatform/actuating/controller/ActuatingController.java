package io.wisoft.iotplatform.actuating.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.wisoft.iotplatform.actuating.domain.Actuating;
import io.wisoft.iotplatform.actuating.service.ActuatingService;
import io.wisoft.iotplatform.actuating.service.dto.ActuatingDto;
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
@RequestMapping("/actuatings")
@Api(value = "actuatings")
@AllArgsConstructor
public class ActuatingController {

  private ActuatingService actuatingService;
  private ModelMapper modelMapper;

  @ApiOperation(value = "액추에이터 수집 값 등록", notes = "액추에이터 수집 값을 시스템에 등록합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @ResponseStatus(CREATED)
  @PostMapping
  public ActuatingDto.ActuatingResponse register(@RequestBody @Valid final ActuatingDto.ActuatingRegister registerDto) {
    final Actuating newActuating = actuatingService.register(registerDto);

    return modelMapper.map(newActuating, ActuatingDto.ActuatingResponse.class);
  }

  @ApiOperation(value = "액츄에이터 정보 갱신", notes = "액츄에이터 정보를 갱신합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @ResponseStatus(OK)
  @PutMapping("{id}")
  public ActuatingDto.ActuatingResponse update(@PathVariable final UUID id,
                                               @RequestBody @Valid final ActuatingDto.ActuatingUpdate updateDto) {
    final Actuating updateActuator = actuatingService.update(id, updateDto);

    return modelMapper.map(updateActuator, ActuatingDto.ActuatingResponse.class);
  }

  @ApiOperation(value = "액추에이터 정보 삭제", notes = "특정 액추에이터의 정보를 삭제합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(NO_CONTENT)
  @DeleteMapping("{id}")
  public void remove(@PathVariable final UUID id) {
    actuatingService.remove(id);
  }

  @ApiOperation(value = "액추에이터 수집 값 조회", notes = "액추에이터 수집 값을 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping("{id}")
  public ActuatingDto.ActuatingResponse getActuatingValue(@PathVariable final UUID id) {
    final Actuating actuating = actuatingService.getActuatingValue(id);

    return modelMapper.map(actuating, ActuatingDto.ActuatingResponse.class);
  }

  @ApiOperation(value = "전체 액추에이터 수집 값 조회", notes = "전체 액추에이터 수집 값을 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping
  public Page<ActuatingDto.ActuatingResponse> getActuatingValues(final Pageable pageable) {
    final Page<Actuating> deviceActuators = actuatingService.getActuatingValues(pageable);
    final List<ActuatingDto.ActuatingResponse> content = deviceActuators.getContent()
        .stream()
        .map(deviceActuator -> modelMapper.map(deviceActuator, ActuatingDto.ActuatingResponse.class))
        .collect(Collectors.toList());

    return new PageImpl<>(content, pageable, deviceActuators.getTotalElements());
  }

}
