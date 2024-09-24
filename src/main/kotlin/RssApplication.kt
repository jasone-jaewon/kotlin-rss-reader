import kotlinx.coroutines.runBlocking
import rss.controller.PostController
import rss.controller.RssController
import rss.service.PostRepository
import kotlin.system.measureTimeMillis

val rssController = RssController()
val postRepository = PostRepository()
val postController = PostController(postRepository)

fun main() {
    runBlocking {
        val times = measureTimeMillis {
            // rss read
            val postMap = rssController.readRssNodes()
                .mapValues { it.value.toPosts() }

            // rss node 를 post로 저장
            val newPostMap = postMap.mapValues { (blogType, posts) ->
                postController.getNewPosts(blogType, posts)
            }

            // 저장
            newPostMap.forEach { (blogType, posts) ->
                postController.savePosts(blogType, posts)
            }

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