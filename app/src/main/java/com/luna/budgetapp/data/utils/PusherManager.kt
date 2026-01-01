package com.luna.budgetapp.data.utils

import com.luna.budgetapp.BuildConfig
import com.luna.budgetapp.domain.repository.AuthRepository
import com.luna.budgetapp.domain.usecase.UseCases
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.channel.PrivateChannelEventListener

class PusherManager(
    private val repository: AuthRepository
) {
    private val key = BuildConfig.PUSHER_KEY
    private val cluster = BuildConfig.PUSHER_CLUSTER
    private val authEndpoint = "${BuildConfig.LOCAL_BACKEND_URL}/api/v1/pusher/auth"

    private val privateExpenseChannel = "private-expense-channel"
    private val expenseEventName = "expense-added"

    private var pusher: Pusher? = null

    suspend fun initPusher() {
        if (pusher != null) return // already initialized

        val jwtToken = repository.getJwtToken() ?: throw IllegalStateException("JWT missing")
        val options = PusherOptions().apply {
            setCluster(cluster)
            setUseTLS(true)
            setChannelAuthorizer(JwtChannelAuthorizer(authEndpoint, jwtToken))
        }

        pusher = Pusher(key, options).apply { connect() }
    }

    fun subscribeToExpenseChannel(listener: PrivateChannelEventListener) {
        val instance = pusher ?: throw IllegalStateException("Pusher not initialized")
        instance.subscribePrivate(privateExpenseChannel, listener, expenseEventName)
    }

    fun disconnect() {
        pusher?.disconnect()
        pusher = null
    }
}
