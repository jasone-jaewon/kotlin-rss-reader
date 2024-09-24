package rss.controller

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import rss.model.BlogType
import rss.model.RssNodes
import rss.service.RssReader

class RssController {

    suspend fun readRssNodes(): Map<BlogType, RssNodes> = coroutineScope {
        val nodeMap = BlogType.values().associateWith { blog ->
            async {
                RssReader.read(blog.url)
            }
        }

        nodeMap.mapValues { (_, deferred) -> deferred.await() }
    }
}