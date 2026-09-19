package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {

    var posts = listOf(
        Post(
            id = 9,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb.",
            likes = 999,
            likedByMe = false,
            shares = 12999,
            views = 1_299_999
        ), Post(
            id = 8,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "22 мая в 17:30",
            content = "Бесплатные курсы и лекции Нетологии — это Возможность познакомиться с интересующей профессией; Новые навыки, которые можно сразу применять; Компактная программа; Обучение в удобное время - Вступай к нам → http://netolo.gy/fyb",
            likes = 999,
            likedByMe = false,
            shares = 12999,
            views = 1_299_999
        ), Post(
            id = 7,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "22 мая в 17:30",
            content = "Станете героем интерактивной истории: спасёте бизнес, попробуете разные маркетинговые роли, попрактикуетесь с ИИ и поймёте, какое направление вам подходит. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likes = 999,
            likedByMe = false,
            shares = 12999,
            views = 1_299_999
        ), Post(
            id = 6,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "22 мая в 17:30",
            content = "Попробуйте интернет-маркетинг на практике: за два занятия разберёте реальные задачи маркетолога и освоите базовые методы анализа аудитории. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likes = 999,
            likedByMe = false,
            shares = 12999,
            views = 1_299_999
        ), Post(
            id = 5,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "22 мая в 17:30",
            content = "Попробуйте себя в создании интерфейсов — от идеи до готового дизайна. Узнайте, как работают дизайнеры цифровых продуктов, познакомьтесь с командными процессами и создайте свой первый макет в Figma. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likes = 999,
            likedByMe = false,
            shares = 12999,
            views = 1_299_999
        ), Post(
            id = 4,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "22 мая в 17:30",
            content = "Познакомитесь с разными стилями оформления пространств, решите практические задачи и поймёте, чем отличаются профессии дизайнера среды и интерьера, и подходит ли вам это направление. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likes = 999,
            likedByMe = false,
            shares = 12999,
            views = 1_299_999
        ), Post(
            id = 3,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "22 мая в 17:30",
            content = "Спасёте маркетплейс во время ИТ-инцидента — попробуете себя в 6 разных ролях и попрактикуетесь на реальных задачах разработчиков и инженеров. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likes = 999,
            likedByMe = false,
            shares = 12999,
            views = 1_299_999
        ), Post(
            id = 2,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "22 мая в 17:30",
            content = "Познакомитесь с профессией бухгалтера на практике: за два занятия разберёте основы налогового учёта и попробуете рассчитать налоги. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likes = 999,
            likedByMe = false,
            shares = 12999,
            views = 1_299_999
        ), Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "22 мая в 17:30",
            content = "Начнёте использовать нейросети в работе, учёбе и жизни — даже если раньше не работали с ИИ. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likes = 999,
            likedByMe = false,
            shares = 12999,
            views = 1_299_999
        )
    )

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
        val newId = posts.maxOf { it.id } +1
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = newId,
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
}