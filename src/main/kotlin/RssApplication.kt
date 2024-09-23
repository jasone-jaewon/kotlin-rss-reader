import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import rss.RssReader
import rss.model.RssNodes
import kotlin.system.measureTimeMillis

val SUBSCRIBE_URLS = listOf(
    "https://techblog.lycorp.co.jp/ko/feed/index.xml",
    "https://tech.devsisters.com/rss.xml",
    "https://techblog.woowahan.com/feed",
)

fun main() {
    runBlocking {
        val times = measureTimeMillis {
            val deferredPosts = SUBSCRIBE_URLS.map { url ->
                async {
                    RssReader.read(url)
                }
            }

            val rssNodes = deferredPosts.awaitAll()
            val posts = rssNodes.flatMap { it.toPosts() }
                .sortedByDescending { it.createdAt }
            posts.forEachIndexed { index, post ->
                println("id: $index $post")
            }
        }
        println(times)
    }
}