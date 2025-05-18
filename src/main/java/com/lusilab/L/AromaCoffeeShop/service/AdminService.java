package com.lusilab.L.AromaCoffeeShop.service;

import com.lusilab.L.AromaCoffeeShop.entity.Admin;
import com.lusilab.L.AromaCoffeeShop.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AdminService {

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
