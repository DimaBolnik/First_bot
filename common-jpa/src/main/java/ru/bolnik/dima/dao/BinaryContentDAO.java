package ru.bolnik.dima.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bolnik.dima.entity.BinaryContent;

public interface BinaryContentDAO extends JpaRepository<BinaryContent, Long> {
}
