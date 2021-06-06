package io.wisoft.iotplatform.model.repository;

import io.wisoft.iotplatform.model.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModelRepository extends JpaRepository<Model, UUID> {

  Optional<Model> findById(final UUID id);

  Optional<Model> findByName(final String name);

}