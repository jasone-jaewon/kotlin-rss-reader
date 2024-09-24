package rss.repository

import rss.model.BlogType
import rss.model.Post


class PostRepository {
    private val blogPostMap: MutableMap<BlogType, Set<Post>> = mutableMapOf()

    suspend fun savePosts(blogType: BlogType, posts: Set<Post>) {
        val savedPosts = blogPostMap[blogType].orEmpty()
        blogPostMap[blogType] = savedPosts + posts
    }

    suspend fun getPosts(blogType: BlogType): Set<Post> {
        return blogPostMap[blogType].orEmpty()
    }
}