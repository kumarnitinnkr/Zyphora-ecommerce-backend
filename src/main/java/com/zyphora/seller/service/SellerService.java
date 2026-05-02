package com.zyphora.seller.service;

import com.zyphora.auth.entity.Role;
import com.zyphora.auth.repository.UserRepository;
import com.zyphora.seller.entity.*;
import com.zyphora.seller.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository repository;
    private final UserRepository userRepository;

    private String email() {
        return SecurityContextHolder.getContext()
                .getAuthentication().getName();
    }

    public Object apply(SellerProfile request) {

        var user = userRepository.findByEmail(email()).orElseThrow();

        if (repository.findByUserEmail(email()).isPresent()) {
            return "Already applied";
        }

        request.setUser(user);
        request.setStatus(SellerStatus.PENDING);

        return repository.save(request);
    }

    public Object myProfile() {
        return repository.findByUserEmail(email());
    }

    public Object approve(Long id) {

        var seller = repository.findById(id).orElseThrow();

        seller.setStatus(SellerStatus.APPROVED);

        var user = seller.getUser();
        user.setRole(Role.ROLE_SELLER);

        userRepository.save(user);
        repository.save(seller);

        return "Seller Approved";
    }

    public Object all() {
        return repository.findAll();
    }
}