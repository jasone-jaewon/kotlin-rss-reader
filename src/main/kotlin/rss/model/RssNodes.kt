package rss.model

import org.w3c.dom.Element
import org.w3c.dom.NodeList
import util.parse

class RssNodes(
    private val nodes: NodeList
) {

    fun toPosts(): List<Post> {
        return (0 until nodes.length).map { i ->
            val item = nodes.item(i) as Element
            val title = item.getElementsByTagName("title").item(0).textContent
            val link = item.getElementsByTagName("link").item(0).textContent
            val creator = item.getElementsByTagName("dc:creator")
            val createdBy = if (creator.length > 0) {
                creator.item(0).textContent
            } else {
                null
            }

            val createdAt = item.getElementsByTagName("pubDate").item(0).textContent
            Post(title, link, parse(createdAt), createdBy)
        }
    }
}