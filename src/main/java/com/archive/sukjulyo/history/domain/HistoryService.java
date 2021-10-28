package com.archive.sukjulyo.history.domain;

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
        Optional<History> history = historyRepository.findById(id);
        if (history.isPresent())
            return history.get();

        throw new EntityNotFoundException("Cant find any history under given ID");
    }

    //SELECT ALL history
    public List<History> selectHistorys() {
        return historyRepository.findAll();
    }

    //CREATE History
    public History createHistory(HistoryCreationRequest request) {
        History history = new History();
        BeanUtils.copyProperties(request, history);
        return historyRepository.save(history);
    }

    //DELETE History
    public void deleteHistory(Long id) {
        historyRepository.deleteById(id);
    }
}