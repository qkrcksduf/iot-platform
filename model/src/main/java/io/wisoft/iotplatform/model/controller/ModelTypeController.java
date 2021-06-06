package io.wisoft.iotplatform.model.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.wisoft.iotplatform.model.service.ModelTypeService;
import io.wisoft.iotplatform.model.domain.ModelType;
import io.wisoft.iotplatform.model.service.dto.ModelTypeDto.ModelTypeRegister;
import io.wisoft.iotplatform.model.service.dto.ModelTypeDto.ModelTypeUpdate;
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

import static io.wisoft.iotplatform.model.service.dto.ModelTypeDto.ModelTypeResponse;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/model-types")
@Api(value = "model-types")
@AllArgsConstructor
public class ModelTypeController {

  private ModelTypeService modelTypeService;
  private ModelMapper modelMapper;

  @ApiOperation(value = "모델 타입 등록", notes = "모델 타입을 시스템에 등록합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @ResponseStatus(CREATED)
  @PostMapping
  public ModelTypeResponse register(@RequestBody @Valid final ModelTypeRegister registerDto) {
    final ModelType newModelType = modelTypeService.register(registerDto);

    return modelMapper.map(newModelType, ModelTypeResponse.class);
  }

  @ApiOperation(value = "모델 타입 정보 갱신", notes = "시스템에 등록되어 있는 모델 타입 정보를 갱신합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @ResponseStatus(OK)
  @PutMapping(value = "{id}")
  public ModelTypeResponse update(@PathVariable final UUID id, @RequestBody @Valid final ModelTypeUpdate updateDto) {
    final ModelType updateModelType = modelTypeService.update(id, updateDto);

    return modelMapper.map(updateModelType, ModelTypeResponse.class);
  }

  @ApiOperation(value = "모델 정보 삭제", notes = "시스템에서 특정 모델의 정보를 삭제합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(NO_CONTENT)
  @DeleteMapping(value = "{id}")
  public void remove(@PathVariable final UUID id) {
    modelTypeService.remove(id);
  }

  @ApiOperation(value = "특정 모델 타입 조회", notes = "시스템에 등록되어 있는 특정 모델 타입을 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping(value = "{id}")
  public ModelTypeResponse getModelType(@PathVariable final UUID id) {
    final ModelType modelType = modelTypeService.getModelType(id);

    return modelMapper.map(modelType, ModelTypeResponse.class);
  }

  @ApiOperation(value = "전체 모델 타입 조회", notes = "시스템에 등록되어 있는 전체 모델 타입을 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping
  public Page<ModelTypeResponse> getModelTypes(final Pageable pageable) {
    final Page<ModelType> modelTypes = modelTypeService.getModelTypes(pageable);
    final List<ModelTypeResponse> content =
        modelTypes.getContent()
            .stream()
            .map(modelType -> modelMapper.map(modelType, ModelTypeResponse.class))
            .collect(Collectors.toList());

    return new PageImpl<>(content, pageable, modelTypes.getTotalElements());
  }

  @ApiOperation(value = "특정 모델 조회: Name", notes = "시스템에 등록되어 있는 특정 모델을 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping("search/name")
  public ModelTypeResponse getModelTypeByName(@RequestParam("q") final String name) {
    final ModelType modelType = modelTypeService.getModelTypeByName(name);

    return modelMapper.map(modelType, ModelTypeResponse.class);
  }

}
