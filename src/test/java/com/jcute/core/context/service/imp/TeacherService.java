package com.jcute.core.context.service.imp;

import com.jcute.core.annotation.Component;
import com.jcute.core.context.service.IUserService;

@Component
public class TeacherService implements IUserService{

	@Override
	public String getName(){
		return "TeacherService";
	}
	
}