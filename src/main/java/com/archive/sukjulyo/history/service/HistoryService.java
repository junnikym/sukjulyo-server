package com.archive.sukjulyo.history.service;

import com.archive.sukjulyo.history.domain.History;
import com.archive.sukjulyo.history.dto.HistoryCreationRequest;
import com.archive.sukjulyo.history.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;

    //SELECT History
    public History selectHistory(Long id) {
        return historyRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find any history"
                ));
    }

    //SELECT ALL history
    public List<History> selectHistorys() {
        return historyRepository.findAll();
    }

    //CREATE History
    public History createHistory(HistoryCreationRequest dto) {
        return historyRepository.save(dto.toEntity());
    }

    //DELETE History
    public void deleteHistory(Long id) {
        historyRepository.deleteById(id);
    }
}