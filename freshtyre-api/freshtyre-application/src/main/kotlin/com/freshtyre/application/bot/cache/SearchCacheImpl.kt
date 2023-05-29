package com.freshtyre.application.bot.cache

import com.freshtyre.domain.BotSearchRequest
import org.springframework.stereotype.Component

@Component
class SearchCacheImpl: Cache<BotSearchRequest> {

    private val users: MutableMap<Long, BotSearchRequest> = HashMap()

    override fun findById(id: Long): BotSearchRequest? = users[id]

    override fun removeById(id: Long) { users.remove(id) }

    override fun getAll(): List<BotSearchRequest> = ArrayList(users.values)

    override fun add(t: BotSearchRequest) {
        if (t.id != null) {
            users[t.id] = t
        } else {
            throw NullPointerException("No Id")
        }
    }
}