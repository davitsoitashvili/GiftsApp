package com.example.giftsapp.ui.fragments.dashboard.posts.comments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giftsapp.App
import com.example.giftsapp.R
import com.example.giftsapp.databinding.FragmentCommentsBinding
import com.example.giftsapp.models.Comment
import com.example.giftsapp.models.Post
import com.example.giftsapp.tools.extensions.clear
import com.example.giftsapp.tools.extensions.removeDots
import com.example.giftsapp.tools.extensions.removeMailSign
import com.example.giftsapp.tools.showToastMessage
import com.example.giftsapp.tools.validators.InputValidationException
import com.example.giftsapp.tools.validators.InputValidators.validateOnEmptyInput
import com.example.giftsapp.ui.adapters.CommentAdapter
import com.example.giftsapp.ui.fragments.dashboard.posts.PostCommentCallback
import com.google.firebase.database.*
import java.util.*

class CommentsFragment : Fragment(R.layout.fragment_comments) {
    private lateinit var binding: FragmentCommentsBinding
    private lateinit var commentsAdapter: CommentAdapter
    private var comments = mutableListOf<Comment>()
    private val database = FirebaseDatabase.getInstance()
    private lateinit var myRef: DatabaseReference
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCommentsBinding.bind(view)
        val post: Post = arguments?.getParcelable(POST)!!
        myRef = database.getReference(
            "posts/${
                App.app!!.getFirebaseAuth().currentUser?.email.toString().removeDots()
                    .removeMailSign()
            }/${post.title!!}/comments/"
        )
        initCommentAdapter()
        displayComments()
        close()
        sendComment()
    }

    private fun close() {
        binding.closeBtn.setOnClickListener {
            postCommentCallback?.closedComments(true)
        }
    }

    private fun initCommentAdapter() {
        commentsAdapter = CommentAdapter()
        commentsAdapter.updateComments(comments)
        with(binding) {
            val layoutManager =LinearLayoutManager(requireActivity())
            layoutManager.reverseLayout = true
            commentsRecyclerView.layoutManager = layoutManager
            commentsRecyclerView.adapter = commentsAdapter

        }
    }

    private fun displayComments() {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                comments = mutableListOf<Comment>()
                snapshot.children.forEach {
                    val comment: Comment = it.getValue(Comment::class.java)!!
                    comments.add(comment)
                    commentsAdapter.updateComments(comments)
                    commentsAdapter.notifyDataSetChanged()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                showToastMessage(error.message)
            }
        })
    }

    private fun sendComment() {
        with(binding) {
            sendCommentBtn.setOnClickListener {
                try {
                    validateOnEmptyInput(inputCommentTextView)
                } catch (exception: InputValidationException) {
                    return@setOnClickListener
                }

                val text = inputCommentTextView.text.toString()
                val author = App.app!!.getFirebaseAuth().currentUser?.email
                val createdAt = Calendar.getInstance().time
                val comment = Comment(text, author, createdAt)
                myRef.child(text + createdAt.time).setValue(comment)
                inputCommentTextView.clear()
            }
        }
    }

    companion object {
        private const val POST = "Post"
        private var postCommentCallback: PostCommentCallback? = null
        fun createInstance(fragment: PostCommentCallback, post: Post): CommentsFragment {
            return CommentsFragment().apply {
                postCommentCallback = fragment
                arguments = bundleOf(POST to post)
            }
        }
    }
}