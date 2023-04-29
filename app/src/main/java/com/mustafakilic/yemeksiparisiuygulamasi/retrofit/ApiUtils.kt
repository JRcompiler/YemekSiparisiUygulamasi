package com.mustafakilic.yemeksiparisiuygulamasi.retrofit

class ApiUtils {
    companion object{
        val BASE_URL = "http://kasimadalan.pe.hu/yemekler/"

        fun getYemeklerDao(): YemeklerDao{
            return RetrofitClient.getClient(BASE_URL).create(YemeklerDao::class.java)
        }
    }
}