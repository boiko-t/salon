package com.boiko.taisa.salon.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.Spinner
import com.boiko.taisa.salon.R
import com.boiko.taisa.salon.domain.entity.Product
import com.boiko.taisa.salon.domain.entity.ProductUsageRecord
import com.boiko.taisa.salon.domain.entity.SalonService
import com.boiko.taisa.salon.domain.entity.Visit
import com.boiko.taisa.salon.mvp.visit.NewVisit
import com.boiko.taisa.salon.mvp.visit.NewVisitModel
import com.boiko.taisa.salon.mvp.visit.NewVisitPresenter
import com.boiko.taisa.salon.ui.adapter.ServiceSpinnerAdapter
import com.boiko.taisa.salon.ui.event.RecyclerViewEventListener
import com.boiko.taisa.salon.ui.recyclerview.ProductRecyclerViewAdapter
import io.reactivex.disposables.CompositeDisposable

class NewVisitActivity : AppCompatActivity(), NewVisit.View {
    private lateinit var presenter: NewVisitPresenter
    private lateinit var disposable: CompositeDisposable

    private lateinit var buttonAddProductListItem: ImageButton
    private lateinit var spinnerServicesList: Spinner
    private lateinit var recyclerViewProductList: RecyclerView
    private lateinit var serviceSpinnerAdapter: ServiceSpinnerAdapter
    private lateinit var productListRecyclerAdapter: ProductRecyclerViewAdapter

    private var visit = NewVisitModel.getInstance().state.visit
// ToDO client name
    private val servicesListOnSelectListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            presenter.onServiceUpdate(serviceSpinnerAdapter.getItem(position))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_visit)
        findViews()
        initViewAdapters()
        initViewEventListeners()
        presenter = NewVisitPresenter()
        disposable = CompositeDisposable()
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

    private fun initViewEventListeners() {
        buttonAddProductListItem.setOnClickListener {
            visit.products.add(ProductUsageRecord())
            productListRecyclerAdapter.setDataSet(visit.products)
            productListRecyclerAdapter.notifyDataSetChanged()
        }
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
}
