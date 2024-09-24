package rss.controller

import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import rss.model.BlogType
import rss.service.PostService
import util.testPost

class PostControllerTest {

    companion object {
        lateinit var postController: PostController
        private val postService = mockk<PostService>()
        val blogType = BlogType.LINE

        @JvmStatic
        @BeforeAll
        fun setUp() {
            postController = PostController(postService)
        }
    }

    @Test
    fun `신규 게시글이 존재하면 저장로직을 실행한다`() = runTest {
        // given
        val expected = setOf(testPost(title = "신규 게시글"))
        val postMap = mapOf(blogType to expected)
        coEvery { postService.getNewPosts(blogType, expected) } returns expected
        coJustRun { postService.savePosts(blogType, expected) }

        // when
        val actual = postController.saveNewPostsIfExists(postMap)

        // then
        actual[blogType] shouldBe expected
        coVerify(exactly = 1) { postService.savePosts(blogType, expected) }
    }

    @Test
    fun `신규 게시글이 존재하지 않으면 저장로직을 실행하지 않는다`() = runTest {
        // when
        val actual = postController.saveNewPostsIfExists(emptyMap())

        // then
        actual[blogType] shouldBe null
        coVerify(exactly = 0) { postService.savePosts(blogType, any()) }
    }
}