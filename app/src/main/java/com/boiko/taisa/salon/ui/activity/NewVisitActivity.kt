package com.boiko.taisa.salon.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import com.boiko.taisa.salon.R
import com.boiko.taisa.salon.domain.entity.Product
import com.boiko.taisa.salon.domain.entity.SalonService
import com.boiko.taisa.salon.mvp.visit.NewVisit
import com.boiko.taisa.salon.mvp.visit.NewVisitPresenter
import com.boiko.taisa.salon.ui.adapter.ServiceSpinnerAdapter
import io.reactivex.disposables.CompositeDisposable

class NewVisitActivity : AppCompatActivity(), NewVisit.View {
    private lateinit var presenter: NewVisitPresenter
    private lateinit var disposable: CompositeDisposable

    private lateinit var serviceSpinnerAdapter: ServiceSpinnerAdapter
    private lateinit var spinServicesList: Spinner
    private val servicesListOnSelectListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_visit)
        findViews()
        initSpinnerAdapters()
        presenter = NewVisitPresenter()
        disposable = CompositeDisposable()
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

    private fun findViews() {
        spinServicesList = findViewById(R.id.spinServicesList)
    }

    override fun initServiceCollection(data: List<SalonService>?) {
        serviceSpinnerAdapter = ServiceSpinnerAdapter(this, ArrayList(data))
        spinServicesList.adapter = serviceSpinnerAdapter
    }

    override fun initProductCollection(data: MutableList<Product>?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initSpinnerAdapters() {
        serviceSpinnerAdapter = ServiceSpinnerAdapter(this, arrayListOf())
        serviceSpinnerAdapter.setDropDownViewResource(R.layout.list_item)
        spinServicesList.adapter = serviceSpinnerAdapter
        spinServicesList.onItemSelectedListener = servicesListOnSelectListener
    }
}
