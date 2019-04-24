package com.boiko.taisa.salon.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.boiko.taisa.salon.R
import com.boiko.taisa.salon.domain.entity.Category
import com.boiko.taisa.salon.mvp.home.Home
import com.boiko.taisa.salon.mvp.home.HomePresenter
import com.boiko.taisa.salon.ui.recyclerview.CategoryRecyclerViewAdapter

class HomeActivity : AppCompatActivity(), Home.View {
    private lateinit var presenter: HomePresenter
    private lateinit var categoryRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        presenter = HomePresenter()
        findViews()
    }

    override fun onStart() {
        super.onStart()
        presenter.onViewAttach(this)
    }

    override fun onStop() {
        presenter.onViewDetach()
        super.onStop()
    }

    override fun initCategoryCollection(data: MutableList<Category>?) {
        val layoutManager = LinearLayoutManager(this)
        categoryRecyclerView.layoutManager = layoutManager
        val adapter = CategoryRecyclerViewAdapter(data)
        categoryRecyclerView.setItemViewCacheSize(10)
        categoryRecyclerView.adapter = adapter
    }

    private fun findViews() {
        categoryRecyclerView = findViewById(R.id.rvCategoriesCollection)
    }
}
