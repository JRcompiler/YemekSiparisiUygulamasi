package com.mustafakilic.yemeksiparisiuygulamasi.retrofit

import com.mustafakilic.yemeksiparisiuygulamasi.data.entity.CRUDCevap
import com.mustafakilic.yemeksiparisiuygulamasi.data.entity.SepetYemeklerCevap
import com.mustafakilic.yemeksiparisiuygulamasi.data.entity.YemeklerCevap
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface YemeklerDao {

    @GET("tumYemekleriGetir.php")
    fun tumYemekler(): Call<YemeklerCevap>

    @POST("sepeteYemekEkle.php")
    @FormUrlEncoded
    fun sepeteEkle(@Field("yemek_adi") yemek_adi: String,
                   @Field("yemek_resim_adi") yemek_resim_adi: String,
                   @Field("yemek_fiyat") yemek_fiyat: Int,
                   @Field("yemek_siparis_adet") yemek_siparis_adet: Int,
                   @Field("kullanici_adi") kullanici_adi:String): Call<CRUDCevap>

    @POST("sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    fun sepettekiYemekler(@Field("kullanici_adi") kullanici_adi: String) : Call<SepetYemeklerCevap>

    @POST("sepettenYemekSil.php")
    @FormUrlEncoded
    fun sepettenYemekSil(@Field("sepet_yemek_id") sepet_yemek_id:Int, @Field("kullanici_adi") kullanici_adi: String): Call<CRUDCevap>

}