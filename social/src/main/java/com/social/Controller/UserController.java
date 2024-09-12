package com.social.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.social.Entity.Friend;
import com.social.Entity.Post;
import com.social.Entity.User;
import com.social.Helper.Message;
import com.social.Repository.FriendRepository;
import com.social.Repository.PostRepository;
import com.social.Repository.UserRepository;

import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private FriendRepository friendRepository;
	
	
	
	@ModelAttribute   //it will run always
	public void common(Model model , Principal principal) {
		String email = principal.getName();  //usename is actually the email
		 User user =  userRepository.getUserByEmail(email);
		 
		 model.addAttribute("user" , user);
	}
	
	//open home page
	@GetMapping("/home")
	public String openHome() {
		return "/normal/home";
	}
	
	
	//open search page
	@GetMapping("/search-form")
    public String openSearchForm() {
		return "normal/search";
	}
	
	
	@GetMapping("/process-search/{query}")
	public ResponseEntity<?> search(@PathVariable("query")String query) {
		
		List<User> users = this.userRepository.findByUsernameContaining(query);
		
		System.out.println(users);
		
		
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/myprofile")
	public String myProfile(Principal principal , Model model ,HttpSession session) {
		session.removeAttribute("message");
		User user = this.userRepository.getUserByEmail(principal.getName());
		System.out.println(user);
	
		
		List<Post> posts = this.postRepository.findByUser_Id(user.getId());
		model.addAttribute("posts" , posts);
		model.addAttribute("user" , user);
		return "normal/myprofile";
		
	}
	
	//EDIT THE MYPROFILE 
	@PostMapping("/edit-profile")
	public String editProfile(@RequestParam("username")String myusername , @RequestParam("profession")String myprofession,@RequestParam("profile_img") MultipartFile file , Model model ,HttpSession session ,Principal principal ) throws IOException {
	System.out.println(myusername);
		
		session.removeAttribute("message");
		
		//first check is there any other user with same username
		User user1 = this.userRepository.getUserByUsername(myusername);
		System.out.println(user1);
		if(user1!=null) {
			session.setAttribute("message", new Message("Username already exists!", "alert-danger"));
			return "normal/myprofile";
		}
		
		
		//if user doesnot send blank name then only save to database , if sends blank then dont do
		User user = this.userRepository.getUserByEmail(principal.getName());
		if(!myusername.equals("")) {
	         user.setUsername(myusername);
	         this.userRepository.save(user);
		}
		
		if(!myprofession.equals("")) {
			
	         user.setProfession(myprofession);
	         this.userRepository.save(user);
		}
		
		
		//UPLOAD IMAGE
		System.out.println(file);
		if(!file.isEmpty()) {
			
		String fileName =file.getOriginalFilename();
		user.setProfile_img(fileName);
		this.userRepository.save(user);
      String UPLOAD_DIR = Paths.get("src/main/resources/static/images").toAbsolutePath().toString();	
		
	Files.copy(file.getInputStream(), Path.of(UPLOAD_DIR + "\\" + file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
	
		}
		
		
		List<Post> posts = this.postRepository.findByUser_Id(user.getId());
		model.addAttribute("posts" , posts);
		model.addAttribute("user",user);
		session.setAttribute("message", new Message("Succesfully updated..", "alert-success"));
		return "normal/myprofile";
		
	}
	
	
	
	
	@GetMapping("/createpost")
	public String createPost() {
		
		return "normal/createpost";
	}
	
	@PostMapping("/process-createpost")
	public String submitPost(@RequestParam("text")String text ,Principal principal , Model model) {
		Post post = new Post();
		
		User user = this.userRepository.getUserByEmail(principal.getName());
		
		
		post.setUser(user);
		
		post.setText(text);
		
		String todaydate = new Date().toString();

		post.setDop(todaydate);
		
		this.postRepository.save(post);
		
		List<Post> posts = this.postRepository.findByUser_Id(user.getId());
		model.addAttribute("posts" , posts);
		model.addAttribute("user" , user);
		
		return "normal/myprofile";
	}
	
	
	//DELETE THE POST BY THE ID
	
	@GetMapping("/delete-post/{myid}")
	public String deletePost(@PathVariable("myid")Long id , Principal principal , Model model) {
		User user = this.userRepository.getUserByEmail(principal.getName());
		
		
		System.out.println(user);
		
		Optional<Post> Optionalpost = this.postRepository.findById(id);
		
		Post post1 = Optionalpost.get();
		
		this.postRepository.delete(post1);
		
		model.addAttribute("user" ,user);
		List<Post> posts = this.postRepository.findByUser_Id(user.getId());
		model.addAttribute("posts" , posts);
		return "normal/myprofile";
	}
	
	
	
	
//	//EDIT THE POST BY THE ID
//	
//	@GetMapping("/edit-post/{myid}")
//	public String editPostForm(@RequestParam("myid")Long id , Model model) {
//		Optional<Post> Optionalpost = this.postRepository.findById(id);
//		Post post = Optionalpost.get();
//		model.addAttribute("post" , post);
//		return "normal/createpost";
//	}
	
	
//	@PostMapping("/update-post/myid}")
//	public String editPost(@RequestParam("myid")Long id) {
//		
//		return "normal/"
//	}
	
	
	//MAIN FRONT PAGE WITH ALL THE POSTS
	
	@GetMapping("/frontpage")
	public String forntPage(Model model, Principal principal) {
	    User user = this.userRepository.getUserByEmail(principal.getName());
	    model.addAttribute("user", user);

	    // Fetch friends by user_id and friend_id
	    List<Friend> friends1 = this.friendRepository.getFriendByFriend_id(user.getId());
	    List<Friend> friends2 = this.friendRepository.getFriendByUser_id(user.getId());

	    // Combine friends into a set to avoid duplicates
	    Set<Friend> allFriends = new HashSet<>();
	    allFriends.addAll(friends1);
	    allFriends.addAll(friends2);

	    List<Post> posts = new ArrayList<>();

	    // Fetch posts for each unique friend
	    for (Friend friend : allFriends) {
	        // Fetch posts for the friend (make sure you get the right ID from the friend entity)
	        List<Post> postByFriend;
	        
	        if (friend.getFriend().getId() != user.getId()) {
	            // Fetch posts of the friend, not the logged-in user
	            postByFriend = this.postRepository.getPostByUser_id(friend.getFriend().getId());
	        } else {
	            // This condition is for friends2, where you get posts from the other friend
	            postByFriend = this.postRepository.getPostByUser_id(friend.getUser().getId());
	        }

	        posts.addAll(postByFriend);
	    }

	    // Fetch the logged-in user's own posts
//	    List<Post> userPosts = this.postRepository.getPostByUser_id(user.getId());
//	    posts.addAll(userPosts);

	    // Remove any duplicate posts
	    Set<Post> uniquePosts = new HashSet<>(posts);
	    model.addAttribute("post", uniquePosts);

	    return "normal/front";
	}

	
	
	@GetMapping("/friend-profile/{myid}")
	public String friendProfile(@PathVariable("myid")Long id ,Model model , Principal principal) {
		

		//agr user apni id search kkrta h to hm uski myprofile page kholenege
		User user = this.userRepository.getUserByEmail(principal.getName());
		if(user.getId()== id) {
			return "redirect:/user/myprofile";
		}
		
	User user1= this.userRepository.findById(id);
	
	System.out.println(user1);
		
		model.addAttribute("user", user1);
		List<Post> posts = this.postRepository.findByUser_Id(user1.getId());
		model.addAttribute("posts" , posts);
		
		Friend friend = this.friendRepository.getFriendByFriend_idAndUser_id(id, user.getId());
		//if reverse exist then also send them as model
		if(friend==null) {
			Friend friendreverse = this.friendRepository.getFriendByFriend_idAndUser_id(user.getId(), id);
			model.addAttribute("friendreverse" ,friendreverse);
			return "normal/friend_profile";
			
		}
		System.out.println(friend);
		model.addAttribute("friend" , friend);
		
		return "normal/friend_profile";
	}
	
	
	@GetMapping("/send-request/{myuserid}")
	public String sendRequest(@PathVariable("myuserid")Long id , Model model , Principal principal) {
		
		
		
		
		User mainuser = this.userRepository.getUserByEmail(principal.getName());

		User frienduser = this.userRepository.findById(id);
		
		//check if reverse friend request exist then dont do new request just make then connected
		Friend friendreverse = this.friendRepository.getFriendByFriend_idAndUser_id(mainuser.getId() , id);
		if(friendreverse != null) {
			friendreverse.setStatus("CONFIRMED");
			this.friendRepository.save(friendreverse);
		    mainuser.setConnection_count(mainuser.getConnection_count()+1);
		    frienduser.setConnection_count(frienduser.getConnection_count()+1);
		    this.userRepository.save(mainuser);
		    this.userRepository.save(frienduser);
			return "redirect:/user/myprofile";
		}
		
		Friend friend1 = this.friendRepository.getFriendByFriend_idAndUser_id(id,mainuser.getId() );
		if(friend1 != null) {
			
			List<Post> posts = this.postRepository.findByUser_Id(id);
			model.addAttribute("posts",posts);
			model.addAttribute("user" , mainuser);
			model.addAttribute("friend",friend1);
			return "normal/friend_profile";
			
		}
		
		
		Friend friend = new Friend();
		friend.setUser(mainuser);
		friend.setFriend(frienduser);
		friend.setStatus("PENDING");
		
		this.friendRepository.save(friend);
		
		
		List<Post> posts = this.postRepository.findByUser_Id(id);
		model.addAttribute("posts",posts);
		model.addAttribute("user" , frienduser);
		model.addAttribute("friend",friend);
		return "normal/friend_profile";
	}
	
	
	
	//USER NOTIFICATION
	@GetMapping("/notification")
	public String notification(Model model , Principal principal) {
		
		User user = this.userRepository.getUserByEmail(principal.getName());
		System.out.println(user);
		List<Friend> friends =  this.friendRepository.getFriendByFriend_id(user.getId());
		
		model.addAttribute("user" , user);
		model.addAttribute("friend" , friends);
		System.out.println(friends);
		return "normal/notification";
	}
	
	
	//ACCEPT REQQUEST
	@GetMapping("/accept-request/{id}")
	public String acceptRequest(@PathVariable("id")Long id , Principal principal , Model model) { //taking the id of user who send request
		
		User user = this.userRepository.getUserByEmail(principal.getName()); //jiska account login h friend id ke andr aayega kuki usnko req bhehja gya h
		
		Friend friend = this.friendRepository.getFriendByFriend_idAndUser_id(user.getId(), id);
	    friend.setStatus("CONFIRMED");
	    
	    
	    //checking reverse exist or not
	    Friend friendrev1 = this.friendRepository.getFriendByFriend_idAndUser_id(id , user.getId());
//	    if(friendrev1 == null) {
//	    	Friend friendreverse = new Friend();
//		    friendreverse .setUser(user);
//		    friendreverse .setFriend(friend.getUser());
//		    friendreverse.setStatus("CONFIRMED");
//		    this.friendRepository.save(friendreverse);
//		    model.addAttribute("friend" , friendreverse);
//			return "normal/notification";
	//  }
	    
	    //getting both user of sender id and reciever id and then seeting their count
	    User frienduser = this.userRepository.findById(id);
	    user.setConnection_count(user.getConnection_count()+1);
	    frienduser.setConnection_count(frienduser.getConnection_count()+1);
	    this.userRepository.save(user);
	    this.userRepository.save(frienduser);
	    
	    
	    
	    this.friendRepository.save(friend);
		
	    List<Friend> friends = this.friendRepository.getFriendByFriend_id(user.getId());
	    model.addAttribute("friend" , friends);
		return "redirect:/user/friend-profile/{id}";
	}
	
	
	//SHOWING ALL THE CONNECTIONS
	
	@GetMapping("/my-connections/{id}")
	public String myConnections(@PathVariable("id")Long id , Model model) {
		
		User user = this.userRepository.findById(id);
		
		List<Friend> friends1 = this.friendRepository.getFriendByFriend_id(id);
		System.out.println(friends1);
		List<Friend> friends2 = this.friendRepository.getFriendByUser_id(id);
		
		List<Friend> allfriend = new ArrayList<>();
		
		allfriend.addAll(friends1);
		allfriend.addAll(friends2);
		
		model.addAttribute("friend" ,allfriend);
		model.addAttribute("user" , user);
		
		//System.out.println(allfriend);
		return "normal/connections-list";
	}
	
	

}
