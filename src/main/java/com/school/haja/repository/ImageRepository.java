package com.school.haja.repository;

import com.school.haja.repository.model.JImage;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<JImage, UUID> {}
