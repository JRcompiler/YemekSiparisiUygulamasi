package com.mustafakilic.yemeksiparisiuygulamasi.data.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mustafakilic.yemeksiparisiuygulamasi.data.entity.*
import com.mustafakilic.yemeksiparisiuygulamasi.retrofit.ApiUtils
import com.mustafakilic.yemeksiparisiuygulamasi.retrofit.YemeklerDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YemeklerDaoRepository {

    var yemeklerListesi:MutableLiveData<List<Yemekler>>
    var sepetYemeklerListesi:MutableLiveData<List<SepetYemekler>>
    var ydao:YemeklerDao

    init{
        ydao = ApiUtils.getYemeklerDao()
        yemeklerListesi = MutableLiveData()
        sepetYemeklerListesi = MutableLiveData()
        println("init çalışıyor")
    }

    fun yemekleriGetir() : MutableLiveData<List<Yemekler>>{
        return yemeklerListesi
    }

    fun sepettekiYemekleriGetir() : MutableLiveData<List<SepetYemekler>>{
        return sepetYemeklerListesi
    }

    fun yemekleriYukle(){
        ydao.tumYemekler().enqueue(object: Callback<YemeklerCevap>{
            override fun onResponse(call: Call<YemeklerCevap>, response: Response<YemeklerCevap>) {
                yemeklerListesi.value = response.body()!!.yemekler
            }

            override fun onFailure(call: Call<YemeklerCevap>, t: Throwable) {
                println("Error!!! yemekler anasayfaya yüklenmedi.")
            }

        })
    }

    fun sepettekiYemekleriYukle(){
        ydao.sepettekiYemekler("mustafa").enqueue(object : Callback<SepetYemeklerCevap>{
            override fun onResponse(call: Call<SepetYemeklerCevap>, response: Response<SepetYemeklerCevap>) {

                println("Cevap : "+response.body()!!.success)
                val list = response.body()!!.sepet_yemekler

                if((response.body()!!.sepet_yemekler) == null){
                    println("Dönen bir cevap var")
                }

                sepetYemeklerListesi.value = list
            }
            override fun onFailure(call: Call<SepetYemeklerCevap>, t: Throwable) {
                println("Sepetteki Yemek Listesi boş. Ekleme yapmalısın.")
                sepetYemeklerListesi.value = emptyList()
            }

        })
    }


    fun sepettenYemekSil(sepet_yemek_id: Int, kullanici_adi: String) {
        ydao.sepettenYemekSil(sepet_yemek_id, kullanici_adi).enqueue(object : Callback<CRUDCevap> {
            override fun onResponse(call: Call<CRUDCevap>, response: Response<CRUDCevap>) {
                println("Sepet silme methodu çalışıyor")
                sepettekiYemekleriYukle()
            }
            override fun onFailure(call: Call<CRUDCevap>, t: Throwable) {}
        })
    }

    fun sepeteEkle(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int,yemek_siparis_adet:Int,kullanici_adi:String){
        ydao.sepeteEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi).enqueue(object : Callback<CRUDCevap>{
            override fun onResponse(call: Call<CRUDCevap>, response: Response<CRUDCevap>) {
                sepettekiYemekleriYukle()
                println("sepeteEkle metodu çalışıyor.")
            }
            override fun onFailure(call: Call<CRUDCevap>, t: Throwable) {
                println("Sepetteki Yemekler Getirilemiyor!")
            }
        })
    }

    fun sepeteEklemeKontrol(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int,yemek_siparis_adet:Int,kullanici_adi:String){
        sepettekiYemekleriYukle() //en son sepettekilerin güncel halini aldık
        var ayniYemekVarmi = false
        var ayniYemekId = 0
        var ayniYemekAdet = 0
        println("sepeteEklemeKontrol")
        if(sepetYemeklerListesi.value != null) {
            println("sepeteEklemeKontrol")
            for (sepettekiYemekler in sepetYemeklerListesi.value!!) {
                println("sepeteEklemeKontrol")
                if (yemek_adi == sepettekiYemekler.yemek_adi) {
                    println("sepeteEklemeKontrol")
                    ayniYemekVarmi = true
                    ayniYemekId = sepettekiYemekler.sepet_yemek_id
                    ayniYemekAdet = sepettekiYemekler.yemek_siparis_adet
                }
            }

            if(ayniYemekVarmi){
                sepettenYemekSil(ayniYemekId,kullanici_adi)
            }

            sepeteEkle(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet + ayniYemekAdet, kullanici_adi)

            println("fordan sonra çıkıyor")
        }
        else{
            sepeteEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
        }
    }

    fun sepettekilerinHepsiniSil() {
        ydao.sepettekiYemekler("mustafa").enqueue(object : Callback<SepetYemeklerCevap> {
            override fun onResponse(call: Call<SepetYemeklerCevap>, response: Response<SepetYemeklerCevap>) {
                sepetYemeklerListesi.value = response.body()!!.sepet_yemekler
                for (eleman in sepetYemeklerListesi.value!!) { sepettenYemekSil(eleman.sepet_yemek_id, eleman.kullanici_adi) }
                sepettekiYemekleriYukle()
            }
            override fun onFailure(call: Call<SepetYemeklerCevap>, t: Throwable) {}
        })
    }

}