package ru.androidschool.intensiv

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.androidschool.intensiv.util.convertImagePath

class ConvertersUnitTest {
    @Test
    fun `convert image path without endpoint`(){
        val path = "/vWs5AuHygJHStRExxrwoIkQq9wd.jpg"
        val expectedPath = "https://image.tmdb.org/t/p/w185/vWs5AuHygJHStRExxrwoIkQq9wd.jpg"
        assertEquals(expectedPath, convertImagePath(path))
    }

    @Test
    fun `do not convert image path with endpoint`(){
        val path = "https://image.tmdb.org/t/p/w185/vWs5AuHygJHStRExxrwoIkQq9wd.jpg"
        assertEquals(path, convertImagePath(path))
    }
}