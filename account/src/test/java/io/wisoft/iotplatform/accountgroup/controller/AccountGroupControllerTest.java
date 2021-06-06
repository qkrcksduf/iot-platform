package io.wisoft.iotplatform.accountgroup.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.wisoft.iotplatform.account.domain.Account;
import io.wisoft.iotplatform.account.service.AccountService;
import io.wisoft.iotplatform.accountgroup.domain.AccountGroup;
import io.wisoft.iotplatform.accountgroup.service.AccountGroupService;
import io.wisoft.iotplatform.group.domain.Group;
import io.wisoft.iotplatform.group.role.RoleWithinGroup;
import io.wisoft.iotplatform.group.service.GroupService;
import org.hamcrest.collection.IsCollectionWithSize;
import org.hamcrest.core.Is;
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

import static io.wisoft.iotplatform.account.service.dto.AccountDto.*;
import static io.wisoft.iotplatform.accountgroup.service.dto.AccountGroupDto.*;
import static io.wisoft.iotplatform.fixtures.Fixtures.registerAccountDto;
import static io.wisoft.iotplatform.fixtures.Fixtures.registerAccountGroupDto;
import static io.wisoft.iotplatform.fixtures.Fixtures.registerGroupDto;
import static io.wisoft.iotplatform.group.service.dto.GroupDto.*;

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
class AccountGroupControllerTest {

  private static final String API_V1_BASE_URI = "/accountGroups/";

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private AccountService accountService;

  @Autowired
  private GroupService groupService;

  @Autowired
  private AccountGroupService accountGroupService;

  @Autowired
  private ObjectMapper objectMapper;

  private AccountGroupRegister registerAccountGroupDto;
  private MockMvc mockMvc;

  public AccountGroupControllerTest() {}

  @BeforeAll
  public void setUp() {
    this.mockMvc =
        MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    final AccountRegister registerAccountDto = registerAccountDto();
    final GroupRegister registerGroupDto = registerGroupDto();
    this.registerAccountGroupDto = registerAccountGroupDto();

    final Account account = accountService.register(registerAccountDto);
    final Group group = groupService.register(registerGroupDto);
    this.registerAccountGroupDto.setAccountId(account.getId());
    this.registerAccountGroupDto.setGroupId(group.getId());
  }

  @Test
  public void register() throws Exception {
    // Given & When
    final ResultActions resultActions = mockMvc.perform(
        post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(registerAccountGroupDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isCreated());
    resultActions.andExpect(jsonPath("$.account.username", is("testUser")));
    resultActions.andExpect(jsonPath("$.group.name", is("wisoft_test")));
    resultActions.andExpect(jsonPath("$.role", is("USER")));
  }

  @Test
  public void registerBadRequest() throws Exception {
    // Given
    final AccountGroupRegister newRegisterAccountGroupDto = registerAccountGroupDto();

    // When
    final ResultActions resultActions = mockMvc.perform(
        post(API_V1_BASE_URI).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newRegisterAccountGroupDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", Is.is("AccountGroupController-E001")));
  }

  @Test
  public void update() throws Exception {
    // Given
    final AccountGroup accountGroup = accountGroupService.register(registerAccountGroupDto);

    final AccountGroupUpdate updateDto = new AccountGroupUpdate();
    updateDto.setRole(RoleWithinGroup.MANAGER);

    // When
    final ResultActions resultActions = mockMvc.perform(
        put(API_V1_BASE_URI + accountGroup.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.role", is("MANAGER")));
  }

  @Test
  public void updateBadRequest() throws Exception {
    // Given
    final AccountGroup accountGroup = accountGroupService.register(this.registerAccountGroupDto);

    final AccountGroupUpdate updateDto = new AccountGroupUpdate();

    // When
    final ResultActions resultActions = mockMvc.perform(
        put(API_V1_BASE_URI + accountGroup.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateDto)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
    resultActions.andExpect(jsonPath("$.code", Is.is("AccountGroupController-E002")));
  }

  @Test
  public void remove() throws Exception {
    // Given
    final AccountGroup accountGroup = accountGroupService.register(registerAccountGroupDto);

    // When
    final ResultActions resultActions =
        mockMvc.perform(delete(API_V1_BASE_URI + accountGroup.getId()));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isNoContent());
  }

  @Test
  public void removeBadRequest() throws Exception {
    // Given
    final String accountGroupId = "1000";

    // When
    final ResultActions resultActions = mockMvc.perform(delete(API_V1_BASE_URI + accountGroupId));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
  }

  @Test
  public void getAccountGroup() throws Exception {
    // Given
    final AccountGroup accountGroup = accountGroupService.register(this.registerAccountGroupDto);

    // When
    final ResultActions resultActions =
        mockMvc.perform(get(API_V1_BASE_URI + accountGroup.getId()));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.account.name", Is.is("testUser")));
    resultActions.andExpect(jsonPath("$.group.name", Is.is("wisoft_test")));
  }

  @Test
  public void getAccountGroupSearchByAccountId() throws Exception {
    // Given
    final AccountGroup accountGroup = accountGroupService.register(this.registerAccountGroupDto);

    // When
    final ResultActions resultActions = mockMvc.perform(
        get(API_V1_BASE_URI + "/search/byAccountId")
            .param("accountId", String.valueOf(accountGroup.getAccount().getId())));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$[0].account.name", Is.is("testUser")));
    resultActions.andExpect(jsonPath("$[0].group.name", Is.is("wisoft_test")));
  }

  @Test
  public void getAccountGroupSearchByAccountIdNotFoundException() throws Exception {
    // Given
    accountGroupService.register(this.registerAccountGroupDto);

    // When
    final ResultActions resultActions = mockMvc.perform(
        get(API_V1_BASE_URI + "/search/byAccountId")
            .param("accountId", String.valueOf(100L)));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isBadRequest());
  }

  @Test
  public void getAccountGroups() throws Exception {
    // Given
    accountGroupService.register(this.registerAccountGroupDto);

    // When
    final ResultActions resultActions = mockMvc.perform(get(API_V1_BASE_URI));

    // Then
    resultActions.andDo(print());
    resultActions.andExpect(status().isOk());
    resultActions.andExpect(jsonPath("$.content", IsCollectionWithSize.hasSize(3)));
  }

}