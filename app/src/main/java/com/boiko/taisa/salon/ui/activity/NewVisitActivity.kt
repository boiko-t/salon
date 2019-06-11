package com.boiko.taisa.salon.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import com.boiko.taisa.salon.R
import com.boiko.taisa.salon.domain.entity.Product
import com.boiko.taisa.salon.domain.entity.ProductUsageRecord
import com.boiko.taisa.salon.domain.entity.SalonService
import com.boiko.taisa.salon.mvp.visit.NewVisit
import com.boiko.taisa.salon.mvp.visit.NewVisitModel
import com.boiko.taisa.salon.mvp.visit.NewVisitPresenter
import com.boiko.taisa.salon.ui.adapter.ServiceSpinnerAdapter
import com.boiko.taisa.salon.ui.event.RecyclerViewEventListener
import com.boiko.taisa.salon.ui.recyclerview.ProductRecyclerViewAdapter
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.CompositeDisposable

class NewVisitActivity : AppCompatActivity(), NewVisit.View {
    private var presenter = NewVisitPresenter()
    private var disposable = CompositeDisposable()

    private lateinit var buttonAddProductListItem: ImageButton
    private lateinit var buttonSendVisit: Button
    private lateinit var etClientName: EditText
    private lateinit var spinnerServicesList: Spinner
    private lateinit var recyclerViewProductList: RecyclerView
    private lateinit var serviceSpinnerAdapter: ServiceSpinnerAdapter
    private lateinit var productListRecyclerAdapter: ProductRecyclerViewAdapter

    private var visit = NewVisitModel.getInstance().state.visit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_visit)
        findViews()
        initViewAdapters()
        initSubscriptions()
        visit.addProduct(ProductUsageRecord())
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
        spinnerServicesList = findViewById(R.id.spinServicesList)
        recyclerViewProductList = findViewById(R.id.rvProductList)
        buttonAddProductListItem = findViewById(R.id.btnAddProductListItem)
        buttonSendVisit = findViewById(R.id.btnSendVisit)
        etClientName = findViewById(R.id.etClientName)
    }

    override fun initServiceCollection(data: List<SalonService>?) {
        serviceSpinnerAdapter = ServiceSpinnerAdapter(this, ArrayList(data))
        spinnerServicesList.adapter = serviceSpinnerAdapter
    }

    override fun initProductCollection(data: MutableList<Product>?) {
        productListRecyclerAdapter = ProductRecyclerViewAdapter(this, data, visit.products)
        productListRecyclerAdapter.setListener(productsRecyclerListener)
        recyclerViewProductList.adapter = productListRecyclerAdapter
    }

    private fun initSubscriptions() {
        buttonAddProductListItem.setOnClickListener {
            visit.products.add(ProductUsageRecord())
            productListRecyclerAdapter.setDataSet(visit.products)
            productListRecyclerAdapter.notifyDataSetChanged()
        }
        disposable.add(RxTextView.textChangeEvents(etClientName)
                .map { input -> input.text().toString() }
                .subscribe {text -> presenter.onClientNameUpdate(text)});
        buttonSendVisit.setOnClickListener { presenter.onVisitSubmit() }
    }

    private fun initViewAdapters() {
        serviceSpinnerAdapter = ServiceSpinnerAdapter(this, arrayListOf())
        serviceSpinnerAdapter.setDropDownViewResource(R.layout.spinner_list_item_layout)
        spinnerServicesList.adapter = serviceSpinnerAdapter
        spinnerServicesList.onItemSelectedListener = servicesListOnSelectListener

        val layoutManager = LinearLayoutManager(this)
        recyclerViewProductList.layoutManager = layoutManager
    }

    private val productsRecyclerListener = object : RecyclerViewEventListener {
        override fun onRecycleItemSpinnerProductSelected(productId: String?, product: ProductUsageRecord) {
            product.productId = productId
            presenter.onProductListUpdate(visit.products)
        }

        override fun onRecycleItemRemoved(product: ProductUsageRecord) {
            visit.removeProduct(product)
            presenter.onProductListUpdate(visit.products)
        }

        override fun onRecycleItemTextChanged(text: String?, product: ProductUsageRecord) {
            if (!text.isNullOrEmpty()) {
                product.amount = text.toInt()
                presenter.onProductListUpdate(visit.products)
            }
        }
    }

    private val servicesListOnSelectListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            presenter.onServiceUpdate(serviceSpinnerAdapter.getItem(position))
        }
    }
}
