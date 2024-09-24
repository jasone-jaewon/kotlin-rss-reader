package rss

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import rss.service.RssReader

class RssReaderTest {
    @Test
    fun `rss를 정상적으로 읽는다`() = runTest {
        // given
        val url = "https://techblog.woowahan.com/feed"

        // when
        RssReader.read(url)
    }
}