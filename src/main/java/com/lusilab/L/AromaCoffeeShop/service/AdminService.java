package com.lusilab.L.AromaCoffeeShop.service;

import com.lusilab.L.AromaCoffeeShop.entity.Admin;
import com.lusilab.L.AromaCoffeeShop.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AdminService {

    @Autowired
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }


    public void saveAdmin(Admin admin) {
        adminRepository.save(admin);
    }


    public Optional<Admin> login(String email,String password) {
        return adminRepository.findByEmail(email)
                .filter(admin -> admin.getPassword().equals(password));
    }

    public boolean existsByEmail(String email) {
        return adminRepository.findByEmail(email).isPresent();
    }


}
