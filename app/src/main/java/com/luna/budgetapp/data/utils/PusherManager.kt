package com.luna.budgetapp.data.utils

import com.luna.budgetapp.BuildConfig
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions

class PusherManager {

    private val appId = BuildConfig.PUSHER_APP_ID
    private val key = BuildConfig.PUSHER_KEY
    private val secret = BuildConfig.PUSHER_SECRET
    private val cluster = BuildConfig.PUSHER_CLUSTER
    private val apiKey = BuildConfig.PUSHER_API_KEY
    private val authEndpoint = "${BuildConfig.LOCAL_BACKEND_URL}/api/v1/pusher/auth"
    
    private val privateExpenseChannel = "private-expense-channel"
    private val eventName = "expense-added"
    
    private val options = PusherOptions().apply {
        setCluster(cluster)
        setUseTLS(true)
        setChannelAuthorizer(JwtChannelAuthorizer(authEndpoint, jwtToken))
    }
    
    val pusher = Pusher(key, options)
    val channel = pusher.subscribePrivate(privateExpenseChannel) 
}
