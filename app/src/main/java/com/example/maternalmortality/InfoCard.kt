package com.example.maternalmortality

import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_info_card.*
import me.relex.circleindicator.CircleIndicator3

class InfoCard : AppCompatActivity() {

    private var titlelist = mutableListOf<String>()
    private var desclist = mutableListOf<String>()
    private var imagelist = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_card)

        postToList()

        view_pager2.adapter = ViewPageAdapter(titlelist,desclist,imagelist)
        view_pager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val indicator = findViewById<CircleIndicator3>(R.id.indicator)
        indicator.setViewPager(view_pager2)
    }

    private fun addToList(title: String, description: String,image :Int){
        titlelist.add(title)
        desclist.add(description)
        imagelist.add(image)
    }

    private fun postToList() {
        addToList("Danger Sign 1","• Bleeding during pregnancy\n" + "• Excessive bleeding during delivery or after delivery",R.drawable.bleeding)

        addToList("Danger Sign 2","• Severe Anemia with or without breathlessness\n",R.drawable.anemia)

        addToList("Danger Sign 3","• High fever during pregnancy or within one month of delivery",R.drawable.fever)

        addToList("Danger Sign 4","• Headache, blurring of vision, fits and swelling all over the body",R.drawable.headache)

        addToList("Danger Sign 5","• Labour pain before term\n• Labour pain for more than 12 hours\n• Reduced fetal movment",R.drawable.labor)

        addToList("Danger Sign 6","• Bursting of water bag without labour pains\n• Preterm labour pains (<37 weeks)",R.drawable.water)
    }
}