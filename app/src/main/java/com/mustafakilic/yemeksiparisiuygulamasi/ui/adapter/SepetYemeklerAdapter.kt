package com.mustafakilic.yemeksiparisiuygulamasi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mustafakilic.yemeksiparisiuygulamasi.R
import com.mustafakilic.yemeksiparisiuygulamasi.data.entity.SepetYemekler
import com.mustafakilic.yemeksiparisiuygulamasi.data.entity.Yemekler
import com.mustafakilic.yemeksiparisiuygulamasi.databinding.SepettekiYemeklerTasarimBinding
import com.mustafakilic.yemeksiparisiuygulamasi.ui.fragment.SepetFragmentDirections
import com.mustafakilic.yemeksiparisiuygulamasi.ui.fragment.YemekAnasayfaFragmentDirections
import com.mustafakilic.yemeksiparisiuygulamasi.ui.viewmodel.SepetViewModel

class SepetYemeklerAdapter(var mContext:Context,var sepetYemeklerListesi:List<SepetYemekler>,var viewModel:SepetViewModel):RecyclerView.Adapter<SepetYemeklerAdapter.YemeklerTasarimTutucu>(){

    inner class YemeklerTasarimTutucu(var binding:SepettekiYemeklerTasarimBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YemeklerTasarimTutucu {
        val binding:SepettekiYemeklerTasarimBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.sepetteki_yemekler_tasarim,parent,false)
        return YemeklerTasarimTutucu(binding)
    }

    override fun onBindViewHolder(holder: SepetYemeklerAdapter.YemeklerTasarimTutucu, position: Int) {

        val sepetYemek = sepetYemeklerListesi.get(position)
        val t = holder.binding



        var url = "http://kasimadalan.pe.hu/yemekler/resimler/${sepetYemek.yemek_resim_adi}"
        Glide.with(mContext).load(url).into(t.imageViewSepetResim)

        t.sepetYemekUrunAdet.text = sepetYemek.yemek_siparis_adet.toString()
        t.sepetYemekIsim.text = sepetYemek.yemek_adi
        t.sepetYemekFiyat.text = sepetYemek.yemek_fiyat.toString()+"₺"

        t.cardViewSepetTasarim.setOnClickListener { 
            val gecis = SepetFragmentDirections.sepetToSepetDetay()
            Navigation.findNavController(it).navigate(gecis)
        }

        t.imageButtonAdd.setOnClickListener {
            viewModel.sepeteEkle(sepetYemek.yemek_adi,sepetYemek.yemek_resim_adi,sepetYemek.yemek_fiyat,1,sepetYemek.kullanici_adi)
        }

        t.imageButtonRemove.setOnClickListener {
            if(sepetYemek.yemek_siparis_adet == 1){
                viewModel.sepettekilerinHepsiniSil(it)
            }
            viewModel.sepeteEkle(sepetYemek.yemek_adi,sepetYemek.yemek_resim_adi,sepetYemek.yemek_fiyat,-1,sepetYemek.kullanici_adi)
        }
    }

    override fun getItemCount(): Int {
        return sepetYemeklerListesi.size
    }


}