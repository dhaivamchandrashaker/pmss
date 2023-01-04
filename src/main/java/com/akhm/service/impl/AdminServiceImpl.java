package com.akhm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akhm.repository.AdminRepository;
import com.akhm.repository.entity.AdminTl;
import com.akhm.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{
	@Autowired
	private AdminRepository repository;
	@Override
	public AdminTl saveAdmin(AdminTl adminTl) {
		AdminTl admin=null;
		admin=repository.save(adminTl);
		return admin;
	}

	@Override
	public AdminTl getAdmin(String emailId, String password) {
		AdminTl admin=repository.findByEmailIdAndPassword(emailId, password);
		return admin;
	}

	@Override
	public AdminTl updateAdmin(AdminTl adminTl) {
		AdminTl admin=repository.findById(adminTl.getAdminId()).get();
		if(admin!=null)
		{
			admin.setFirstName(adminTl.getFirstName());
			admin.setLastName(adminTl.getLastName());
			admin.setEmailId(adminTl.getEmailId());
			admin.setPassword(adminTl.getPassword());
			admin.setMobileNumber(adminTl.getMobileNumber());
			admin.setStatus(adminTl.getStatus());
			admin=repository.save(admin);
		}
		return admin;
	}

		

}
