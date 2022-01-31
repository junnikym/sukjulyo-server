package com.archive.sukjulyo.liked.service;

import com.archive.sukjulyo.account.repository.AccountRepository;
import com.archive.sukjulyo.liked.domain.Liked;
import com.archive.sukjulyo.liked.dto.LikedCreationDTO;
import com.archive.sukjulyo.liked.repository.LikedRepository;
import com.archive.sukjulyo.util.ClassConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

@Service
@RequiredArgsConstructor
@Lazy
public class LikedService {
    private final LikedRepository likedRepository;
    private final AccountRepository accountRepository;

    //SELECT Liked by id
    public Liked selectLiked (Long id) {
        return likedRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find target account"
                ));
    }

    //CREATE Liked
    public Liked createLiked (LikedCreationDTO dto) {
        dto.setAccount(accountRepository
                .findById(dto.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find target account"
                ))
        );

        return likedRepository.save(dto.toEntity());
    }

    //UPDATE Liked
    public Liked updateLiked(Long id, LikedCreationDTO dto) throws Exception {
        Liked liked = likedRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find target liked entity"
                ));

        ClassConverter.convertWithoutNull(dto, liked);

        return likedRepository.save(liked);
    }

    //DELETE Liked
    public void deleteLiked(Long id) {
        likedRepository.deleteById(id);
    }

}
