package com.social.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.social.Entity.Friend;
import com.social.Entity.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Long>{

	List<Post> findByUser_Id(Long user_id);
	
	@Query("select p from Post p where p.user.id =:n ")
	List<Post> getPostByUser_id(@Param("n")Long id );
	
	@Query("select p from Post p where p.user.id =:n ")
	List<Post> getPostByFriend_id(@Param("n")Long id );
	
}
