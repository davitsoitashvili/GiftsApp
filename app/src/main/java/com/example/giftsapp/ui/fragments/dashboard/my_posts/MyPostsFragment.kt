package com.example.giftsapp.ui.fragments.dashboard.my_posts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giftsapp.App
import com.example.giftsapp.R
import com.example.giftsapp.databinding.FragmentMyPostsBinding
import com.example.giftsapp.models.Post
import com.example.giftsapp.tools.extensions.removeDots
import com.example.giftsapp.tools.extensions.removeMailSign
import com.example.giftsapp.tools.showToastMessage
import com.example.giftsapp.ui.adapters.PostsAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyPostsFragment : Fragment(R.layout.fragment_my_posts) {
    private lateinit var binding: FragmentMyPostsBinding
    private lateinit var postsAdapter: PostsAdapter
    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference(
        "posts/${
            App.app!!.getFirebaseAuth().currentUser?.email.toString().removeDots().removeMailSign()
        }"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyPostsBinding.bind(view)
        initPostAdapter()
    }

    private fun initPostAdapter() {
        postsAdapter = PostsAdapter {}
        with(binding) {
            myPostsRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
            myPostsRecyclerView.adapter = postsAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        myRef.addValueEventListener(object : ValueEventListener {
            val posts = mutableListOf<Post>()
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val post: Post = it.getValue(Post::class.java)!!
                    posts.add(post)
                }
                postsAdapter.updatePosts(posts)
                postsAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                showToastMessage(error.message)
            }
        })
    }
}