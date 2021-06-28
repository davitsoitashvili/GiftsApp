package com.example.giftsapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Post(val title : String? = null, val description : String? = null, val author : String? = null,val createdAt : Date? = null) : Parcelable