package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post
import java.io.File
import java.lang.reflect.Type

class PostRepositoryFileImpl(
    private val
    context: Context
) : PostRepository {

    private var posts: List<Post> = getPosts()
        set(value) {
            field = value
            sync()
        }
    private var nextId = getId()

    private val data = MutableLiveData(posts)

    override fun getData(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    likedByMe = !post.likedByMe, likes = if (post.likedByMe) {
                        post.likes - 1
                    } else {
                        post.likes + 1
                    }
                )
            } else {
                post
            }
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(shares = post.shares + 1)
            } else {
                post
            }
        }
        data.value = posts
    }

    override fun viewById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(views = post.views + 1)
            } else {
                post
            }
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filterNot { it.id == id }
        data.value = posts
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    likes = 0,
                    likedByMe = false,
                    published = "Now"
                )
            ) + posts
        } else {
            posts = posts.map {
                if (it.id == post.id) {
                    it.copy(content = post.content)
                } else {
                    it
                }
            }
        }
        data.value = posts
    }

    private fun sync() {
        val file = File(context.filesDir, FILE_NAME)
        file.writeText(gson.toJson(posts))
    }

    private fun getPosts(): List<Post> {
        val file = File(context.filesDir, FILE_NAME)
        if (file.exists()) {
            val text = file.readText()
            return gson.fromJson(text, postsType)
        } else {
            return emptyList()
        }
    }

    private fun getId() = (posts.maxByOrNull { it.id }?.id ?: 0L) + 1L

    private companion object {
        const val FILE_NAME = "posts_json"
        val gson = Gson()
        val postsType: Type = object : TypeToken<List<Post>>() {}.type
    }
}