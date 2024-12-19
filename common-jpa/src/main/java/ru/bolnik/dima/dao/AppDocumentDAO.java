package ru.bolnik.dima.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bolnik.dima.entity.AppDocument;

public interface AppDocumentDAO extends JpaRepository<AppDocument, Long> {
}
