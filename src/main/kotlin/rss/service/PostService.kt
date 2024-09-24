package rss.service

import rss.model.BlogType
import rss.model.Post
import rss.repository.PostRepository

class PostService(private val postRepository: PostRepository) {

    suspend fun getNewPosts(blogType: BlogType, posts: Set<Post>): Set<Post> {
        val savedPosts = postRepository.getPosts(blogType)
        return posts - savedPosts
    }

    suspend fun savePosts(blogType: BlogType, posts: Set<Post>) {
        postRepository.savePosts(blogType, posts)
    }

    suspend fun getPosts(blogType: BlogType): Set<Post> {
        return postRepository.getPosts(blogType)
    }
}