package mbn.repository;

import mbn.model.FileRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FileRepository extends JpaRepository<FileRequest, UUID> {
    Optional<FileRequest> findByPath(String path);
}
