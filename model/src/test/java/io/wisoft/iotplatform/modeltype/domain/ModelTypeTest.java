package io.wisoft.iotplatform.modeltype.domain;

import io.wisoft.iotplatform.model.domain.ModelType;
import io.wisoft.iotplatform.model.repository.ModelTypeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ModelTypeTest {

  @Autowired
  private ModelTypeRepository modelTypeRepository;

  private ModelType modelType;

  @BeforeAll
  void setUp(){
    modelType = modelTypeRepository.save(ModelType.builder()
        .name("test-modelType")
        .description("test용 ModelType입니다.")
        .build());
  }

  @Test
  void Given_ModelType_When_ModelTypeSaveOne_Then_ReturnValueWillOne() {

    assertNotNull(modelType);
    assertThat(modelType).isNotNull();
  }

  @Test
  void Given_ModelType_When_ModelTypeFindByAll_Then_ReturnModelTypeList() {

    List<ModelType> modelTypes = modelTypeRepository.findAll();
    assertThat(modelTypes).hasSize(3);
  }

  @Test
  void Given_ModelType_When_ModelTypeUpdateOne_Then_WillUpdateModelType() {

    modelType.setName("modelType-test");
    modelType.setUpdated(new Date());
    ModelType changeModelType = modelTypeRepository.save(modelType);

    assertThat(changeModelType.getName()).isEqualTo("modelType-test");
  }

  @Test
  void Given_ModelType_When_ModelTypeDeleteOne_Then_ReturnNotEmpty() {

    modelTypeRepository.delete(modelType);
    List<ModelType> modelTypes = modelTypeRepository.findAll();
    assertThat(modelTypes).isNotEmpty();
  }

}