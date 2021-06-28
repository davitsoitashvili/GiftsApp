package com.example.giftsapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.giftsapp.databinding.CommentRecyclerItemBinding
import com.example.giftsapp.models.Comment

class CommentAdapter :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    private var comments: List<Comment> = mutableListOf()

    fun updateComments(comments: List<Comment>) {
        this.comments = comments
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding =
            CommentRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val post = comments[position]
        holder.onBindComment(post)
    }

    override fun getItemCount() = comments.size

    class CommentViewHolder(val view: CommentRecyclerItemBinding) :
        RecyclerView.ViewHolder(view.root) {
        @SuppressLint("SetTextI18n")
        fun onBindComment(comment: Comment) {
            with(view) {
                commentTextView.text = "Text : ${comment.text}"
                commentAuthorView.text = "Author : ${comment.author}"
                commentCreationDateView.text = "Created At ${comment.createdAt!!}"
            }
        }
    }
}