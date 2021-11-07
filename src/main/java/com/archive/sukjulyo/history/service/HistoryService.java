package com.archive.sukjulyo.history.service;

import com.archive.sukjulyo.client.domain.Client;
import com.archive.sukjulyo.client.repository.ClientRepository;
import com.archive.sukjulyo.history.domain.History;
import com.archive.sukjulyo.history.dto.HistoryCreationDTO;
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
    private final ClientRepository clientRepository;

    //SELECT History
    public History selectHistory(Long id) {
        return historyRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find any history"
                ));
    }

    //SELECT ALL history
    public List<History> selectHistoryList() {
        return historyRepository.findAll();
    }

    //CREATE History
    public History createHistory(HistoryCreationDTO dto) {
        dto.setClient(clientRepository
                .findById(dto.getClientId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find target client"
                ))
        );

        return historyRepository.save(dto.toEntity());
    }

    //DELETE History
    public void deleteHistory(Long id) {
        historyRepository.deleteById(id);
    }
}