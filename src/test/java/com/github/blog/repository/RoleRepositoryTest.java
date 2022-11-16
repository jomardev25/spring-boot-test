package com.github.blog.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.github.blog.entity.Role;

//@DataJpaTest
//@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(
	properties = {
		//"spring.profiles.active=test",
		//"spring.datasource.url=jdbc:postgresql://localhost:5432/blog_app_test" //Should be docker docker container
	}
)
public class RoleRepositoryTest {

	@Container
	public static PostgreSQLContainer<?> pgsql = new PostgreSQLContainer<>("postgres:latest");

	/*@DynamicPropertySource
	static void configureTestContainersProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", "postgre");
		registry.add("spring.datasource.username", );
		registry.add("spring.datasource.password", pgsql::getPassword);
	}*/

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private TestEntityManager entityManage;

	@Test
	@Sql("/multiple-insert-role.sql")
	void testFindAllRole() {
		List<Role> roles = roleRepository.findAll();
		assertThat(roles).isNotEmpty();
		assertThat(roles.size()).isEqualTo(2);
	}

	@Test
	void testSaveRole() {
		Role role = Role.builder().name("admin").build();
		Role savedRole = roleRepository.save(role);
		assertThat(savedRole).usingRecursiveComparison().ignoringFields("id").isEqualTo(role);
		System.out.println(savedRole.toString());
	}

	@Test
	@Sql("classpath:multiple-insert-role.sql")
	void testFindById() {
		Long roleId = 1L;
		Optional<Role> role = roleRepository.findById(roleId);
		assertThat(role).isPresent();
		System.out.println(role.toString());
	}

	@Test
	@Sql("/multiple-insert-role.sql")
	void testUpdateRole() {
		Long roleId = 1L;
		Optional<Role> role = roleRepository.findById(roleId);
		assertThat(role).isPresent();

		role.get().setName("administrator");
		Role savedRole = roleRepository.save(role.get());
		assertThat(savedRole.getName()).isEqualTo("administrator");
		System.out.println(role.get().toString());
	}

	@Test
	@Sql("classpath:multiple-insert-role.sql")
	void testDeleteById() {
		Long roleId = 1L;
		roleRepository.deleteById(roleId);
		Optional<Role> role = roleRepository.findById(roleId);
		assertThat(role).isNotPresent();
	}
}
