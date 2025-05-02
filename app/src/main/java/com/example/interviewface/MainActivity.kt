package com.example.interviewface

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.interviewsViewPager)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        // Set up ViewPager
        val interviews = listOf(
            Interview(
                "Entrevista de trabajo de marketing",
                "Prepárate para tu puesto de marketing entrevista",
                "https://example.com/marketing.jpg"
            ),
            Interview(
                "Entrevista de ingeniero de software",
                "Perfecciona tus habilidades para tu entrevista de ingeniería de software",
                "https://example.com/software.jpg"
            ),
            Interview(
                "Entrevista de análisis de datos",
                "Prepárate para tu entrevista de análisis de datos",
                "https://example.com/data.jpg"
            )
        )

        viewPager.adapter = InterviewAdapter(interviews)
        viewPager.offscreenPageLimit = 1
        
        // Add padding for carousel effect
        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.page_margin)
        val offsetPx = resources.getDimensionPixelOffset(R.dimen.page_offset)
        viewPager.setPageTransformer { page, position ->
            val viewPager = page.parent.parent as ViewPager2
            val offset = position * -(2 * offsetPx + pageMarginPx)
            if (viewPager.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                page.translationX = offset
            }
        }

        // Set up bottom navigation
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
        
        Glide.with(holder.imageView.context)
            .load(interview.imageUrl)
            .centerCrop()
            .into(holder.imageView)
    }

    override fun getItemCount() = interviews.size
}
