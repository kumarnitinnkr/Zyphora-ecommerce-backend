package com.zyphora.order.controller;

import com.zyphora.order.entity.Address;
import com.zyphora.order.repository.AddressRepository;
import com.zyphora.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressRepository repository;
    private final UserRepository userRepository;

    private String email() {
        return SecurityContextHolder.getContext()
                .getAuthentication().getName();
    }

    @PostMapping
    public Object add(@RequestBody Address address) {

        address.setUser(
                userRepository.findByEmail(email()).orElseThrow()
        );

        return repository.save(address);
    }

    @DeleteMapping("/{id}")
public void delete(@PathVariable Long id) {

    Address address = repository.findById(id)
            .orElseThrow();

    repository.delete(address);
}

    @GetMapping
    public Object all() {
        return repository.findByUserEmail(email());
    }
}