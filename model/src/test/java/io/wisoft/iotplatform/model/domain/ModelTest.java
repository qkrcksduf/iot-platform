package io.wisoft.iotplatform.model.domain;

import io.wisoft.iotplatform.model.repository.ModelRepository;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ModelTest {

  @Autowired
  private ModelRepository modelRepository;
  @Autowired
  private ModelTypeRepository modelTypeRepository;

  private Model model;
  private ModelType modelType;
  private ModelType modelTypeSave;

  @BeforeAll
  void setUp() {
    modelTypeSave = modelTypeRepository.save(ModelType.builder()
        .name("test-modelType")
        .description("test용 ModelType입니다.")
        .build());
    final Optional<ModelType> modelType = modelTypeRepository.findById(3L);

    model = modelRepository.save(Model.builder()
        .name("test-model")
        .modelType(modelType.get())
        .description("test용 Model입니다.")
        .build()
    );
  }

  @Test
  void Given_ModelType_When_ModelTypeSaveOne_Then_ReturnValueWillOne() {

    assertNotNull(model);
    assertThat(model).isNotNull();
  }

  @Test
  void Given_ModelType_When_ModelTypeFindByAll_Then_ReturnModelTypeList() {

    List<Model> models = modelRepository.findAll();
    assertThat(models).hasSize(3);
  }

  @Test
  void Given_ModelType_When_ModelTypeUpdateOne_Then_WillUpdateModelType() {

    model.setName("model-test");
    model.setUpdated(new Date());
    Model changeModel = modelRepository.save(model);

    assertThat(changeModel.getName()).isEqualTo("model-test");
  }

  @Test
  void Given_ModelType_When_ModelTypeDeleteOne_Then_ReturnNotEmpty() {

    modelRepository.delete(model);
    List<Model> models = modelRepository.findAll();
    assertThat(models).isNotEmpty();
  }

}