package byaj.controllers;

import byaj.configs.CloudinaryConfig;
import byaj.models.*;
import byaj.repositories.*;
import byaj.validators.UserValidator;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
<<<<<<< HEAD

import com.cloudinary.utils.ObjectUtils;


import byaj.configs.CloudinaryConfig;

import javax.validation.Valid;

=======

import javax.validation.Valid;
>>>>>>> 612928ed85b517cbb38523f4a8dae806a36179ed
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
<<<<<<< HEAD
import java.io.File;
=======
>>>>>>> 612928ed85b517cbb38523f4a8dae806a36179ed

/**
 * Created by student on 7/10/17.
 */
@Controller
public class HomeController {
	
	@Autowired
	 private   CloudinaryConfig cloudc;
    @Autowired
    private UserValidator userValidator;

    @Autowired
    private byaj.services.UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;   
    

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SearchRepository searchRepository;
    
    @Autowired
    private FollowRepository followRepository;
    
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ProfileBuilderRepository profileBuilderRepository;

    @Autowired
    private PostBuilderRepository postBuilderRepository;

    @Autowired
    private CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String home(Model model){
        model.addAttribute("search", new Search());
        model.addAttribute("post", new Post());
        model.addAttribute("follow", new Follow());
        model.addAttribute("like", new Like());
        model.addAttribute("posts", postRepository.findAllByOrderByPostDateDesc());
        //model.addAttribute("userPrincipal", userRepository.findByUsername(principal.getName()));
        return "postresults2";
    }

    @RequestMapping(value="/register", method = RequestMethod.GET)
    public String showRegistrationPage(Model model){
        model.addAttribute("search", new Search());
        model.addAttribute("user", new User());
        return "register2";
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        model.addAttribute("search", new Search());

        model.addAttribute("user", user);
        userValidator.validate(user, result);

        if (result.hasErrors()) {
            return "register2";
        } else {
           /* if (user.getRoleSettings().toUpperCase().equals("ADMIN")) {
                //user.setRoles(Arrays.asList(adminRole));
                //userRepository.save(user);
                userService.saveAdmin(user);
            }
            if (user.getRoleSettings().toUpperCase().equals("EMPLOYER")) {
                //user.setRoles(Arrays.asList(employerRole));
                //userRepository.save(user);
                userService.saveEmployer(user);
            }
            if (user.getRoleSettings().toUpperCase().equals("USER")) {
                //user.setRoles(Arrays.asList(userRole));
                //userRepository.save(user);
                userService.saveUser(user);
                model.addAttribute("message", "User Account Successfully Created");
            }*/
           user.setPicUrl("http://res.cloudinary.com/andrewjonesdev/image/upload/c_scale,h_100/v1499894133/profilepic_kos4l4.jpg");
            userService.saveUser(user);
            model.addAttribute("message", "User Account Successfully Created");
        }
        return "redirect:/";
    }
    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("search", new Search());
        return "login2";
    }

    @GetMapping("/upload")
    public String uploadForm(Model model){
        model.addAttribute("search", new Search());
        return "upload2";
    }
    @PostMapping("/upload")
    public String singleImageUpload(@RequestParam("file") MultipartFile file, @RequestParam("text") String text, @RequestParam("fontSize") int fontSize, @RequestParam("scale") int scale, @RequestParam("x") int x, @RequestParam("y") int y, RedirectAttributes redirectAttributes, Model model){
        model.addAttribute("search", new Search());
        if (file.isEmpty()){
            model.addAttribute("message","Please select a file to upload");
            return "upload2";
        }
        try {
            Map uploadResult =  cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));

            System.out.println(cloudc.createUrl(file.getOriginalFilename(), Integer.parseInt(uploadResult.get("width").toString()), Integer.parseInt(uploadResult.get("height").toString()), "fill", "sepia"));
            System.out.println(uploadResult.get("width").toString());
            System.out.println(uploadResult.get("height").toString());
            System.out.println(cloudc.createUrlNoFormat(uploadResult.get("url").toString(), Integer.parseInt(uploadResult.get("width").toString()), Integer.parseInt(uploadResult.get("height").toString()), "fill", "sepia", 2));
            System.out.println(cloudc.createUrlSuper(uploadResult.get("url").toString(), Integer.parseInt(uploadResult.get("width").toString()), Integer.parseInt(uploadResult.get("height").toString()), "fill", "sepia", 2));
            System.out.println(cloudc.createUrlSuperMeme(uploadResult.get("url").toString(), Integer.parseInt(uploadResult.get("width").toString()), Integer.parseInt(uploadResult.get("height").toString()), "fill", "sepia", 2, text, fontSize,scale, x, y));
            model.addAttribute("message", "You successfully uploaded '" + file.getOriginalFilename() + "'");
            model.addAttribute("imageurl1", uploadResult.get("url"));
            //    model.addAttribute("imageurl", cloudc.createUrlNoFormat(uploadResult.get("url").toString(), Integer.parseInt(uploadResult.get("width").toString()), Integer.parseInt(uploadResult.get("height").toString()), "fill", "sepia", 2));
            //model.addAttribute("imageurl", cloudc.createUrl(file.getOriginalFilename(), Integer.parseInt(uploadResult.get("width").toString()), Integer.parseInt(uploadResult.get("height").toString()), "fill", "sepia"));
        /*master*/    model.addAttribute("imageurl2", cloudc.createUrlSuper(uploadResult.get("url").toString(), Integer.parseInt(uploadResult.get("width").toString()), Integer.parseInt(uploadResult.get("height").toString()), "fill", "sepia", 2));
        /*40x40 sepia cropped*/ model.addAttribute("imageurl3", cloudc.createUrlSuper(uploadResult.get("url").toString(), 40, 40, "pad", "sepia"));
        /*40x40 sepia*/ model.addAttribute("imageurl4", cloudc.createUrlSuper(uploadResult.get("url").toString(), 40, 40, "sepia"));
        /*fixed height width auto*/ model.addAttribute("imageurl5", cloudc.createUrlSuper(uploadResult.get("url").toString(), 100));
        /*master*/    model.addAttribute("imageurl6", cloudc.createUrlSuperMeme(uploadResult.get("url").toString(), Integer.parseInt(uploadResult.get("width").toString()), Integer.parseInt(uploadResult.get("height").toString()), "fill", "sepia", 2, text, fontSize, scale, x, y));

        } catch (IOException e){
            e.printStackTrace();
            model.addAttribute("message", "Sorry I can't upload that!");
        }
        return "upload2";
    }

    @GetMapping("/post")
    public String newPost(Model model, Principal principal, RedirectAttributes redirectAttributes) {
        model.addAttribute("search", new Search());
        model.addAttribute("post", new Post());
        model.addAttribute("posts", postRepository.findAllByPostUserOrderByPostDateDesc(userRepository.findByUsername(principal.getName()).getId()));
        model.addAttribute("follow", new Follow());
        model.addAttribute("like", new Like());
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        model.addAttribute("userPrincipal", userRepository.findByUsername(principal.getName()));
        return "post2";
    }
    @PostMapping("/profile/picture")
    public String singleImageUpload(@RequestParam("fileProfile") MultipartFile fileProfile, Model model, Principal principal, RedirectAttributes redirectAttributes){
        model.addAttribute("search", new Search());
        if (fileProfile.isEmpty()){
            model.addAttribute("message","Please select a file to upload");
            model.addAttribute("post", new Post());
            model.addAttribute("posts", postRepository.findAllByPostUserOrderByPostDateDesc(userRepository.findByUsername(principal.getName()).getId()));
            model.addAttribute("follow", new Follow());
            model.addAttribute("like", new Like());
            model.addAttribute("user", userRepository.findByUsername(principal.getName()));
            model.addAttribute("userPrincipal", userRepository.findByUsername(principal.getName()));
            return "post2";
        }
        try {
            Map uploadResult = cloudc.upload(fileProfile.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            userRepository.findByUsername(principal.getName()).setPicUrl(cloudc.createUrlSuperProfile(uploadResult.get("url").toString(), 100, "scale", 2));
            userRepository.findByUsername(principal.getName()).setPicDate();
            userRepository.save(userRepository.findByUsername(principal.getName()));
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Sorry I can't upload that!");
        }
        model.addAttribute("post", new Post());
        model.addAttribute("posts", postRepository.findAllByPostUserOrderByPostDateDesc(userRepository.findByUsername(principal.getName()).getId()));
        model.addAttribute("follow", new Follow());
        model.addAttribute("like", new Like());
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        model.addAttribute("userPrincipal", userRepository.findByUsername(principal.getName()));
        return "post2";
}
    @PostMapping(path = "/post")
<<<<<<< HEAD
    public String processPost(@Valid Post post, BindingResult bindingResult,@RequestParam("file") MultipartFile file,Principal principal, RedirectAttributes redirectAttributes,
            Model model) {
        if (post.getPostName().equals("!=null")||bindingResult.hasErrors()) {
=======
    public String processPost(@Valid Post post, BindingResult bindingResult, @RequestParam("filePost") MultipartFile filePost, Principal principal) {
        if (bindingResult.hasErrors()) {
>>>>>>> 612928ed85b517cbb38523f4a8dae806a36179ed
            System.out.println("post");
            return "redirect:/post";
        }
        
        if (file.isEmpty()){
            redirectAttributes.addFlashAttribute("message","Please select a file to upload");
            return "redirect:uploadStatus";
        }
<<<<<<< HEAD

        try {
            Map uploadResult =  cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));

            model.addAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
            model.addAttribute("imageurl", uploadResult.get("url"));
            String filename = uploadResult.get("public_id").toString() + "." + uploadResult.get("format").toString();
           
            model.addAttribute("sizedimageurl", cloudc.createUrl(filename,100,150, "fit")); 
            model.addAttribute("cropped4040imageurl", cloudc.createCroppedSepia(filename,40,40, "crop", "sepia"));
            model.addAttribute("sizedimageurl", cloudc.createCropped(filename,40,40, "crop"));
            model.addAttribute("uncroppedimageurl", cloudc.createUnCropped(filename,40,40));
            model.addAttribute("fixedheightimageurl", cloudc.createfixedimage(filename,40));  
            
            
            
        } catch (IOException e){
            e.printStackTrace();
            model.addAttribute("message", "Sorry I can't upload that!");
        }
        
=======
        try {
            if (!filePost.isEmpty()) {
                try {
                    Map uploadResult = cloudc.upload(filePost.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
                    post.setPicUrl(cloudc.createUrlSuperPost(uploadResult.get("url").toString(), 100, "scale", 2));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                post.setPicUrl("http://res.cloudinary.com/andrewjonesdev/image/upload/c_fill,h_100,w_100/v1499897311/Empty_xay49d.png");
            }
        }
        catch(Exception e){
            System.out.println("null file");
            post.setPicUrl("http://res.cloudinary.com/andrewjonesdev/image/upload/c_fill,h_100,w_100/v1499897311/Empty_xay49d.png");
        }

>>>>>>> 612928ed85b517cbb38523f4a8dae806a36179ed
        post.setPostUser(userRepository.findByUsername(principal.getName()).getId());
        post.setPostAuthor(userRepository.findByUsername(principal.getName()).getUsername());
        postRepository.save(post);
        return "redirect:/post";

    }

            	
        //}
    	/*else if(post.getImage().equals("!=null"))
    	{*/
      /*  if(file.isEmpty()){
            redirectAttributes.addFlashAttribute("message","Please select a file to upload");
            return "redirect:/post";
        }

        try {
            Map uploadResult =  cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));

            model.addAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
            model.addAttribute("imageurl", uploadResult.get("url"));
            String filename = uploadResult.get("public_id").toString() + "." + uploadResult.get("format").toString();
           
            model.addAttribute("sizedimageurl", cloudc.createUrl(filename,100,150, "fit")); 
            model.addAttribute("cropped4040imageurl", cloudc.createCroppedSepia(filename,40,40, "crop", "sepia"));
            model.addAttribute("sizedimageurl", cloudc.createCropped(filename,40,40, "crop"));
            model.addAttribute("uncroppedimageurl", cloudc.createUnCropped(filename,40,40));
            model.addAttribute("fixedheightimageurl", cloudc.createfixedimage(filename,40));
            
            
            
        } catch (IOException e){
            e.printStackTrace();
            model.addAttribute("message", "Sorry I can't upload that!");
        }
     */
    public UserValidator getUserValidator() {
        return userValidator;
    }

    public void setUserValidator(UserValidator userValidator) {
        this.userValidator = userValidator;
    }
        
   @GetMapping("/upload")
    public String uploadForm(){
        return "upload";
    }

    @PostMapping("/upload")
    public String singleImageUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model, Principal principal){
    	model.addAttribute("search", new Search());
        model.addAttribute("post", new Post());
        model.addAttribute("posts", postRepository.findAllByPostUserOrderByPostDateDesc(userRepository.findByUsername(principal.getName()).getId()));
        model.addAttribute("follow", new Follow());
        if (file.isEmpty()){
            redirectAttributes.addFlashAttribute("message","Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {
            Map uploadResult =  cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));

            model.addAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
            model.addAttribute("imageurl", uploadResult.get("url"));
            String filename = uploadResult.get("public_id").toString() + "." + uploadResult.get("format").toString();
           
            model.addAttribute("sizedimageurl", cloudc.createUrl(filename,100,150, "fit")); 
            model.addAttribute("cropped4040imageurl", cloudc.createCroppedSepia(filename,40,40, "crop", "sepia"));
            model.addAttribute("sizedimageurl", cloudc.createCropped(filename,40,40, "crop"));
            model.addAttribute("uncroppedimageurl", cloudc.createUnCropped(filename,40,40));
            model.addAttribute("fixedheightimageurl", cloudc.createfixedimage(filename,40));  
            
            
            
        } catch (IOException e){
            e.printStackTrace();
            model.addAttribute("message", "Sorry I can't upload that!");
        }
        return "upload";
    }

    @PostMapping("/search")
    public String searchForResumes(@Valid Search search, BindingResult bindingResult, Principal principal, Model model){
        if (bindingResult.hasErrors()) {
            System.out.println("search");
            return "redirect:/";
        }
        model.addAttribute("search", new Search());
        model.addAttribute("profileBuilder", new ProfileBuilder());
        model.addAttribute("postBuilder", new PostBuilder());
        model.addAttribute("userPrincipal", userRepository.findByUsername(principal.getName()));
        search.setSearchUser(userRepository.findByUsername(principal.getName()).getId());
        search.setSearchAuthor(userRepository.findByUsername(principal.getName()).getUsername());
        searchRepository.save(search);
        if(search.getSearchType().toLowerCase().equals("username")){
            model.addAttribute("posts", postRepository.findAllByPostAuthorOrderByPostDateDesc(search.getSearchValue()));
            model.addAttribute("follow", new Follow());
            model.addAttribute("like", new Like());
            return "postresults2";
        }
       /* if(search.getSearchType().toLowerCase().equals("company")){
            ArrayList<User> result = new ArrayList();
            List<Work> comp = workRepository.findAllByWorkEmployerOrderByWorkResAsc(search.getSearchValue());
            for (int count = 0; count< comp.size(); count++){
                result.add(userRepository.findById(comp.get(count).getWorkRes()));
            }
            model.addAttribute("results", result);
            return "searchResults2";
        }
        if(search.getSearchType().toLowerCase().equals("school")){
            ArrayList<User> result = new ArrayList();
            List<Education> comp = educationRepository.findAllByEduSchoolOrderByEduResAsc(search.getSearchValue());
            for (int count = 0; count< comp.size(); count++){
                result.add(userRepository.findById(comp.get(count).getEduRes()));
            }
            model.addAttribute("results", result);
            return "searchResults2";
        }
        if(search.getSearchType().toLowerCase().equals("jobtitle")){
            ArrayList<User> job = new ArrayList();
            //List<Job> comp = jobRepository.findAllByJobTitleOrderByJobStartYearDesc(search.getSearchValue());
            model.addAttribute("jobs", jobRepository.findAllByJobTitleOrderByJobStartYearDescJobStartMonthDesc(search.getSearchValue()));
            return "jobResults2";
        }*/
        return "redirect:/";
    }

    //Follow Post Mapping

    @PostMapping("/follow")
    public String changeFollowStatus(@Valid Follow follow, BindingResult bindingResult, Principal principal, Model model){
        if(bindingResult.hasErrors()){
            return "redirect:/";
        }
        if(follow.getFollowType().toLowerCase().equals("follow")){
            userService.followUser(userRepository.findByUsername(follow.getFollowValue()), userRepository.findByUsername(principal.getName()));
        }
        if(follow.getFollowType().toLowerCase().equals("unfollow")){
            userService.unfollowUser(userRepository.findByUsername(follow.getFollowValue()), userRepository.findByUsername(principal.getName()));
        }
        follow.setFollowUser(userRepository.findByUsername(principal.getName()).getId());
        follow.setFollowAuthor(userRepository.findByUsername(principal.getName()).getUsername());
        followRepository.save(follow);
        System.out.println(userRepository.findByUsername(principal.getName()).getUsername());
        System.out.println(userRepository.findByUsername(follow.getFollowValue()).getUsername());
        return "redirect:/";
    }

    //Following Post Mapping

    @GetMapping("/following")
    public String viewFollowing(Model model, Principal principal){
        model.addAttribute("search", new Search());
        model.addAttribute("profileBuilder", new ProfileBuilder());
        model.addAttribute("userPrincipal", userRepository.findByUsername(principal.getName()));
        model.addAttribute("users", userRepository.findByUsername(principal.getName()).getFollowing());
        model.addAttribute("follow", new Follow());
        return "userresults2";
    }
    @GetMapping("/followers")
    public String viewFollowers(Model model, Principal principal){
        model.addAttribute("search", new Search());
        model.addAttribute("profileBuilder", new ProfileBuilder());
        model.addAttribute("userPrincipal", userRepository.findByUsername(principal.getName()));
        model.addAttribute("users", userRepository.findByUsername(principal.getName()).getFollowed());
        model.addAttribute("follow", new Follow());
        return "userresults2";
    }

    @PostMapping("/like")
    public String changeLikeStatus(@Valid Like like, BindingResult bindingResult, Principal principal, Model model){
        if(bindingResult.hasErrors()){
            return "redirect:/";
        }
        if(like.getLikeType().toLowerCase().equals("like")){
            userService.likePost(postRepository.findByPostID(Integer.parseInt(like.getLikeValue())), userRepository.findByUsername(principal.getName()));
        }
        if(like.getLikeType().toLowerCase().equals("unlike")){
            userService.unlikePost(postRepository.findByPostID(Integer.parseInt(like.getLikeValue())), userRepository.findByUsername(principal.getName()));
        }
        like.setLikeUser(userRepository.findByUsername(principal.getName()).getId());
        like.setLikeAuthor(userRepository.findByUsername(principal.getName()).getUsername());
        likeRepository.save(like);
        return "redirect:/";
    }
    
    @GetMapping("/users")
    public String viewUsers(Model model, Principal principal){
        model.addAttribute("search", new Search());
        model.addAttribute("profileBuilder", new ProfileBuilder());
        model.addAttribute("userPrincipal", userRepository.findByUsername(principal.getName()));
        model.addAttribute("users", userRepository.findAllByOrderByUserDateDesc());
        model.addAttribute("follow", new Follow());
        return "userresults2";
    }
    @PostMapping("/generate/posts")
    public String generatePosts(@Valid ProfileBuilder profileBuilder, BindingResult bindingResult, Model model, Principal principal){
        if(bindingResult.hasErrors()){
            return "redirect:/";
        }
        model.addAttribute("search", new Search());
        model.addAttribute("posts", postRepository.findAllByPostAuthorOrderByPostDateDesc(profileBuilder.getProfileBuilderValue()));
        model.addAttribute("follow", new Follow());
        model.addAttribute("like", new Like());
        model.addAttribute("user", userRepository.findByUsername(profileBuilder.getProfileBuilderValue()));
        model.addAttribute("userPrincipal", userRepository.findByUsername(principal.getName()));
        profileBuilder.setProfileBuilderUser(userRepository.findByUsername(principal.getName()).getId());
        profileBuilder.setProfileBuilderAuthor(userRepository.findByUsername(principal.getName()).getUsername());
        profileBuilderRepository.save(profileBuilder);
        return "post2";
    }

}
