package ru.netology.nmedia.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.formatCount

interface PostListener {
    fun onLike(post: Post)
    fun onShare(post: Post)
    fun onView(post: Post)
    fun onRemove(post: Post)
    fun onEdit(post: Post)
}


class PostAdapter(
    private val listener: PostListener
) : ListAdapter<Post, PostViewHolder>(PostDiffItemCallBack()) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): PostViewHolder = PostViewHolder(
        CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        listener
    )

    override fun onBindViewHolder(
        holder: PostViewHolder, position: Int
    ) {
        holder.bind(getItem(position))
    }

}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener: PostListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            viewCount.text = post.views.formatCount()
            share.text = post.shares.formatCount()
            like.isChecked = post.likedByMe
            like.text = post.likes.formatCount()

            like.setOnClickListener {
                listener.onLike(post)
            }
            share.setOnClickListener {
                listener.onShare(post)
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, post.content)
                }
                val chooser =
                    Intent.createChooser(intent, binding.root.context.getString(R.string.share))
                binding.root.context.startActivity(chooser)
            }
            view.setOnClickListener {
                listener.onView(post)
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.post_menu)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                listener.onRemove(post)
                                true
                            }

                            R.id.edit -> {
                                listener.onEdit(post)
                                true
                            }

                            else -> false
                        }
                    }
                    show()
                }
            }
        }
    }
}

class PostDiffItemCallBack : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(
        oldItem: Post, newItem: Post
    ): Boolean = oldItem.id == newItem.id


    override fun areContentsTheSame(
        oldItem: Post, newItem: Post
    ): Boolean = oldItem == newItem
}