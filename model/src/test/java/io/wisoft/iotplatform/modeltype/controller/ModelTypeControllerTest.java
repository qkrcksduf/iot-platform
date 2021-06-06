package io.wisoft.iotplatform.modeltype.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.wisoft.iotplatform.model.domain.ModelType;
import io.wisoft.iotplatform.model.service.ModelTypeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static io.wisoft.iotplatform.fixture.Fixtures.registerModelTypeDto;
import static io.wisoft.iotplatform.model.service.dto.ModelTypeDto.*;
import static org.hamcrest.core.Is.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ModelTypeControllerTest {

  private static final String API_V1_BASE_URI = "/model-types/";

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private ModelTypeService modelTypeService;

  @Autowired
  private ObjectMapper objectMapper;

  private ModelTypeRegister registerModelTypeDto;
  private MockMvc mockMvc;

  @BeforeAll
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    this.registerModelTypeDto = registerModelTypeDto();
  }

  @Test
  public void register() throws Exception {
    // Given & When
    final ResultActions resultActions = mockMvc.perform(
        post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(this.registerModelTypeDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isCreated());
    resultActions.andExpect(jsonPath("$.name", is("testModelType")));
  }

  @Test
  public void registerBadRequest() throws Exception {
    // Given
    this.registerModelTypeDto.setName(" ");

    // When
    final ResultActions resultActions = mockMvc.perform(
        post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(this.registerModelTypeDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("ModelTypeController-E001")));
  }

  @Test
  public void registerDuplicatedName() throws Exception {
    // Given
    mockMvc.perform(post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(this.registerModelTypeDto)));

    final ModelTypeRegister newRegisterModelTypeDto = registerModelTypeDto();

    // When
    final ResultActions resultActions = mockMvc.perform(
        post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newRegisterModelTypeDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("ModelTypeController-X001")));
  }

  @Test
  public void update() throws Exception {
    // Given
    final ModelType modelType = modelTypeService.register(this.registerModelTypeDto);

    final ModelTypeUpdate updateDto = new ModelTypeUpdate();
    updateDto.setName("modelTypeName");
    updateDto.setDescription("블라블라블라");

    // When
    final ResultActions resultActions = mockMvc.perform(
        put(API_V1_BASE_URI + modelType.getId()).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.name", is("modelTypeName")));
  }

  @Test
  public void updateBadRequest() throws Exception {
    // Given
    final ModelType modelType = modelTypeService.register(this.registerModelTypeDto);

    final ModelTypeUpdate updateDto = new ModelTypeUpdate();
    updateDto.setName(" ");

    // When
    final ResultActions resultActions = mockMvc.perform(
        put(API_V1_BASE_URI + modelType.getId()).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("ModelTypeController-E002")));
  }

  @Test
  public void remove() throws Exception {
    // Given
    final ModelType modelType = modelTypeService.register(this.registerModelTypeDto);

    // When
    final ResultActions resultActions =
        mockMvc.perform(delete(API_V1_BASE_URI + modelType.getId()));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isNoContent());
  }

  @Test
  public void removeBadRequest() throws Exception {
    // Given
    final String modelTypeId = "1000";

    // When
    final ResultActions resultActions = mockMvc.perform(delete(API_V1_BASE_URI + modelTypeId));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
  }

  @Test
  public void getModelType() throws Exception {
    // Given
    final ModelType modelType = modelTypeService.register(this.registerModelTypeDto);

    // When
    final ResultActions resultActions = mockMvc.perform(get(API_V1_BASE_URI + modelType.getId()));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.name", is("testModelType")));
  }

  @Test
  public void getModelTypeByName() throws Exception {
    // Given
    final ModelType modelType = modelTypeService.register(this.registerModelTypeDto);

    // When
    final String uri = API_V1_BASE_URI + "?name=" + modelType.getName();
    final ResultActions resultActions = mockMvc.perform(get(uri));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.name", is("testModelType")));
  }

  @Test
  public void getModelTypeByNameNotFound() throws Exception {
    // Given
    modelTypeService.register(this.registerModelTypeDto);
    final String modelTypeName = "testModelType10";

    // When
    final String uri = API_V1_BASE_URI + "?name=" + modelTypeName;
    final ResultActions resultActions = mockMvc.perform(get(uri));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("ModelTypeController-X002")));
  }

  @Test
  public void getModelTypes() throws Exception {
    // Given
    this.registerModelTypeDto.setName("testModelType1");
    modelTypeService.register(this.registerModelTypeDto);

    // When
    final ResultActions result = mockMvc.perform(get(API_V1_BASE_URI));

    // Then
    result.andDo(print());
    result.andExpect(status().isOk());
  }

}