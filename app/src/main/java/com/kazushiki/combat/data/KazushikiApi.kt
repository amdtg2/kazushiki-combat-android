package com.kazushiki.combat.data

import com.kazushiki.combat.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL

object KazushikiApi {
    private const val SYSTEM_PROMPT = "You are Kazushiki Combat AI Coach. Be concise, practical, encouraging, and focused on striking technique. Never use profanity."

    suspend fun askCoach(message: String): Result<String> = withContext(Dispatchers.IO) {
        runCatching {
            val endpoint = URL("${BuildConfig.KAZUSHIKI_API_BASE_URL}/ai/chat")
            val connection = (endpoint.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                connectTimeout = 15_000
                readTimeout = 30_000
                doOutput = true
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("Accept", "application/json")
            }

            val payload = JSONObject().apply {
                put("system", SYSTEM_PROMPT)
                put("stream", false)
                put("temperature", 0.2)
                put("maxTokens", 190)
                put("messages", JSONArray().put(JSONObject().apply {
                    put("role", "user")
                    put("content", message)
                }))
            }

            connection.outputStream.bufferedWriter(Charsets.UTF_8).use { writer ->
                writer.write(payload.toString())
            }

            val status = connection.responseCode
            val stream = if (status in 200..299) connection.inputStream else connection.errorStream
            val body = BufferedReader(stream.reader(Charsets.UTF_8)).use { it.readText() }
            connection.disconnect()

            val json = JSONObject(body)
            if (status !in 200..299) {
                val providerMessage = json.optJSONObject("error")?.optString("message")
                    ?.takeIf { it.isNotBlank() }
                    ?: "AI Coach is unavailable right now."
                error(providerMessage)
            }

            json.optString("reply").takeIf { it.isNotBlank() }
                ?: error("AI Coach returned an empty response.")
        }
    }
}
