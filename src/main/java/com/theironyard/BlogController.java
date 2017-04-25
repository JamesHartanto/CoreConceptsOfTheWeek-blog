package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * Created by JamesHartanto on 4/20/17.
 */
@Controller
@SessionAttributes("userId")
public class BlogController {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    BlogRepository blogRepository;

    @ModelAttribute("userId")
    public Integer userId(){
        return 0;
    }

    // Provide login details
    @RequestMapping("/loginPage")
//    @GetMapping("/loginPage")
    public String loginPage(){
        return "Login/loginPage";
    }

    // Check if inputs are acceptable
    @RequestMapping("/login")
//    @PostMapping("/login")
    public String login(Model model, String username, String password){
        if (repositoryService.usernameList(personRepository.listPeople()).contains(username)){
            // Check the password is correct
            Person personToCheck = personRepository.findPerson(username);
            if (personToCheck.getPassword().equals(password)){
                // gives the user a unique session id
                model.asMap().put("userId",personToCheck.getId());
                return "redirect:/homePage";
            }
        }
        return "redirect:/badLogin";
    }

    // login page with error message
    @RequestMapping("/badLogin")
    public String badLogin(){
        return "Login/badLogin";
    }

    // Creates new user, checks if username was taken
    @RequestMapping("/newUser")
//    @GetMapping("/newUser")
    public String newUser(Model model, String username, String password1, String password2) {
        if (username == null || password1 == null || password2 == null){
            return "Login//newUser";
        }
        // Checks to see unique username and validity of password inputs
        if (repositoryService.uniqueUsername(username, repositoryService.usernameList(personRepository.listPeople()))
                && (password1.equals(password2))) {
            Person person = new Person(username,password1);
            personRepository.createPerson(person);
            return "redirect:/loginPage";
        }
        return "redirect:/badNewUser";
    }

    // new user with bad input error message
    @RequestMapping("/badNewUser")
    public String badNewUser() {
        return "Login//badNewUser";
    }



    // ** INDIVIDUAL BLOGS **

    // Home Page of the blog
    @RequestMapping("/homePage")
    public String homePage(Model model,@ModelAttribute("userId") Integer userId,
                           @RequestParam(defaultValue = "") String search){
        // to say hello to username
        model.addAttribute("user",personRepository.selectPerson(userId));
        // list of posts with search parameter
        model.addAttribute("postList",blogRepository.listPosts(search));
        // make search string persist
        model.addAttribute("search",search);
        return "BlogPost//homePage";
    }

    // Creating a blog
    @RequestMapping("/createBlog")
    public String createBlog(){
        return "BlogPost/createBlog";
    }

    // Save blog
    @RequestMapping("/saveBlog")
    public String saveBlog(@ModelAttribute("userId") Integer userId, String title, String post){
        String time = LocalDateTime.now().toString();
        Person person = new Person(userId);
        Post blog = new Post(person, title, post, time);
        blogRepository.addBlogPost(blog);
        return "redirect:/viewMyBlogs";
    }

    // View the User's blog posts
    @RequestMapping("/viewMyBlogs")
    public String viewMyBlogs(Model model, @ModelAttribute("userId") Integer userId){
        model.addAttribute("myBlogs",blogRepository.listMyBlogs(userId));
        return "BlogPost//viewMyBlogs";
    }

    // View another user's blog posts
    @RequestMapping("/viewPersonBlogPosts")
    public String viewPersonBlogPosts(Model model,String username){
        model.addAttribute("AUserBlogList", blogRepository.selectPersonPosts(username));
        model.addAttribute("username",username);
        return "BlogPost/viewPersonBlogPosts";
    }

    // View a blog post
    @RequestMapping("/viewBlogPost")
    public String viewBlogPost(Model model, Integer post_id){
        model.addAttribute("blogPost",blogRepository.viewABlog(post_id));
        return "BlogPost/viewBlogPost";
    }

    // Edit Delete ONLY IF YOU ARE THE AUTHOR
    @RequestMapping("/editDeleteBlogPost")
    public String editDelete(Model model, Integer id,@ModelAttribute("userId") Integer userId){
        Post post = blogRepository.viewABlog(id);
        if (post.getAuthor().getId() == userId){
            model.addAttribute("blogPost",blogRepository.viewABlog(id));
            return "BlogPost/editDeleteBlogPost";
        }
        // not allowed to edit
        return "redirect:/homePage";
    }

    // Edit
    @RequestMapping("/editPost")
    public String editPost(String title, String post, Integer id, @ModelAttribute("userId") Integer userId){
        String date = LocalDateTime.now().toString();
        Person person = new Person(userId);
        Post blog = new Post(id,person,title,post,date);
        blogRepository.editBlogPost(blog);
        return "redirect:/viewMyBlogs";
    }

    // Delete
    @RequestMapping("/deletePost")
    public String deletePost(Integer id){
        blogRepository.deleteBlogPost(id);
        return "redirect:/viewMyBlogs";
    }

    // COMMENTS FOR POSTS
    // Adding comment
    @RequestMapping("/addComment")
    public String addComment(Integer post_id, @ModelAttribute("userId") Integer userId, String comment){
//        post_id,author_id,text,date
        String time = LocalDateTime.now().toString();
        blogRepository.addComment(post_id, userId, comment, time);
        return "redirect:/viewBlogPost?post_id="+ post_id.toString();
    }




    // logout
    @RequestMapping(path = "/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/loginPage";
    }

}
