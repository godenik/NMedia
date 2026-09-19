package ru.netology.nmedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

private val emptyPost = Post()

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()

    val data: LiveData<List<Post>> = repository.getData()

    fun likeById(id: Long) {
        repository.likeById(id)
    }

    fun shareById(id: Long) {
        repository.shareById(id)
    }

    fun viewById(id: Long) {
        repository.viewById(id)
    }

    fun removeById(id: Long) {
        repository.removeById(id)
    }

    val edited = MutableLiveData<Post?>(null)

    fun save(content: String) {
        val post = edited.value ?: emptyPost
        val trimmed: String = content.trim()
        if (trimmed != post.content) {
            repository.save(post.copy(content = trimmed))
        }

        edited.value = null
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun cancelEdit() {
        edited.value = null
    }
}


