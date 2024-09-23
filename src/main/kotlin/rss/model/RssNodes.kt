package rss.model

import org.w3c.dom.Element
import org.w3c.dom.NodeList
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class RssNodes(
    private val nodes: NodeList
) {

    fun toPosts(): List<Post> {
        return (0 until nodes.length).map { i ->
            val item = nodes.item(i) as Element
            val title = item.getElementsByTagName("title").item(0).textContent
            val link = item.getElementsByTagName("link").item(0).textContent
            val createdBy = item.getElementsByTagName("dc:creator").item(0).textContent
            val createdAt = item.getElementsByTagName("pubDate").item(0).textContent

            val formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z")
            val at = ZonedDateTime.parse(createdAt, formatter)

            Post(title, link, at, createdBy)
        }
    }
}