package ru.bolnik.dima.dao;

import org.springframework.data.repository.CrudRepository;
import ru.bolnik.dima.entity.AppDocument;

public interface AppDocumentDAO extends CrudRepository<AppDocument, Long> {
}
