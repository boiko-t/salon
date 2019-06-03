package com.boiko.taisa.salon.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.boiko.taisa.salon.BuildConfig
import com.boiko.taisa.salon.R
import com.boiko.taisa.salon.domain.entity.Category
import com.boiko.taisa.salon.mvp.home.Home
import com.boiko.taisa.salon.mvp.home.HomePresenter
import com.boiko.taisa.salon.ui.recyclerview.CategoryRecyclerViewAdapter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.disposables.CompositeDisposable


class HomeActivity : AppCompatActivity(), Home.View {
    private lateinit var presenter: HomePresenter
    private lateinit var disposable: CompositeDisposable

    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var fabCreateVisit: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        presenter = HomePresenter()
        disposable = CompositeDisposable()
        findViews()
        initSubscriptions()
        configureMenu()
    }

    override fun onStart() {
        super.onStart()
        presenter.onViewAttach(this)
    }

    override fun onStop() {
        presenter.onViewDetach()
        super.onStop()
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

    override fun openNewVisitView() {
        val intent = Intent(this, NewVisitActivity::class.java)
        startActivity(intent)
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
        fabCreateVisit = findViewById(R.id.fabCreateVisit)
    }

    private fun initSubscriptions() {
        val createNewObservable = RxView.clicks(fabCreateVisit)

        disposable.add(createNewObservable.subscribe { presenter.onNewVisitClick() })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_sign_out -> {
            FirebaseAuth.getInstance().signOut()
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(BuildConfig.GOOGLE_REQUEST_ID_TOKEN)
                    .requestEmail()
                    .build()

            val googleSignInClient = GoogleSignIn.getClient(this, gso)
            googleSignInClient.signOut().addOnCompleteListener {
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun configureMenu() {
//        setSupportActionBar(findViewById(R.id.my_toolbar))
    }
}
