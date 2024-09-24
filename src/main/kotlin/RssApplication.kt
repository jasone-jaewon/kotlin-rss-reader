import kotlinx.coroutines.runBlocking
import rss.controller.RssController
import rss.model.BlogType
import rss.model.Post
import kotlin.system.measureTimeMillis

val blogPostMap: Map<BlogType, Set<Post>> = mutableMapOf()

val rssController = RssController()

fun main() {
    runBlocking {
        val times = measureTimeMillis {
            // rss read
            val rssNodeMap = rssController.readRssNodes()

            // rss node 를 post로 저장

            val rssPostMap = rssNodeMap.mapValues { (key, value) ->
                value.toPosts()
            }

            val newPostMap = BlogType.values().associateWith { blog ->
                val newPosts = rssPostMap[blog].orEmpty() - blogPostMap[blog].orEmpty()
                // TODO: 저장
//                blogPostMap[blog] = rssPostMap[blog].toSet()
                newPosts
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