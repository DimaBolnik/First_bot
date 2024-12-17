package ru.bolnik.dima.dao;

import org.springframework.data.repository.CrudRepository;
import ru.bolnik.dima.entity.AppPhoto;

public interface AppPhotoDAO extends CrudRepository<AppPhoto, Long> {
}
