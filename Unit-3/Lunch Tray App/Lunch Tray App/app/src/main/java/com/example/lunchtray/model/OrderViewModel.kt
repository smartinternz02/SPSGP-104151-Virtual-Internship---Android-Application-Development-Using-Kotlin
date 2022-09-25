package com.example.lunchtray.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.lunchtray.data.DataSource
import java.text.NumberFormat

class OrderViewModel : ViewModel() {

    val menuItems = DataSource.menuItems

    private var previousEntreePrice = 0.0
    private var previousSidePrice = 0.0
    private var previousAccompanimentPrice = 0.0

    private val taxRate = 0.08

    private val _entree = MutableLiveData<MenuItem?>()
    val entree: LiveData<MenuItem?> = _entree

    private val _side = MutableLiveData<MenuItem?>()
    val side: LiveData<MenuItem?> = _side

    private val _accompaniment = MutableLiveData<MenuItem?>()
    val accompaniment: LiveData<MenuItem?> = _accompaniment

    private val _subtotal = MutableLiveData(0.0)
    val subtotal: LiveData<String> = Transformations.map(_subtotal) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    private val _total = MutableLiveData(0.0)
    val total: LiveData<String> = Transformations.map(_total) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    private val _tax = MutableLiveData(0.0)
    val tax: LiveData<String> = Transformations.map(_tax) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    fun setEntree(entree: String) {
        if(_entree.value != null){
            previousEntreePrice = _entree.value!!.price
        }

        if(_subtotal.value != null){
            _subtotal.value = _subtotal.value!! - previousEntreePrice
        }

        _entree.value = menuItems[entree]
        _entree.value?.let { updateSubtotal(it.price) }
    }

    fun setSide(side: String) {
        if(_side.value != null){
            previousSidePrice = _side.value!!.price
        }

        if(_subtotal.value != null){
            _subtotal.value = _subtotal.value!! - previousSidePrice
        }

        _side.value = menuItems[side]

        _side.value?.let { updateSubtotal(it.price) }
    }

    fun setAccompaniment(accompaniment: String) {
        if(_accompaniment.value != null){
            previousAccompanimentPrice = _accompaniment.value!!.price
        }

        if(_accompaniment.value != null){
            _subtotal.value = _subtotal.value!! - previousAccompanimentPrice
        }

        _accompaniment.value = menuItems[accompaniment]

        _accompaniment.value?.let { updateSubtotal(it.price) }
    }

    private fun updateSubtotal(itemPrice: Double) {
        if(_subtotal.value != null){
            _subtotal.value = _subtotal.value!! + itemPrice
        }

        calculateTaxAndTotal()
    }

    fun calculateTaxAndTotal() {
        _tax.value = _subtotal.value?.times(taxRate)

        _total.value = _subtotal.value?.plus(_tax.value!!)
    }

    fun resetOrder() {
        previousEntreePrice = 0.0
        previousSidePrice = 0.0
        previousAccompanimentPrice = 0.0

        _entree.value = null
        _side.value = null
        _accompaniment.value = null

        _subtotal.value = 0.0
        _total.value = 0.0
        _tax.value = 0.0
    }
}
