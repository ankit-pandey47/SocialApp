package com.social.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.social.Entity.Friend;

public interface FriendRepository extends CrudRepository<Friend, Long>{

	@Query("select f from Friend f where f.friend.id =:n AND f.user.id =:n1")
	Friend getFriendByFriend_idAndUser_id(@Param("n")Long id , @Param("n1")Long long1);
	
	@Query("select f from Friend f where f.friend.id =:n ")
	List<Friend> getFriendByFriend_id(@Param("n")Long id );
	
	
	 @Query("select f from Friend f where f.user.id =:n ") 
	List<Friend> getFriendByUser_id(@Param("n")Long id );
	 
}
