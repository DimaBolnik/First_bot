package ru.bolnik.dima.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bolnik.dima.entity.AppPhoto;

public interface AppPhotoDAO extends JpaRepository<AppPhoto, Long> {
}
