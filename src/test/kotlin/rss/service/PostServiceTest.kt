package rss.service

import io.kotest.common.runBlocking
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import rss.model.BlogType
import rss.repository.PostRepository
import util.testPost
import java.time.ZonedDateTime


class PostServiceTest {
    private val blogType = BlogType.LINE

    companion object {
        lateinit var postService: PostService

        @JvmStatic
        @BeforeAll
        fun setUp() {
            postService = PostService(PostRepository())
        }
    }

    @BeforeEach
    fun init() {
        val now = ZonedDateTime.now()
        val posts = setOf(
            testPost(title = "게시글1", createdAt = now.minusMinutes(1L)),
            testPost(title = "게시글2", createdAt = now.minusMinutes(2L)),
            testPost(title = "게시글3", createdAt = now.minusMinutes(3L)),
            testPost(title = "게시글4", createdAt = now.minusMinutes(4L)),
        )

        runBlocking { postService.savePosts(blogType, posts) }
    }


    @Test
    fun `게시글 등록 test`() = runTest {
        // given
        val now = ZonedDateTime.now()
        val expected = setOf(
            testPost(title = "등록 게시글1", createdAt = now.minusMinutes(1L)),
            testPost(title = "등록 게시글2", createdAt = now.minusMinutes(1L)),
        )

        // when
        postService.savePosts(blogType, expected)

        // then
        val actual = postService.getPosts(blogType)
        actual shouldContainAll expected
    }


    @Test
    fun `새로운 게시글 조회 기능 test`() = runTest {
        // given
        val now = ZonedDateTime.now()
        val expected = setOf(
            testPost(title = "신규 게시글1", createdAt = now.minusMinutes(1L)),
            testPost(title = "신규 게시글2", createdAt = now.minusMinutes(2L)),
        )

        // when
        val newPosts = postService.getNewPosts(BlogType.LINE, expected)

        // then
        newPosts shouldBe expected
    }
}