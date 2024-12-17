package ru.bolnik.dima.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bolnik.dima.entity.RawData;

public interface RawDataDAO extends JpaRepository<RawData, Long> {
}
