package ru.netology.nmedia.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.databinding.ActivityNewPostBinding
import ru.netology.nmedia.util.AndroidUtils

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
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

        val content = intent.getStringExtra(EXTRA_POST_CONTENT)
        if (content != null) {
            binding.group.visibility = View.VISIBLE
            binding.editText.text = content
        }
        binding.edit.setText(content)
        AndroidUtils.showKeyboard(binding.edit)

        binding.cancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        binding.ok.setOnClickListener {
            val text = binding.edit.text.toString()
            if (text.isBlank()) {
                setResult(RESULT_CANCELED)
            } else {
                setResult(RESULT_OK, Intent().apply { putExtra(Intent.EXTRA_TEXT, text) })
            }
            finish()
        }

//        val videoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
//            if (uri != null) {

            }
        }

    }

}

private const val EXTRA_POST_CONTENT = "content"

object NewPostContract : ActivityResultContract<String?, String?>() {
    override fun createIntent(
        context: Context,
        input: String?
    ) = Intent(context, NewPostActivity::class.java)
        .putExtra(EXTRA_POST_CONTENT, input)

    override fun parseResult(
        resultCode: Int,
        intent: Intent?
    ) = intent?.getStringExtra(Intent.EXTRA_TEXT)

}