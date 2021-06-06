package io.wisoft.iotplatform.modeltype.service;

import io.wisoft.iotplatform.model.domain.ModelType;
import io.wisoft.iotplatform.model.service.ModelTypeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static io.wisoft.iotplatform.fixture.Fixtures.registerModelTypeDto;
import static io.wisoft.iotplatform.model.service.dto.ModelTypeDto.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ModelTypeServiceTest {

  @Autowired
  private ModelTypeService modelTypeService;

  private ModelTypeRegister registerModelTypeDto;

  @BeforeAll
  public void setup() {
    this.registerModelTypeDto = registerModelTypeDto();
  }

  @Test
  public void equals() throws Exception {
    // Given
    final ModelType modelType = modelTypeService.register(this.registerModelTypeDto);

    // When & Then
    assertTrue(modelType.equals(modelType), "Class equal to itself.");
  }

  @Test
  public void isHashcodeConsistent() throws Exception {
    // Given
    final ModelType modelType = modelTypeService.register(this.registerModelTypeDto);

    // When& Then
    assertEquals(modelType.hashCode(), modelType.hashCode());
  }

}