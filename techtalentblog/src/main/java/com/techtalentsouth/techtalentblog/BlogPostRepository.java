package com.techtalentsouth.techtalentblog;

import org.springframework.data.repository.CrudRepository;

public interface BlogPostRepository extends CrudRepository<BlogPost, Long> {
    
}