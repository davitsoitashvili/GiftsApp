package com.example.giftsapp.ui.fragments.dashboard.posts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giftsapp.R
import com.example.giftsapp.databinding.FragmentPostsBinding
import com.example.giftsapp.models.Post
import com.example.giftsapp.tools.showToastMessage
import com.example.giftsapp.ui.adapters.PostsAdapter
import com.example.giftsapp.ui.fragments.dashboard.posts.comments.CommentsFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PostsFragment : Fragment(R.layout.fragment_posts), PostCommentCallback {
    private lateinit var binding: FragmentPostsBinding
    private lateinit var posts: MutableList<Post>
    private lateinit var postsAdapter: PostsAdapter
    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference("posts/")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostsBinding.bind(view)
        initPostAdapter()
    }

    override fun onResume() {
        super.onResume()
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                posts = mutableListOf()
                snapshot.children.forEach {
                    it?.children?.forEach {
                        val post: Post = it.getValue(Post::class.java)!!
                        posts.add(post)
                    }
                    postsAdapter.updatePosts(posts)
                    postsAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showToastMessage(error.message)
            }
        })
    }

    private fun initPostAdapter() {
        postsAdapter = PostsAdapter {
            openCommentsFragment(it)
        }
        with(binding) {
            postsRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            postsRecyclerView.adapter = postsAdapter
        }
    }

    private fun openCommentsFragment(post: Post) {
        binding.commentsFragmentContainer.visibility = View.VISIBLE
        binding.postsRecyclerView.visibility = View.GONE
        childFragmentManager.beginTransaction()
            .replace(R.id.commentsFragmentContainer, CommentsFragment.createInstance(this,post))
            .addToBackStack("CommentsFragment").commit()
    }

    override fun closedComments(closed : Boolean) {
        if (closed) {
            binding.commentsFragmentContainer.visibility = View.GONE
            binding.postsRecyclerView.visibility = View.VISIBLE
        }
    }
}