package com.archive.sukjulyo.liked.domain;

import com.archive.sukjulyo.client.domain.Client;
import com.archive.sukjulyo.client.domain.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Lazy
public class LikedService {
    private final LikedRepository likedRepository;
    private final ClientRepository clientRepository;

    //SELECT Liked by id
    public Liked selectLiked (Long id) {
        Optional<Liked> liked = likedRepository.findById(id);
        if (!liked.isPresent())
            return liked.get();
        throw new EntityNotFoundException("Can not find any Liked in id");
    }

    //CREATE Liked
    public Liked createLiked (LikedCreationRequest liked) {
        Optional<Client> client = clientRepository.findById(liked.getClient().getId());
        if (!client.isPresent()) {
            throw new EntityNotFoundException("Client Not Found");
        }
        Liked likedToCreate = new Liked();
        BeanUtils.copyProperties(liked, likedToCreate);
        likedToCreate.setClient(client.get());
        return likedRepository.save(likedToCreate);
    }

    //UPDATE Liked
    public Liked updateLiked(Long id, LikedCreationRequest request) {
        Optional<Liked> optionalLiked = likedRepository.findById(id);
        if (!optionalLiked.isPresent())
            throw new EntityNotFoundException("Liked not preset int the database");

        Liked liked = optionalLiked.get();
        liked.setClient(request.getClient());
        liked.setIslike(request.isIslike());
        liked.setCreate_at(request.getCreate_at());
        return likedRepository.save(liked);
    }

    //DELETE Liked
    public void deleteLiked(Long id) {
        likedRepository.deleteById(id);
    }

}
