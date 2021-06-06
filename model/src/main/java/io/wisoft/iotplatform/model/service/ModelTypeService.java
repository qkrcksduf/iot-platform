package io.wisoft.iotplatform.model.service;

import io.wisoft.iotplatform.model.domain.ModelType;
import io.wisoft.iotplatform.model.exception.ModelTypeDuplicatedException;
import io.wisoft.iotplatform.model.exception.ModelTypeNotFoundException;
import io.wisoft.iotplatform.model.repository.ModelTypeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static io.wisoft.iotplatform.model.service.dto.ModelTypeDto.ModelTypeRegister;
import static io.wisoft.iotplatform.model.service.dto.ModelTypeDto.ModelTypeUpdate;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class ModelTypeService {

  private ModelTypeRepository modelTypeRepository;
  private ModelMapper modelMapper;

  public ModelType register(final ModelTypeRegister registerDto) {
    checkDuplicateName(registerDto.getName());

    final ModelType modelType = modelMapper.map(registerDto, ModelType.class);

    return modelTypeRepository.save(modelType);
  }

  public ModelType update(final UUID id, final ModelTypeUpdate updateDto) {
    final ModelType modelType = getModelType(id);
    modelType.setName(updateDto.getName());
    modelType.setDescription(updateDto.getDescription());
    modelType.setUpdated(LocalDateTime.now());

    return modelTypeRepository.save(modelType);
  }

  public void remove(final UUID id) {
    modelTypeRepository.delete(getModelType(id));
  }

  public ModelType getModelType(final UUID id) {
    return modelTypeRepository.findById(id).orElseThrow(() -> new ModelTypeNotFoundException(String.valueOf(id)));
  }

  public ModelType getModelTypeByName(final String name) {
    return modelTypeRepository.findByName(name).orElseThrow(() -> new ModelTypeNotFoundException(name));
  }

  public Page<ModelType> getModelTypes(final Pageable pageable) {
    return modelTypeRepository.findAll(pageable);
  }

  private void checkDuplicateName(final String name) {
    modelTypeRepository.findByName(name).ifPresent(modelType -> {
      log.error("ModelType Duplicated Exception: {}", name);
      throw new ModelTypeDuplicatedException(name);
    });
  }

}
