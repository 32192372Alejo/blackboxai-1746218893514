package com.example.interviewface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.interviewsViewPager)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        setupViewPager()
        setupBottomNavigation()
    }

    private fun setupViewPager() {
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

        viewPager.apply {
            adapter = InterviewAdapter(interviews)
            offscreenPageLimit = 1
            
            // Set page transformer for carousel effect
            setPageTransformer { page, position ->
                val minScale = 0.85f
                val minAlpha = 0.5f
                
                page.apply {
                    val pageWidth = width
                    when {
                        position < -1 -> { // [-Infinity,-1)
                            alpha = 0f
                            scaleX = minScale
                            scaleY = minScale
                        }
                        position <= 1 -> { // [-1,1]
                            val scaleFactor = minScale.coerceAtLeast(1 - abs(position))
                            val vertMargin = pageWidth * (1 - scaleFactor) / 2
                            val horzMargin = pageWidth * (1 - scaleFactor) / 2
                            
                            translationX = if (position < 0) {
                                horzMargin - vertMargin / 2
                            } else {
                                horzMargin + vertMargin / 2
                            }
                            
                            scaleX = scaleFactor
                            scaleY = scaleFactor
                            
                            alpha = (minAlpha + (((scaleFactor - minScale) / (1 - minScale)) * (1 - minAlpha)))
                        }
                        else -> { // (1,+Infinity]
                            alpha = 0f
                            scaleX = minScale
                            scaleY = minScale
                        }
                    }
                }
            }
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
