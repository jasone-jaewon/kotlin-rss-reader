import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import rss.RssReader
import rss.model.BlogType
import rss.model.Post
import kotlin.system.measureTimeMillis

val blogPostMap: Map<BlogType, Set<Post>> = mutableMapOf()


fun main() {
    runBlocking {
        val times = measureTimeMillis {
            // rss read
            val deferredPostMap = BlogType.values().associateWith { blog ->
                val postReadJob = async {
                    RssReader.read(blog.url)
                }
                postReadJob.await()
            }


            val rssPostMap = deferredPostMap.mapValues { (key, value) ->
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