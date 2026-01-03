package com.luna.budgetapp.data.utils

import android.util.Log
import com.luna.budgetapp.BuildConfig
import com.luna.budgetapp.data.datastore.AuthLocalDataSource
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.channel.PrivateChannelEventListener
import com.pusher.client.connection.ConnectionEventListener
import com.luna.budgetapp.domain.repository.AuthRepository
import com.pusher.client.channel.PusherEvent
import com.pusher.client.connection.ConnectionStateChange
import java.lang.Exception

class PusherManager(
    private val repository: AuthRepository
) {
    private val key = BuildConfig.PUSHER_KEY
    private val cluster = BuildConfig.PUSHER_CLUSTER
    private val authEndpoint = "${BuildConfig.LOCAL_BACKEND_URL}/api/v1/auth/pusher/auth"
    private val privateExpenseChannel = "private-expense-channel"
    private val expenseEventName = "expense-added"
    private var pusher: Pusher? = null

    private val privateChannelEventListener = object: PrivateChannelEventListener {
        override fun onAuthenticationFailure(message: String?, e: Exception?) {
            Log.e("PusherChannelEvent", "Failed authentication: $message")
        }

        override fun onSubscriptionSucceeded(channelName: String?) {
            Log.d("PusherChannelEvent", "Subscription succeeded on channel: $channelName")
        }

        override fun onEvent(event: PusherEvent?) {
            
        }
    }

    suspend fun initPusher() {
        if (pusher != null) return

        val options = PusherOptions().apply {
            setCluster(cluster)
            setUseTLS(false)
            setChannelAuthorizer(JwtChannelAuthorizer(authEndpoint, repository::refreshJwtToken))
        }

        pusher = Pusher(key, options).apply { connect(
            object : ConnectionEventListener {
                override fun onConnectionStateChange(change: ConnectionStateChange?) {
                    Log.d("PusherConnection", "Connection state changed: ${change?.previousState} -> ${change?.currentState}")
                }

                override fun onError(
                    message: String?,
                    code: String?,
                    e: Exception?
                ) {
                    Log.e("PusherConnection", "Error detected: [$code] $message")
                }
            }
        ) }
    }

    fun subscribeToExpenseChannel(
        onEvent: (PusherEvent?) -> Unit
    ) {
        val instance = pusher ?: throw IllegalStateException("Pusher not initialized")
        instance.subscribePrivate(
            privateExpenseChannel, 
            object : PrivateChannelEventListener {
                override fun onAuthenticationFailure(message: String?, e: Exception?) {
                    Log.e("PusherChannelEvent", "Failed authentication: $message")
                }

                override fun onSubscriptionSucceeded(channelName: String?) {
                    Log.d("PusherChannelEvent", "Subscription succeeded on channel: $channelName")
                }

                override fun onEvent(event: PusherEvent?) {
                    onEvent(event)
                }
            },
            expenseEventName
        )
    }

    fun disconnect() {
        pusher?.disconnect()
        pusher = null
    }
}
