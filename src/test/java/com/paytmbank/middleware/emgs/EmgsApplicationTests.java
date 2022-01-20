package com.paytmbank.middleware.emgs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paytmbank.middleware.emgs.entity.Employee;
import com.paytmbank.middleware.emgs.service.EmployeeService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
@AutoConfigureMockMvc
class EmgsApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	ObjectMapper objectMapper;

	final static String phNoRand = "0123456789";
	final static String suffix = "@gmail.com", emailRand = "abcdefghijklmnopqrstuvwxyz";

	/* List of all the created employees for the sake of testing */
	List<Employee> created;

	String randomPhone() {
		String ans = "";
		Random rnd = new Random();
		for (int i = 0; i < 10; i++) {
			ans += phNoRand.charAt(rnd.nextInt(10));
		}
		return ans;
	}

	String randomEmail() {
		String ans = "";
		Random rnd = new Random();
		for (int i = 0; i < 10; i++) {
			ans += emailRand.charAt(rnd.nextInt(26));
		}
		return ans + suffix;
	}

	void createRandomUsers() {
		created = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			Employee curr = new Employee();
			// for the sake of testing email is the eid.
			curr.setEid(randomEmail());
			curr.setEmail(randomEmail());
			curr.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			curr.setActive(true);
			curr.setPhone(randomPhone());

			boolean flag = true;
			for (int j = 0; j < created.size(); j++) {
				Employee temp = created.get(j);
				if (temp.getPhone() == curr.getPhone() || temp.getEmail() == curr.getEmail() || temp.getEid() == curr.getEid()) {
					flag = false;
					break;
				}
			}
			created.add(curr);
		}
	}

	/* Testing all the end points. */
	@Test
	@Order(1)
	void addUsers() throws Exception {
		createRandomUsers();
		for (int i = 0; i < created.size(); i++) {
			mockMvc.perform(
					MockMvcRequestBuilders.post("/createEmployee").contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(created.get(i)))
			).andExpect(
					MockMvcResultMatchers.status().isOk()
			);
		}
	}

	@Test
	@Order(2)
	void getUsers() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/users")
		).andExpect(
				MockMvcResultMatchers.status().isOk()
		);
	}


	@Test
	void contextLoads() {

	}

}
