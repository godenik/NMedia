package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostListener
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val ime = insets.getInsets(WindowInsetsCompat.Type.ime())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                maxOf(systemBars.bottom, ime.bottom)
            )
            insets
        }
        val viewModel: PostViewModel by viewModels()
        val adapter = PostAdapter(
            object : PostListener {
                override fun onLike(post: Post) {
                    binding.content.clearFocus()
                    viewModel.likeById(post.id)
                }

                override fun onShare(post: Post) {
                    binding.content.clearFocus()
                    viewModel.shareById(post.id)
                }

                override fun onView(post: Post) {
                    binding.content.clearFocus()
                    viewModel.viewById(post.id)
                }

                override fun onRemove(post: Post) {
                    binding.content.clearFocus()
                    viewModel.removeById(post.id)
                }

                override fun onEdit(post: Post) {
                    println("EDIT: id=${post.id}, content=${post.content}")
                    viewModel.edit(post)
                }

            }
        )

        binding.list.adapter = adapter


        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this) { edited ->
            if (edited != null) {
                binding.editText.text = edited.content
                binding.content.setText(edited.content)
                binding.group.visibility = View.VISIBLE
                binding.save.visibility = View.VISIBLE
                AndroidUtils.showKeyboard(binding.content)
            }
        }

        binding.content.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.save.visibility = View.VISIBLE
            } else {
                binding.save.visibility = View.GONE
            }
        }

        binding.save.setOnClickListener {
            binding.save.visibility = View.VISIBLE
            val content = binding.content.text?.toString()
            if (content.isNullOrBlank()) {
                Toast.makeText(this, R.string.error_empty_text, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.save(content)
            binding.content.clearFocus()
            binding.content.setText("")
            AndroidUtils.hideKeyboard(binding.content)
            binding.save.visibility = View.GONE
            binding.group.visibility = View.GONE
        }
        binding.cancel.setOnClickListener {
            binding.group.visibility = View.GONE
            binding.content.clearFocus()
            binding.content.setText("")
            AndroidUtils.hideKeyboard(binding.content)
            viewModel.cancelEdit()

        }

    }

}


