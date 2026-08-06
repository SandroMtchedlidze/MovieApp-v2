package com.space.data.remote.cache

class GenreCache {
    private var cache: Map<Int, String> = emptyMap()

    val isEmpty: Boolean
        get() = cache.isEmpty()

    fun get(): Map<Int, String> = cache

    fun update(genres: Map<Int, String>) {
        cache = genres
    }
}