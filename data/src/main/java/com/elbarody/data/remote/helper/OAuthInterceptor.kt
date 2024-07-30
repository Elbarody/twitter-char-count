package com.elbarody.data.remote.helper

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.net.URLEncoder
import java.util.*

class OAuthInterceptor(
    private val consumerKey: String,
    private val consumerSecret: String,
    private val accessToken: String,
    private val accessTokenSecret: String
) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val oauthHeaders = generateOAuthHeaders(originalRequest)

        println("OAuth headers: $oauthHeaders")
        val requestWithAuth = originalRequest.newBuilder()
            .header("Authorization", oauthHeaders)
            .build()

        return chain.proceed(requestWithAuth)
    }

    private fun generateOAuthHeaders(request: Request): String {
        val oauthParams = mapOf(
            "oauth_consumer_key" to consumerKey,
            "oauth_nonce" to UUID.randomUUID().toString().replace("-", ""),
            "oauth_signature" to generateSignature(request),
            "oauth_signature_method" to "HMAC-SHA1",
            "oauth_timestamp" to (System.currentTimeMillis() / 1000).toString(),
            "oauth_token" to accessToken,
            "oauth_version" to "1.0"
        )

        return "OAuth " + oauthParams.entries.joinToString(", ") {
            "${it.key}=\"${URLEncoder.encode(it.value, "UTF-8")}\""
        }
    }

    private fun generateSignature(request: Request): String {
        val baseString = buildBaseString(request)
        val signingKey = "$consumerSecret&$accessTokenSecret"

        val mac = Mac.getInstance("HmacSHA1")
        mac.init(SecretKeySpec(signingKey.toByteArray(Charsets.UTF_8), "HmacSHA1"))
        val signature = mac.doFinal(baseString.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(signature)
    }

    private fun buildBaseString(request: Request): String {
        val method = request.method
        val url = request.url.toString()

        // Collect parameters from request
        val parameters = sortedMapOf<String, String>()
        request.headers.forEach { header ->
            parameters[header.first] = header.second
        }
        val encodedParams = parameters.entries.joinToString("&") {
            "${URLEncoder.encode(it.key, "UTF-8")}=${URLEncoder.encode(it.value, "UTF-8")}"
        }

        return "$method&${URLEncoder.encode(url, "UTF-8")}&${URLEncoder.encode(encodedParams, "UTF-8")}"
    }
}
