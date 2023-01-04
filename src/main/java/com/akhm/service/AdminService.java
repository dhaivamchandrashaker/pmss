package com.akhm.service;

import com.akhm.repository.entity.AdminTl;

public interface AdminService {
	public AdminTl saveAdmin(AdminTl adminTl);
	public AdminTl getAdmin(String emailId,String password);
	public AdminTl updateAdmin(AdminTl adminTl);
	

}
