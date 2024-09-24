package rss.controller

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import rss.service.PostService
import types.BlogPostMap

class PostController(
    private val postService: PostService
) {

    suspend fun getAndSaveNewPosts(postMap: BlogPostMap): BlogPostMap = coroutineScope {
        val newPostMap = postMap.mapValues { (blogType, posts) ->
            async {
                postService.getNewPosts(blogType, posts)
            }
        }
        // 저장
        newPostMap.forEach { (blogType, posts) ->
            launch { postService.savePosts(blogType, posts.await()) }
        }

        newPostMap.mapValues { (_, deferred) -> deferred.await() }
    }
}