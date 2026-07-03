package com.chatop.rental;

import com.chatop.common.dto.SimpleMessageResponse;
import com.chatop.exception.ResourceNotFoundException;
import com.chatop.rental.dto.RentalCreateRequest;
import com.chatop.rental.dto.RentalResponse;
import com.chatop.rental.dto.RentalUpdateRequest;
import com.chatop.rental.dto.RentalsResponse;
import com.chatop.storage.FileStorageService;
import com.chatop.user.User;
import com.chatop.user.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    public RentalService(RentalRepository rentalRepository,
                          UserRepository userRepository,
                          FileStorageService fileStorageService) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
    }

    public RentalsResponse findAll() {
        return new RentalsResponse(
                rentalRepository.findAll().stream().map(RentalResponse::new).toList()
        );
    }

    public RentalResponse findById(Integer id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found: " + id));
        return new RentalResponse(rental);
    }

    public SimpleMessageResponse create(RentalCreateRequest request, String ownerEmail) {
        MultipartFile picture = request.getPicture();
        if (picture == null || picture.isEmpty()) {
            throw new IllegalArgumentException("picture is required");
        }

        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email " + ownerEmail));

        String filename = fileStorageService.store(picture);
        String pictureUrl = fileStorageService.buildPublicUrl(filename);

        Rental rental = new Rental();
        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setDescription(request.getDescription());
        rental.setPicture(pictureUrl);
        rental.setOwnerId(owner.getId());
        rentalRepository.save(rental);

        return new SimpleMessageResponse("Rental created !");
    }

    public SimpleMessageResponse update(Integer id, RentalUpdateRequest request) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found: " + id));

        rental.setName(request.getName());
        rental.setSurface(request.getSurface());
        rental.setPrice(request.getPrice());
        rental.setDescription(request.getDescription());
        rentalRepository.save(rental);

        return new SimpleMessageResponse("Rental updated !");
    }
}
