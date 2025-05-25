package org.animeapi.service;

import org.animeapi.repository.UserRepository;
import org.animeapi.request.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final UserRepository repository;

    @Autowired
    public RegistrationService(UserRepository repository) {
        this.repository = repository;
    }

    public String register(RegistrationRequest request) {
        return "works";
    }
}

