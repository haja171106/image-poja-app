package com.school.haja.repository;

import com.school.haja.repository.model.JImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<JImage, Long> {}
