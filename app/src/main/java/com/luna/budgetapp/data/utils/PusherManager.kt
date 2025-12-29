package com.luna.budgetapp.data.utils

import com.luna.budgetapp.BuildConfig

class PusherManager {

    private val appId = BuildConfig.PUSHER_APP_ID
    private val key = BuildConfig.PUSHER_KEY
    private val secret = BuildConfig.PUSHER_SECRET
    private val cluster = BuildConfig.PUSHER_CLUSTER
    private val apiKey = BuildConfig.PUSHER_API_KEY

    private val privateExpenseChannel = "PRIVATE-EXPENSE-CHANNEL"
    
}
