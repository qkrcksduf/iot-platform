package io.wisoft.iotplatform.model.repository;

import io.wisoft.iotplatform.model.domain.ModelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModelTypeRepository extends JpaRepository<ModelType, UUID> {

  Optional<ModelType> findById(final UUID id);

  Optional<ModelType> findByName(final String name);

}