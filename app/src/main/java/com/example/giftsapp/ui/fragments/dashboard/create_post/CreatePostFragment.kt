package com.example.giftsapp.ui.fragments.dashboard.create_post

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.giftsapp.App
import com.example.giftsapp.R
import com.example.giftsapp.databinding.FragmentCreatePostBinding
import com.example.giftsapp.models.Post
import com.example.giftsapp.tools.extensions.clear
import com.example.giftsapp.tools.extensions.removeDots
import com.example.giftsapp.tools.extensions.removeMailSign
import com.example.giftsapp.tools.showToastMessage
import com.example.giftsapp.tools.validators.InputValidationException
import com.example.giftsapp.tools.validators.InputValidators.validateOnEmptyInput
import com.example.giftsapp.tools.validators.InputValidators.validateOnPostDescriptionLength
import com.example.giftsapp.tools.validators.InputValidators.validateOnPostTitleLength
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class CreatePostFragment : Fragment(R.layout.fragment_create_post) {
    private lateinit var binding: FragmentCreatePostBinding
    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference(
        "posts/${
            App.app!!.getFirebaseAuth().currentUser?.email.toString().removeDots().removeMailSign()
        }"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreatePostBinding.bind(view)
        createPost()
    }

    private fun createPost() {
        with(binding) {
            createPostBtn.setOnClickListener {
                try {
                    validateOnEmptyInput(inputPostTitleView)
                    validateOnEmptyInput(inputPostDescriptionView)
                    validateOnPostTitleLength(inputPostTitleView)
                    validateOnPostDescriptionLength(inputPostDescriptionView)
                } catch (exception: InputValidationException) {
                    return@setOnClickListener
                }

                val title = inputPostTitleView.text.toString().trim()
                val description = inputPostDescriptionView.text.toString().trim()
                val author = App.app!!.getFirebaseAuth().currentUser?.email
                val createdAt = Calendar.getInstance().time
                val post = Post(title, description, author, createdAt)
                myRef.child(post.title!!).setValue(post)
                showToastMessage("Post Creation Finished!")
                inputPostTitleView.clear()
                inputPostDescriptionView.clear()
            }
        }
    }
}
