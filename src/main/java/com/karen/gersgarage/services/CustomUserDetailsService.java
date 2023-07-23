package com.karen.gersgarage.services;

import com.karen.gersgarage.model.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ClientRepository clientRepository;

    public CustomUserDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(email);
        if (client == null) {
            throw new UsernameNotFoundException(email);
        }
        Logger.getLogger(CustomUserDetailsService.class.getName()).info("CustomUserDetailsService--Client: " + client);
        return new org.springframework.security.core.userdetails.User(

                client.getEmail(), client.getPaassword(), getAuthorities(client));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Client client) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        //  0 es admin y 1 es user
        if (client.getProfile() == 0) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            Logger.getLogger(CustomUserDetailsService.class.getName()).info("CustomUserDetailsService--ROLE_ADMIN: " + client);
        } else if (client.getProfile() == 1) {
            Logger.getLogger(CustomUserDetailsService.class.getName()).info("CustomUserDetailsService--ROLE_USER: " + client);
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return authorities;
    }
}
