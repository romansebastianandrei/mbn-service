package mbn.repository;

import mbn.model.Client;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {

    Optional<Client> findClientByCodPatient(Long codPatient);
    Optional<Client> findClientByCnp(String cnp);
    List<Client> findAll(Specification<Client> spec);

}
