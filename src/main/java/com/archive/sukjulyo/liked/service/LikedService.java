package com.archive.sukjulyo.liked.service;

import com.archive.sukjulyo.client.domain.Client;
import com.archive.sukjulyo.client.repository.ClientRepository;
import com.archive.sukjulyo.liked.domain.Liked;
import com.archive.sukjulyo.liked.dto.LikedCreationDTO;
import com.archive.sukjulyo.liked.repository.LikedRepository;
import com.archive.sukjulyo.util.PropertyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Lazy
public class LikedService {
    private final LikedRepository likedRepository;
    private final ClientRepository clientRepository;

    //SELECT Liked by id
    public Liked selectLiked (Long id) {
        return likedRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find target client"
                ));
    }

    //CREATE Liked
    public Liked createLiked (LikedCreationDTO dto) {
        dto.setClient(clientRepository
                .findById(dto.getClientId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find target client"
                ))
        );

        return likedRepository.save(dto.toEntity());
    }

    //UPDATE Liked
    public Liked updateLiked(Long id, LikedCreationDTO dto) {
        Liked liked = likedRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't find target liked entity"
                ));

        BeanUtils.copyProperties(dto, liked, PropertyUtil.getNullPropertyNames(dto));

        return likedRepository.save(liked);
    }

    //DELETE Liked
    public void deleteLiked(Long id) {
        likedRepository.deleteById(id);
    }

}
