package ru.tbank.education.school.lesson6.client.java.user

import ru.tbank.education.school.lesson6.client.dto.ApiResponse
import ru.tbank.education.school.lesson6.client.dto.User
import ru.tbank.education.school.lesson6.client.lessonObjectMapper
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.random.Random

class UserClient(private val url: String) {
    private val javaHttpClient = HttpClient.newBuilder().build()

    fun addUser(user: User): ApiResponse? {
        val body = lessonObjectMapper.writeValueAsString(user)
        val request = HttpRequest.newBuilder()
            .uri(URI.create("$url/user"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build()

        val response = javaHttpClient.send(request, HttpResponse.BodyHandlers.ofString())

        return if (response.statusCode() == 200 || response.statusCode() == 201) {
            lessonObjectMapper.readValue(response.body(), ApiResponse::class.java)
        } else {
            null
        }
    }

    fun deleteUser(username: String): ApiResponse? {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("$url/user/$username"))
            .header("Content-Type", "application/json")
            .DELETE()
            .build()

        val response = javaHttpClient.send(request, HttpResponse.BodyHandlers.ofString())

        return if (response.statusCode() == 200 || response.statusCode() == 201) {
            lessonObjectMapper.readValue(response.body(), ApiResponse::class.java)
        } else {
            null
        }
    }

    fun updateUser(username: String, user: User): ApiResponse? {
        val body = lessonObjectMapper.writeValueAsString(user)
        val request = HttpRequest.newBuilder()
            .uri(URI.create("$url/user/$username"))
            .header("Content-Type", "application/json")
            .PUT(HttpRequest.BodyPublishers.ofString(body))
            .build()

        val response = javaHttpClient.send(request, HttpResponse.BodyHandlers.ofString())

        return if (response.statusCode() == 200 || response.statusCode() == 201) {
            lessonObjectMapper.readValue(response.body(), ApiResponse::class.java)
        } else {
            null
        }
    }

    fun getUser(username: String): User? {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("$url/user/$username"))
            .header("Content-Type", "application/json")
            .GET()
            .build()

        val response = javaHttpClient.send(request, HttpResponse.BodyHandlers.ofString())

        return if (response.statusCode() == 200 || response.statusCode() == 201) {
            lessonObjectMapper.readValue(response.body(), User::class.java)
        } else {
            null
        }
    }
}

fun main() {
    val user = User(
        id = Random.nextLong() * 1000,
        username = "user",
        firstName = "rafael",
        lastName = "biglov",
        email = "raf.big8@gmail.com",
        password = "qwerty12345",
        phone = "79168853157",
        userStatus = 1
    )

    val userClient = UserClient(url = "https://petstore.swagger.io/v2")
    userClient.addUser(user)
    println(userClient.getUser(user.username))
    println(
        userClient.updateUser(user.username, user.copy(password = "pomogite"))
    )
    println(userClient.getUser(user.username))

    println(userClient.deleteUser(user.username))

}