package com.example.book_m_front.network

import com.example.book_m_front.network.dto.PlaylistResponse
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test

class PlaylistResponseTest {
    @Test
    fun backendResponsePreservesChapterAssignmentsAndNumericMusicIds() {
        val json = """{
            "isbn":"9781234567890", "title":"테스트 도서",
            "totalChapters":3, "totalMusic":3,
            "chapterPlaylist":[
                {"chapterNum":1,"musics":[{"id":42},{"id":43}]},
                {"chapterNum":2,"musics":[]},
                {"chapterNum":3,"musics":[{"id":99}]}
            ]
        }"""
        val response = Gson().fromJson(json, PlaylistResponse::class.java)

        assertEquals(listOf("42", "43", "99"), response.playlist.map { it.id })
        assertEquals(listOf(0, 0, 2), response.playlist.map { it.chapterIndex })
        assertEquals(emptyList<Any>(), response.playlist.filter { it.chapterIndex == 1 })
        assertEquals("테스트 도서", response.playlist.first().album)
        assertEquals("AI 음악 42", response.playlist.first().title)
    }
}
