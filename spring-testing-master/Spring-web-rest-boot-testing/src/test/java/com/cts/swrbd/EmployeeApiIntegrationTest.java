package com.cts.swrbd;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cts.swrbd.dao.EmployeeRepository;
import com.cts.swrbd.model.Department;
import com.cts.swrbd.model.Employee;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK,
classes=SpringWebRestBootDemoApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
public class EmployeeApiIntegrationTest {

@Autowired
private MockMvc mvc;

@Autowired
private EmployeeRepository repository;

@Before
public void setUp() {
Employee emp = new Employee("Bireddy", "Chandra",45000, LocalDate.now(), Department.DEVELOPMENT,"9951537897","cbrh@gmail.com");
repository.save(emp);
}
@After
public void tearDown() {
repository.deleteAll();
}

@Test
public void whenFindAll_thenReturnJsonArray() throws Exception {

mvc.perform(get("/emps").contentType(MediaType.APPLICATION_JSON))
.andExpect(status().isOk())
.andExpect(jsonPath("$", hasSize(1)))
.andExpect(jsonPath("$[0].mobileNumber", is("9951537897")));
}
}