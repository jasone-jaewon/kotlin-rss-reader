import kotlinx.coroutines.runBlocking
import rss.controller.PostController
import rss.controller.RssController
import rss.model.BlogType
import rss.model.Post
import rss.service.PostRepository
import kotlin.system.measureTimeMillis

val blogPostMap: Map<BlogType, Set<Post>> = mutableMapOf()

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
            val newPostMap = postMap.mapValues {(blogType, posts) ->
                postController.getNewPosts(blogType, posts)
            }

            newPostMap.forEach{ (blog, posts) ->
                println("$blog have new posts.")
                println(posts)
            }


            // 게시글을 읽어와서 출력
//            posts.forEachIndexed { index, post ->
//                println("id: $index $post")
//            }
        }
        println(times)
    }
}