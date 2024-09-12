package com.social.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.social.Entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer >{
	
	@Query("select u from User u where u.email =:n")
	User getUserByEmail(@Param("n")String email);

	public List<User> findByEmailContaining(String keyword);
	public List<User> findByUsernameContaining(String keyword);
	

	@Query("select u from User u where u.username =:n")
	User getUserByUsername(@Param("n")String username);

	User findById(Long id);
}
