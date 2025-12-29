package com.luna.budgetapp.data.utils

import okhttp3.OkHttpClient
import okhttp3.FormBody
import com.pusher.client.ChannelAuthorizer
import okhttp3.Request

class JwtChannelAuthorizer(
    private val authUrl: String,
    private val jwtToken: String,
    private val client: OkHttpClient = OkHttpClient()
) : ChannelAuthorizer {
    override fun authorize(channelName: String, socketId: String): String {
        val form = FormBody.Builder()
            .add("channel_name", channelName)
            .add("socket_id", socketId)
            .build()

        val request = Request.Builder()
            .url(authUrl)
            .post(form)
            .addHeader("Authorization", "Bearer $jwtToken")
            .build()

        client.newCall(request).execute().use { resp ->
            if (!resp.isSuccessful) {
                throw RuntimeException("Auth failed: ${resp.code} ${resp.message}")
            }

            return resp.body.toString()
        }
    }

}
