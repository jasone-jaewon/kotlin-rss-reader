package rss.controller

import rss.model.BlogType
import rss.model.Post
import rss.service.PostRepository

class PostController(
    private val postRepository: PostRepository
) {
    suspend fun getNewPosts(blogType: BlogType, posts: Set<Post>): Set<Post> {
        val savedPosts = postRepository.getPosts(blogType)
        return posts - savedPosts
    }

    suspend fun savePosts(blogType: BlogType, posts: Set<Post>) {
        postRepository.savePosts(blogType, posts)
    }
}