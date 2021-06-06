package io.wisoft.iotplatform.model.service;

import io.wisoft.iotplatform.model.domain.Model;
import io.wisoft.iotplatform.model.domain.ModelType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static io.wisoft.iotplatform.fixture.Fixtures.registerModelDto;
import static io.wisoft.iotplatform.fixture.Fixtures.registerModelTypeDto;
import static io.wisoft.iotplatform.model.service.dto.ModelDto.*;
import static io.wisoft.iotplatform.model.service.dto.ModelTypeDto.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ModelServiceTest {

  @Autowired
  private ModelService modelService;

  @Autowired
  private ModelTypeService modelTypeService;

  private ModelTypeRegister registerModelTypeDto;
  private ModelRegister registerModelDto;

  @BeforeAll
  public void setup() {
    this.registerModelTypeDto = registerModelTypeDto();
    this.registerModelDto = registerModelDto();
  }

  @Test
  public void equals() throws Exception {
    // Given
    final ModelType modelType = modelTypeService.register(this.registerModelTypeDto);
    this.registerModelDto.setModelTypeId(modelType.getId());

    final Model model = modelService.register(this.registerModelDto);

    // When & Then
    assertTrue("Class equals to itself.", model.equals(model));
  }

  @Test
  public void isHashcodeConsistent() throws Exception {
    // Given
    final ModelType modelType = modelTypeService.register(this.registerModelTypeDto);
    this.registerModelDto.setModelTypeId(modelType.getId());

    final Model model = modelService.register(this.registerModelDto);

    // When & Then
    assertEquals(model.hashCode(), model.hashCode());
  }

}