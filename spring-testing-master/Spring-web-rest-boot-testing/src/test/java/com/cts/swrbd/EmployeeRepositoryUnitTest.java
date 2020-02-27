package com.cts.swrbd;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.swrbd.dao.EmployeeRepository;
import com.cts.swrbd.model.Department;
import com.cts.swrbd.model.Employee;

//spring uses junit only

@RunWith(SpringRunner.class)  //a bridge btw Spring Boot test features and JUnit
@DataJpaTest  //Configuring H2,in memory
public class EmployeeRepositoryUnitTest {
	
	private Employee emps[];
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public EmployeeRepositoryUnitTest() {
		emps=new Employee[] {
				new Employee("Bireddy","Chandra",45000,LocalDate.now(),Department.DEVELOPMENT,"9954632897","cbrh@gmail.com")
		};
	}
	
	@Before
	public void beforeEachTest() {
		for(Employee e:emps) {
			entityManager.persist(e);
		}
		entityManager.flush();
	}
	@After
	public void afterEachTest() {
		for(Employee e:emps) {
			entityManager.remove(e);
		}
		entityManager.flush();
	}
	
	@Test
	public void whenFindByMobileNumber_thenReturnEmployee() {
		Employee e=employeeRepository.findByMobileNumber(emps[0].getMobileNumber());
		assertThat(e).isEqualTo(emps[0]);
	}
	@Test
	public void whenFindByMobileNumber_withNonExistingMobileNumber_thenReturnNull() {
		Employee e=employeeRepository.findByMobileNumber("9951537897");
		assertThat(e).isNull();
	}

}