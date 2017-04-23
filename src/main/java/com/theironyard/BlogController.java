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
        return "loginPage";
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
        return "badLogin";
    }

    // Creates new user, checks if username was taken
    @RequestMapping("/newUser")
//    @GetMapping("/newUser")
    public String newUser(Model model, String username, String password1, String password2) {
        if (username == null || password1 == null || password2 == null){
            return "/newUser";
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
        return "/badNewUser";
    }



    // Home Page of the blog
    @RequestMapping("/homePage")
    public String homePage(Model model,@ModelAttribute("userId") Integer userId, @RequestParam(defaultValue = "") String search){
        // to say hello to username
        model.addAttribute("user",personRepository.selectPerson(userId));
        // list of posts with search parameter
        model.addAttribute("blogList",repositoryService.peoplePostList(blogRepository.listBlogs(search)));
        return "/homePage";
    }

    // Creating a blog
    @RequestMapping("/createBlog")
    public String createBlog(){
        return "createBlog";
    }

    // Save blog
    @RequestMapping("/saveBlog")
    public String saveBlog(@ModelAttribute("userId") Integer userId, String title, String post){
        String time = LocalDateTime.now().toString();
        Blog blog = new Blog(userId, title, post, time);
        blogRepository.addBlogPost(blog);
        return "redirect:/viewMyBlogs";
    }

    // View User's blogs
    @RequestMapping("/viewMyBlogs")
    public String viewMyBlogs(Model model, @ModelAttribute("userId") Integer userId){
        model.addAttribute("myBlogs",blogRepository.listMyBlogs(userId));
        return "/viewMyBlogs";
    }

    // View a blog post
    @RequestMapping("/viewBlogPost")
    public String viewBlogPost(Model model, Integer blogId){
        model.addAttribute("blogPost",repositoryService.usernameOfPost(blogId));
        return "viewBlogPost";
    }

    // Edit Delete ONLY IF YOU ARE THE AUTHOR
    @RequestMapping("/editDeletePost")
    public String editDelete(Blog blog){
        // need to check that person_id of post belongs to
        return "redirect:/viewBlogPost";
    }


    // logout
    @RequestMapping(path = "/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/loginPage";
    }

}
