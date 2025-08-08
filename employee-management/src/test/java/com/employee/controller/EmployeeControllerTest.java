package com.employee.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType; // ✅ Correct import
import org.springframework.test.web.servlet.MockMvc;

import com.employee.dto.EmployeeDto;
import com.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EmployeeController.class) 
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService service;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_returnsCreated() throws Exception {
        EmployeeDto dto = new EmployeeDto("A", "B", "a@b.com");
        EmployeeDto saved = new EmployeeDto("A", "B", "a@b.com");
        saved.setId(1L); 

        when(service.create(any(EmployeeDto.class))).thenReturn(saved);

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON) 
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getAll_returnsList() throws Exception {
        when(service.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/employees")
                .accept(MediaType.APPLICATION_JSON)) 
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
