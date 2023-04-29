package com.mustafakilic.yemeksiparisiuygulamasi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mustafakilic.yemeksiparisiuygulamasi.R
import com.mustafakilic.yemeksiparisiuygulamasi.data.entity.Yemekler
import com.mustafakilic.yemeksiparisiuygulamasi.databinding.FragmentYemekAnasayfaBinding
import com.mustafakilic.yemeksiparisiuygulamasi.databinding.YemeklerTasarimBinding
import com.mustafakilic.yemeksiparisiuygulamasi.ui.fragment.YemekAnasayfaFragmentDirections
import com.mustafakilic.yemeksiparisiuygulamasi.ui.viewmodel.YemekAnasayfaViewModel

class YemeklerAdapter(var mContext:Context, var yemeklerListesi:List<Yemekler>,var viewModel:YemekAnasayfaViewModel) : RecyclerView.Adapter<YemeklerAdapter.YemeklerTasarimTutucu>() {

    inner class YemeklerTasarimTutucu(var binding:YemeklerTasarimBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YemeklerTasarimTutucu {
        val binding:YemeklerTasarimBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.yemekler_tasarim,parent,false)
        return YemeklerTasarimTutucu(binding)
    }

    override fun onBindViewHolder(holder: YemeklerTasarimTutucu, position: Int) {
        val yemek = yemeklerListesi.get(position)
        val t = holder.binding

        t.textViewYemekAdi.text = yemek.yemek_adi
        t.textViewYemekFiyat.text = yemek.yemek_fiyat.toString()+"₺"


        var url = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}"
        Glide.with(mContext).load(url).override(300,300).into(t.imageViewYemek)

        t.cardViewYemek.setOnClickListener {
            val gecis = YemekAnasayfaFragmentDirections.anasayfaToDetay(yemek = yemek)
            Navigation.findNavController(it).navigate(gecis)
            println("gecise kadar çalışıyor.")
        }
    }

    override fun getItemCount(): Int {
        return yemeklerListesi.size
    }
}