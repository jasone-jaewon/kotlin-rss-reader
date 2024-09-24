import kotlinx.coroutines.delay
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
        // rss read
        var postMap = rssController.readRssNodes()
            .mapValues { it.value.toPosts() }

        val posts = postMap.values.flatten().toList().sortedByDescending { it.createdAt }

        // 게시글을 읽어와서 출력
        println("##############################")
        posts.forEachIndexed { index, post ->
            println("id: $index $post")
        }

        while (true) {
            postMap = rssController.readRssNodes()
                .mapValues { it.value.toPosts() }

            // rss node 를 post로 저장
            val newPostMap = postController.saveNewPostsIfExists(postMap)
            newPostMap.forEach { (blog, posts) ->
                if (posts.isEmpty()) {
                    println("$blog 의 새로운 게시글이 없습니다.")
                    return@forEach
                }

                println("##############################")
                println("$blog have new posts.")
                posts.forEach { post ->
                    println("$post")
                }
            }
            delay(1000)
        }
    }
}