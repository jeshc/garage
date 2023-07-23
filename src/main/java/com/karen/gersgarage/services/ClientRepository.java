package com.karen.gersgarage.services;

import com.karen.gersgarage.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {
    public Client findByEmail(String email);
}
