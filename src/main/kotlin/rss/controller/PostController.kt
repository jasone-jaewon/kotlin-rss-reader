package rss.controller

import rss.model.BlogType
import rss.model.Post
import rss.service.PostRepository

class PostController(
    private val postRepository: PostRepository
) {
    suspend fun getNewPosts(blogType: BlogType, posts: Set<Post>): Set<Post> {
        val savedPosts = postRepository.getPosts(blogType)
        val newPosts = posts - savedPosts
        // TODO: async
        postRepository.savePosts(blogType, newPosts)
        return newPosts
    }
}