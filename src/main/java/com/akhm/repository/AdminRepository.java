package com.akhm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akhm.repository.entity.AdminTl;

public interface AdminRepository extends JpaRepository<AdminTl, Integer>{
	public AdminTl findByEmailIdAndPassword(String emailId,String password);
}
