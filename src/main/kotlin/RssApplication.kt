import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import rss.controller.PostController
import rss.controller.RssController
import rss.repository.PostRepository
import rss.service.PostService
import kotlin.system.measureTimeMillis

val rssController = RssController()
val postRepository = PostRepository()
val postService = PostService(postRepository)
val postController = PostController(postService)

fun main() {
    runBlocking {
        val times = measureTimeMillis {
            // rss read
            val postMap = rssController.readRssNodes()
                .mapValues { it.value.toPosts() }

            // rss node 를 post로 저장
            val newPostMap = postController.getAndSaveNewPosts(postMap)

            newPostMap.forEach { (blog, posts) ->
                println("##############################")
                println("$blog have new posts.")
                posts.forEach { post ->
                    println("$post")
                }
            }

            val posts = postMap.values.flatten().toList().sortedByDescending { it.createdAt }
            // 게시글을 읽어와서 출력
            println("##############################")
            posts.forEachIndexed { index, post ->
                println("id: $index $post")
            }
        }
        println(times)
    }
}