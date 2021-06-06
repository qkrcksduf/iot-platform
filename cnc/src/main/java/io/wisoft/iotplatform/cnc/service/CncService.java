package io.wisoft.iotplatform.cnc.service;

import io.wisoft.iotplatform.cnc.otherservice.OtherService;
import io.wisoft.iotplatform.cnc.service.dto.CncDto.CncInitialize;
import io.wisoft.iotplatform.cnc.service.dto.CncDto.CncRegister;
import io.wisoft.iotplatform.cnc.service.dto.CncDto.CncUpdate;
import io.wisoft.iotplatform.cnc.repository.CncRepository;
import io.wisoft.iotplatform.cnc.domain.Cnc;
import io.wisoft.iotplatform.cnc.exception.CncDuplicatedException;
import io.wisoft.iotplatform.cnc.exception.CncNotFoundException;
import io.wisoft.iotplatform.cnc.otherservice.dto.DeviceResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class CncService {

  private CncRepository cncRepository;
  private OtherService otherService;
  private ModelMapper modelMapper;

  public Cnc register(final CncRegister registerDto) {
    checkDuplicateName(registerDto.getName());
    checkDuplicateIpAddress(registerDto.getIpAddress());
    checkExistingGroup(registerDto.getGroupId());

    final Cnc cnc = modelMapper.map(registerDto, Cnc.class);

    return cncRepository.save(cnc);
  }

  public Cnc update(final UUID id, final CncUpdate updateDto) {
    final Cnc cnc = getCnc(id);
    cnc.setName(updateDto.getName());
    cnc.setLocation(updateDto.getLocation());
    cnc.setStatus(updateDto.getStatus());
    cnc.setIpAddress(updateDto.getIpAddress());
    cnc.setDescription(updateDto.getDescription());
    cnc.setUpdated(LocalDateTime.now());

    return cncRepository.save(cnc);
  }

  public Cnc initialize(final UUID id, final CncInitialize dto) {
    final Cnc cnc = getCnc(id);
    cnc.setStatus(dto.getStatus());
    cnc.setIpAddress(dto.getIpAddress());
    cnc.setUpdated(LocalDateTime.now());

    return cncRepository.save(cnc);
  }

  public void remove(final UUID id) {
    cncRepository.delete(getCnc(id));
  }

  public Cnc getCnc(final UUID id) {
    return cncRepository.findById(id).orElseThrow(() -> new CncNotFoundException(String.valueOf(id)));
  }

  public Cnc getCncBySerial(final String serial) {
    return cncRepository.findBySerial(serial).orElseThrow(() -> new CncNotFoundException(serial));
  }

  public Cnc getCncByActuatorId(final UUID actuatorId) {
    final DeviceResponse deviceResponse = otherService.getDeviceListByActuatorId(actuatorId);

    return cncRepository.findById(deviceResponse.getCncId()).orElseThrow(() -> new CncNotFoundException(String.valueOf(actuatorId)));
  }

  public Cnc getCncByName(final String name) {
    return cncRepository.findByName(name).orElseThrow(() -> new CncNotFoundException(name));
  }

  public List<Cnc> getCncListByGroupId(final UUID id) {
    final List<Cnc> cnc = cncRepository.findByGroupId(id);
    if (cnc.isEmpty()) {
      throw new CncNotFoundException(String.valueOf(id));
    }

    return cnc;
  }

//  public List<ActuatingDto> getActuatingsById(final Long id, final String result) {
//    ActuatingDto.Status status = null;
//    if ((result.toUpperCase()).equals("RUNNING")) {
//      status = ActuatingDto.Status.RUNNING;
//    }
//
//    return cncRepository.getActuatingsById(id, status);
//  }

  public Page<Cnc> getCncs(final Pageable pageable) {
    return cncRepository.findAll(pageable);
  }

  public List<DeviceResponse> getDeviceListOfCnc(UUID cncId) {
    checkExistingCnc(cncId);

    return otherService.getDeviceListByCncId(cncId);
  }

  private void checkDuplicateName(final String name) {
    cncRepository.findByName(name).ifPresent(cnc -> {
      log.error("CnC Duplicated Name Exception: {}", cnc.getName());
      throw new CncDuplicatedException(cnc.getName());
    });
  }

  private void checkDuplicateIpAddress(final String ipAddress) {
    cncRepository.findByIpAddress(ipAddress).ifPresent(cnc -> {
      log.error("CnC Duplicated IP Address Exception: {}", cnc.getIpAddress());
      throw new CncDuplicatedException(cnc.getIpAddress());
    });
  }

  private void checkExistingGroup(final UUID groupId) {
    otherService.checkExistingGroup(groupId);
  }

  private void checkExistingCnc(final UUID cncId) {
    getCnc(cncId);
  }

}
