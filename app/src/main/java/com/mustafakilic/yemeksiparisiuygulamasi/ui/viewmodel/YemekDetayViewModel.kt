package com.mustafakilic.yemeksiparisiuygulamasi.ui.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import com.mustafakilic.yemeksiparisiuygulamasi.data.repo.YemeklerDaoRepository

class YemekDetayViewModel : ViewModel() {

    val yrepo = YemeklerDaoRepository()

    fun sepeteEkle(yemek_adi: String, yemek_resim_adi: String, yemek_fiyat: Int, yemek_siparis_adet: Int, kullanici_adi: String){
        //yrepo.sepeteEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
        yrepo.sepeteEklemeKontrol(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
    }

    fun sepetiYukle(){
        yrepo.sepettekiYemekleriYukle()
    }
}