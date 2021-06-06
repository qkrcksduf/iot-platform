package io.wisoft.iotplatform.model.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.wisoft.iotplatform.model.service.ModelService;
import io.wisoft.iotplatform.model.domain.Model;
import io.wisoft.iotplatform.model.service.dto.ModelDto.ModelRegister;
import io.wisoft.iotplatform.model.service.dto.ModelDto.ModelUpdate;
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

import static io.wisoft.iotplatform.model.service.dto.ModelDto.ModelResponse;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/models")
@Api(value = "models")
@AllArgsConstructor
public class ModelController {

  private ModelService modelService;
  private ModelMapper modelMapper;

  @ApiOperation(value = "센서와 액추에이터 모델 등록", notes = "센서와 액추에이터 모델을 시스템에 등록합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @ResponseStatus(CREATED)
  @PostMapping
  public ModelResponse register(@RequestBody @Valid final ModelRegister registerDto) {
    final Model newModel = modelService.register(registerDto);

    return modelMapper.map(newModel, ModelResponse.class);
  }

  @ApiOperation(value = "모델 정보 갱신", notes = "시스템에 등록되어 있는 모델 정보를 갱신합니다.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 400, message = "Bad Request")
  })
  @ResponseStatus(OK)
  @PutMapping(value = "{id}")
  public ModelResponse update(@PathVariable final UUID id,
                              @RequestBody @Valid final ModelUpdate updateDto) {
    final Model updateModel = modelService.update(id, updateDto);

    return modelMapper.map(updateModel, ModelResponse.class);
  }

  @ApiOperation(value = "모델 정보 삭제", notes = "시스템에서 특정 모델의 정보를 삭제합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(NO_CONTENT)
  @DeleteMapping(value = "{id}")
  public void remove(@PathVariable final UUID id) {
    modelService.remove(id);
  }

  @ApiOperation(value = "특정 모델 조회", notes = "시스템에 등록되어 있는 특정 모델을 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping(value = "{id}")
  public ModelResponse getModel(@PathVariable final UUID id) {
    final Model model = modelService.getModel(id);

    return modelMapper.map(model, ModelResponse.class);
  }

  @ApiOperation(value = "전체 모델 조회", notes = "시스템에 등록되어 있는 전체 모델을 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping
  public Page<ModelResponse> getModels(final Pageable pageable) {
    final Page<Model> models = modelService.getModels(pageable);
    final List<ModelResponse> content =
        models.getContent()
            .stream()
            .map(model -> modelMapper.map(model, ModelResponse.class))
            .collect(Collectors.toList());

    return new PageImpl<>(content, pageable, models.getTotalElements());
  }

  @ApiOperation(value = "특정 모델 조회: Name", notes = "시스템에 등록되어 있는 특정 모델을 조회합니다.")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @ResponseStatus(OK)
  @GetMapping("search/name")
  public ModelResponse getModelByName(@RequestParam("q") final String name) {
    final Model model = modelService.getModelByName(name);

    return modelMapper.map(model, ModelResponse.class);
  }

}
