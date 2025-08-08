package com.employee.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.employee.dto.EmployeeDto;
import com.employee.entity.Employee;
import com.employee.exception.ResourceNotFoundException;
import com.employee.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository repo;

	public EmployeeServiceImpl(EmployeeRepository repo) {
		this.repo = repo;
	}

	@Override
	public EmployeeDto create(EmployeeDto dto) {
		Employee e = new Employee();
		BeanUtils.copyProperties(dto, e);
		Employee saved = repo.save(e);
		EmployeeDto out = new EmployeeDto();
		BeanUtils.copyProperties(saved, out);
		return out;
	}

	@Override
	public EmployeeDto getById(Long id) {
		Employee e = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
		EmployeeDto dto = new EmployeeDto();
		BeanUtils.copyProperties(e, dto);
		return dto;
	}

	@Override
	public List<EmployeeDto> getAll() {
		return repo.findAll().stream().map(e -> {
			EmployeeDto d = new EmployeeDto();
			BeanUtils.copyProperties(e, d);
			return d;
		}).collect(Collectors.toList());
	}

	@Override
	public EmployeeDto update(Long id, EmployeeDto dto) {
		Employee e = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
		e.setFirstName(dto.getFirstName());
		e.setLastName(dto.getLastName());
		e.setEmail(dto.getEmail());
		Employee saved = repo.save(e);
		EmployeeDto out = new EmployeeDto();
		BeanUtils.copyProperties(saved, out);
		return out;
	}

	@Override
	public void delete(Long id) {
		 if (!repo.existsById(id)) throw new ResourceNotFoundException("Employee not found: " + id);
				  repo.deleteById(id);

	}

}
