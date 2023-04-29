package com.mustafakilic.yemeksiparisiuygulamasi.ui.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mustafakilic.yemeksiparisiuygulamasi.data.entity.Yemekler
import com.mustafakilic.yemeksiparisiuygulamasi.data.repo.YemeklerDaoRepository

class YemekAnasayfaViewModel : ViewModel() {

    val yrepo = YemeklerDaoRepository()
    var yemeklerListesi : MutableLiveData<List<Yemekler>>

    init {
        yemekleriYukle()
        yemeklerListesi = yrepo.yemekleriGetir()
    }

    fun yemekleriYukle(){
        yrepo.yemekleriYukle()
    }
}