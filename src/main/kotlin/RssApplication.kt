import rss.RssReader

val SUBSCRIBE_URLS = listOf(
    "https://techblog.lycorp.co.jp/ko/feed/index.xml",
    "https://tech.devsisters.com/rss.xml",
    "https://techblog.woowahan.com/feed",
)

fun main() {
    val posts = SUBSCRIBE_URLS.map { url ->
        RssReader.read(url)
    }.flatMap { rssNodes ->
        rssNodes.toPosts()
    }.sortedByDescending { it.createdAt }

    posts.forEachIndexed { index, post ->
        println("id: $index $post")
    }
}