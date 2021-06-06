package io.wisoft.iotplatform.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.wisoft.iotplatform.account.domain.Account;
import io.wisoft.iotplatform.account.service.AccountService;
import io.wisoft.iotplatform.account.service.dto.AccountDto.AccountUpdate;
import io.wisoft.iotplatform.account.service.dto.AccountDto.AccountRegister;
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

import static org.hamcrest.core.Is.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountControllerTest {

  private static final String API_V1_BASE_URI = "/accounts/";

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private AccountService accountService;

  @Autowired
  private ObjectMapper objectMapper;

  private AccountRegister registerAccountDto;
  private MockMvc mockMvc;

  @BeforeAll
  public void setup() {
    this.mockMvc =
        MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    this.registerAccountDto =new AccountRegister(
        "tjqh55", "seobomin",
        "test123", "tjqh55@gmail.com",
        "ROLE_USER", "석사과정");
  }

  @Test
  public void register() throws Exception {
    // Given & When
    final ResultActions resultActions = mockMvc.perform(
        post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(this.registerAccountDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isCreated());
    resultActions.andExpect(jsonPath("$.username", is("tjqh55")));
  }

  @Test
  public void registerBadRequest() throws Exception {
    // Given
    this.registerAccountDto.setUsername(" ");
    this.registerAccountDto.setPassword("1234");

    // When
    final ResultActions resultActions = mockMvc.perform(
        post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(this.registerAccountDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("AccountController-E001")));
  }

  @Test
  public void registerDuplicatedUsername() throws Exception {
    // Given
    mockMvc.perform(post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(
            this.registerAccountDto)));

    final AccountRegister newRegisterAccountDto = new AccountRegister();

    // When
    final ResultActions resultActions = mockMvc.perform(
        post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newRegisterAccountDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("AccountController-E001")));
  }

  @Test
  public void registerDuplicatedEmail() throws Exception {
    // Given
    mockMvc.perform(post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(
            this.registerAccountDto)));

    final AccountRegister newRegisterAccountDto = new AccountRegister();
    newRegisterAccountDto.setUsername("testUser2");

    // When
    final ResultActions resultActions = mockMvc.perform(
        post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newRegisterAccountDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("AccountController-E001")));
  }

  @Test
  public void update() throws Exception {
    // Given
    final Account account = accountService.register(this.registerAccountDto);

    final AccountUpdate updateDto = new AccountUpdate();
    updateDto.setName("testUser2");
    updateDto.setPassword("passwords");
    updateDto.setEmail("testuser2@hanbat.ac.kr");
    updateDto.setDescription("블라블라블라");

    // When
    final ResultActions resultActions = mockMvc.perform(
        put(API_V1_BASE_URI + account.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.email", is("testuser2@hanbat.ac.kr")));
  }

  @Test
  public void updateBadRequest() throws Exception {
    // Given
    final Account account = accountService.register(this.registerAccountDto);

    final AccountUpdate updateDto = new AccountUpdate();
    updateDto.setEmail(" ");
    updateDto.setPassword(" ");

    // When
    final ResultActions resultActions = mockMvc.perform(
        put(API_V1_BASE_URI + account.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("AccountController-E002")));
  }

  @Test
  public void remove() throws Exception {
    // Given
    final Account account = accountService.register(this.registerAccountDto);

    // When
    final ResultActions resultActions = mockMvc.perform(
        delete(API_V1_BASE_URI + account.getId()));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isNoContent());
  }

  @Test
  public void removeBadRequest() throws Exception {
    // Given
    final Account account = accountService.register(this.registerAccountDto);
    final String accountId = "1000";

    // When
    final ResultActions resultActions = mockMvc.perform(
        delete(API_V1_BASE_URI + accountId));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isNotFound());
  }

  @Test
  public void getAccount() throws Exception {
    // Given
    final Account account = accountService.register(this.registerAccountDto);

    // When
    final ResultActions resultActions = mockMvc.perform(get(API_V1_BASE_URI + account.getId()));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.username", is("tjqh55")));
  }

  @Test
  public void getAccountByUsername() throws Exception {
    // Given
    final Account account = accountService.register(this.registerAccountDto);

    // When
    final String uri = API_V1_BASE_URI + "?username=" + account.getUsername();
    final ResultActions resultActions = mockMvc.perform(get(uri));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.username", is("tjqh55")));
  }

  @Test
  public void getAccountByUsernameNotFound() throws Exception {
    // Given
    accountService.register(this.registerAccountDto);
    final String username = "testUser2";

    // When
    final String uri = API_V1_BASE_URI + "?username=" + username;
    final ResultActions resultActions = mockMvc.perform(get(uri));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", is("AccountController-X002")));
  }

  @Test
  public void getAccounts() throws Exception {
    // Given
    accountService.register(this.registerAccountDto);

    // When
    final ResultActions resultActions = mockMvc.perform(get(API_V1_BASE_URI));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
  }

  @Test
  public void isDuplicatedUsername() throws Exception {
    // Given
    final Account account = accountService.register(this.registerAccountDto);

    // When
    final String uri = API_V1_BASE_URI + "duplicate/byUsername?username=" + account.getUsername();
    final ResultActions resultActions = mockMvc.perform(get(uri));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.result", is(Boolean.TRUE)));
  }

}