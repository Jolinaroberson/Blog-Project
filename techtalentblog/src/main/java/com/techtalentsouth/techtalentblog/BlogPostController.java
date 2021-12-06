package com.techtalentsouth.techtalentblog;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Optionals;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BlogPostController {

    @Autowired
    private BlogPostRepository blogPostRepository;
    private static List<BlogPost> posts = new ArrayList<>();

    @GetMapping("/")
    public String index(BlogPost blogPost, Model model) {
	    
        model.addAttribute("posts",posts);
        posts.removeAll(posts);
    for (BlogPost post : blogPostRepository.findAll()) {
    	posts.add(post);
    }
    return "blogpost/index";
    }

    @GetMapping("/blogposts/new")
    public String newBlog(BlogPost blogPost) {
        return "blogpost/new";
    }

    @PostMapping("/blogposts")
    public String addNewBlogPost(BlogPost blogPost, Model model) {
        blogPostRepository.save(blogPost);
        // (new BlogPost(blogPost.getTitle(), blogPost.getAuthor(),
        // blogPost.getBlogEntry()));
        posts.add(blogPost);
        model.addAttribute("title", blogPost.getTitle());
        model.addAttribute("author", blogPost.getAuthor());
        model.addAttribute("blogEntry", blogPost.getBlogEntry());
        return "blogpost/result";
    }

    @RequestMapping(value = "/blogposts/{id}", method = RequestMethod.GET)
    public String editPostWithId(@PathVariable Long id, BlogPost blogPost, Model model) {
        Optional<BlogPost> post = blogPostRepository.findById(id);
        if (post.isPresent()) {
            BlogPost actualBlogPost = post.get();
            model.addAttribute("blogPost", actualBlogPost);
        }
        return "blogpost/edit";

    }

    @RequestMapping(value = "/blogposts/update/{id}")
    public String updateExistingPost(@PathVariable Long id, BlogPost blogPost, Model model) {
        Optional<BlogPost> post = blogPostRepository.findById(id);
        if (post.isPresent()) {
            BlogPost actualPost = post.get();
            actualPost.setTitle(blogPost.getTitle());
            actualPost.setAuthor(blogPost.getAuthor());
            actualPost.setBlogEntry(blogPost.getBlogEntry());
            blogPostRepository.save(actualPost);
            model.addAttribute("title", actualPost.getTitle());
            model.addAttribute("author", actualPost.getAuthor());
            model.addAttribute("blogEntry", actualPost.getBlogEntry());
        }

        return "blogpost/result";
    }
    @DeleteMapping("/blogposts/{id}")
        public String deletePostWithId(@PathVariable Long id, Model model){
            model.addAttribute("id", id);
            return "blogpost/delete";
        }
        
    }
