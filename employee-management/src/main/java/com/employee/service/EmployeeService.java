package com.employee.service;

import java.util.List;

import com.employee.dto.EmployeeDto;

public interface EmployeeService {

	EmployeeDto create(EmployeeDto dto);

	EmployeeDto getById(Long id);

	List<EmployeeDto> getAll();

	EmployeeDto update(Long id, EmployeeDto dto);

	void delete(Long id);

}
