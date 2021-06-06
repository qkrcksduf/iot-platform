package io.wisoft.iotplatform.model.service;

import io.wisoft.iotplatform.model.service.dto.ModelDto.ModelRegister;
import io.wisoft.iotplatform.model.domain.Model;
import io.wisoft.iotplatform.model.exception.ModelDuplicatedException;
import io.wisoft.iotplatform.model.exception.ModelNotFoundException;
import io.wisoft.iotplatform.model.repository.ModelRepository;
import io.wisoft.iotplatform.model.domain.ModelType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static io.wisoft.iotplatform.model.service.dto.ModelDto.ModelUpdate;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class ModelService {

  private ModelRepository modelRepository;
  private ModelTypeService modelTypeService;
  private ModelMapper modelMapper;

  public Model register(final ModelRegister modelDto) {
    checkDuplicateName(modelDto.getName());

    final ModelType modelType = modelTypeService.getModelType(modelDto.getModelTypeId());
    final Model model = modelMapper.map(modelDto, Model.class);
    model.setModelType(modelType);

    return modelRepository.save(model);
  }

  public Model update(final UUID id, final ModelUpdate updateDto) {
    final ModelType modelType = modelTypeService.getModelType(updateDto.getModelTypeId());
    final Model model = getModel(id);

    model.setName(updateDto.getName());
    model.setModelType(modelType);
    model.setDescription(updateDto.getDescription());
    model.setUpdated(LocalDateTime.now());

    return modelRepository.save(model);
  }

  public void remove(final UUID id) {
    modelRepository.delete(getModel(id));
  }

  public Model getModel(final UUID id) {
    return modelRepository.findById(id).orElseThrow(() -> new ModelNotFoundException(String.valueOf(id)));
  }

  public Model getModelByName(final String name) {
    return modelRepository.findByName(name).orElseThrow(() -> new ModelNotFoundException(name));
  }

  public Page<Model> getModels(final Pageable pageable) {
    return modelRepository.findAll(pageable);
  }

  private void checkDuplicateName(final String name) {
    modelRepository.findByName(name).ifPresent(model -> {
      log.error("Model Duplicated Exception: {}", name);
      throw new ModelDuplicatedException(name);
    });
  }

}
