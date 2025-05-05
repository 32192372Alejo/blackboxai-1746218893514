package com.example.interviewface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var practiceInterviewsRecyclerView: RecyclerView
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        practiceInterviewsRecyclerView = findViewById(R.id.practiceInterviewsRecyclerView)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        setupRecyclerView()
        setupBottomNavigation()
    }

    private fun setupRecyclerView() {
        val interviews = listOf(
            Interview(
                getString(R.string.marketing_interview_title),
                getString(R.string.marketing_interview_description),
                "https://raw.githubusercontent.com/yourusername/interview-app-images/main/marketing.jpg"
            ),
            Interview(
                getString(R.string.software_interview_title),
                getString(R.string.software_interview_description),
                "https://raw.githubusercontent.com/yourusername/interview-app-images/main/software.jpg"
            ),
            Interview(
                getString(R.string.data_interview_title),
                getString(R.string.data_interview_description),
                "https://raw.githubusercontent.com/yourusername/interview-app-images/main/data.jpg"
            )
        )

        practiceInterviewsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = InterviewAdapter(interviews)
        }
    }

    private fun setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> true
                R.id.navigation_interviews -> true
                R.id.navigation_comments -> true
                R.id.navigation_profile -> true
                R.id.navigation_more -> true
                else -> false
            }
        }
    }
}

data class Interview(
    val title: String,
    val description: String,
    val imageUrl: String
)

class InterviewAdapter(private val interviews: List<Interview>) : 
    RecyclerView.Adapter<InterviewAdapter.InterviewViewHolder>() {

    class InterviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.findViewById(R.id.interviewTitle)
        val descriptionView: TextView = view.findViewById(R.id.interviewDescription)
        val imageView: ImageView = view.findViewById(R.id.interviewImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_interview, parent, false)
        return InterviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: InterviewViewHolder, position: Int) {
        val interview = interviews[position]
        holder.titleView.text = interview.title
        holder.descriptionView.text = interview.description
        
        // Load image using Glide
        Glide.with(holder.imageView.context)
            .load(interview.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.imageView)
    }

    override fun getItemCount() = interviews.size
}
