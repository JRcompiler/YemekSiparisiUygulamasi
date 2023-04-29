package com.mustafakilic.yemeksiparisiuygulamasi.ui.viewmodel

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.mustafakilic.yemeksiparisiuygulamasi.data.entity.SepetYemekler
import com.mustafakilic.yemeksiparisiuygulamasi.data.repo.YemeklerDaoRepository

class SepetViewModel : ViewModel() {

    private val yrepo = YemeklerDaoRepository()
    val sepetYemekListesi : MutableLiveData<List<SepetYemekler>>

    init {
        sepettekiYemekleriYukle()
        sepetYemekListesi = yrepo.sepettekiYemekleriGetir()
    }

    fun sepettekiYemekleriYukle(){
        yrepo.sepettekiYemekleriYukle()
    }

    fun sepeteEkle(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int,yemek_siparis_adet:Int,kullanici_adi:String){
        yrepo.sepeteEklemeKontrol(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
    }

    fun sepettenYemekSilTek(sepet_yemek_id: Int, kullanici_adi: String){
        yrepo.sepettenYemekSil(sepet_yemek_id,kullanici_adi)
    }

    fun sepettekilerinHepsiniSil(btn: View){

        val alert = AlertDialog.Builder(btn.context)
        alert.setTitle("Sil")
        alert.setMessage("Sepettekilerin hepsi silinsin mi ?")
        alert.setPositiveButton("Evet"){dialog,which ->
            yrepo.sepettekilerinHepsiniSil()
            Toast.makeText(btn.context,"Sepettekiler silindi", Toast.LENGTH_LONG).show()
        }

        alert.show()
    }

    fun sepettenYemekSil(sepet_yemek_id: Int, kullanici_adi: String){
        yrepo.sepettenYemekSil(sepet_yemek_id,kullanici_adi)
    }

}