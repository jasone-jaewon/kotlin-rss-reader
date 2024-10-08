package rss.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.w3c.dom.Element
import rss.model.RssNodes
import javax.xml.parsers.DocumentBuilderFactory

object RssReader {

    private const val FIRST_INDEX = 0
    private const val CHANNEL_TAG_NAME = "channel"
    private const val ITEM_TAG_NAME = "item"
    suspend fun read(url: String): RssNodes {
        val factory = DocumentBuilderFactory.newInstance()
        val xml = withContext(Dispatchers.IO) {
            factory.newDocumentBuilder().parse(url)
        }
        val channel = xml.getElementsByTagName(CHANNEL_TAG_NAME).item(FIRST_INDEX) as Element
        val itemNodes = channel.getElementsByTagName(ITEM_TAG_NAME)
        return RssNodes(itemNodes)
    }
}