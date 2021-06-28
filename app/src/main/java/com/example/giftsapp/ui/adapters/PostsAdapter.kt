package com.example.giftsapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.giftsapp.databinding.PostRecyclerItemBinding
import com.example.giftsapp.models.Post

class PostsAdapter(private val getPostCallback: (Post) -> Unit) :
    RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {
    private var posts: List<Post> = mutableListOf()

    fun updatePosts(posts: List<Post>) {
        this.posts = posts
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding =
            PostRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.onBindPost(post)
        holder.onPostClick(post, getPostCallback)
    }

    override fun getItemCount() = posts.size

    class PostViewHolder(val view: PostRecyclerItemBinding) : RecyclerView.ViewHolder(view.root) {
        @SuppressLint("SetTextI18n")
        fun onBindPost(post: Post) {
            with(view) {
                postTitleView.text = "Title : ${post.title}"
                postDescriptionView.text = "Description : ${post.description}"
                postAuthorView.text = "Author ${post.author}"
                postCreationTime.text = "Created At ${post.createdAt}"
            }
        }

        fun onPostClick(post: Post, postCallback: (Post) -> Unit) {
            view.postView.setOnClickListener {
                postCallback(post)
            }
        }
    }
}