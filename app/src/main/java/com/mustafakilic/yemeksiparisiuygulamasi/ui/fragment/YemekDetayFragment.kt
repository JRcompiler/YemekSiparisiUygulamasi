package com.mustafakilic.yemeksiparisiuygulamasi.ui.fragment

import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.mustafakilic.yemeksiparisiuygulamasi.R
import com.mustafakilic.yemeksiparisiuygulamasi.data.entity.Yemekler
import com.mustafakilic.yemeksiparisiuygulamasi.databinding.FragmentYemekDetayBinding
import com.mustafakilic.yemeksiparisiuygulamasi.ui.viewmodel.YemekAnasayfaViewModel
import com.mustafakilic.yemeksiparisiuygulamasi.ui.viewmodel.YemekDetayViewModel

class YemekDetayFragment : Fragment() {

    private lateinit var binding: FragmentYemekDetayBinding
    private lateinit var viewModel: YemekDetayViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_yemek_detay,container,false)
        binding.yemekDetayFragment = this

        val bundle:YemekDetayFragmentArgs by navArgs()
        val gelenYemek = bundle.yemek

        binding.secilenYemek = gelenYemek
        binding.yemekFiyati = gelenYemek.yemek_fiyat.toString()+"₺"

        var url = "http://kasimadalan.pe.hu/yemekler/resimler/${gelenYemek.yemek_resim_adi}"
        Glide.with(this).load(url).into(binding.ivGelenYemek)

        binding.adetSayisi = "1"

        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : YemekDetayViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.sepetiYukle()
    }

    fun adetArttir(view: View){
        binding.adetSayisi = ((binding.adetSayisi)!!.toInt()+1).toString()
    }

    fun adetAzalt(view: View){
        binding.adetSayisi = ((binding.adetSayisi)!!.toInt()-1).toString()
    }

    fun anasayfayaGecis(fab:View){

        val gecis = YemekDetayFragmentDirections.yemekDetaytoAnasayfa()
        Navigation.findNavController(fab).navigate(gecis)
    }

    fun sepeteGecis(fab:View){
        val gecis = YemekDetayFragmentDirections.yemekDetayToSepet()
        Navigation.findNavController(fab).navigate(gecis)
    }

    fun yemegiSepeteEkle(yemek_adi: String, yemek_resim_adi: String, yemek_fiyat: Int, yemek_siparis_adet: String, kullanici_adi: String){
        viewModel.sepeteEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet.toInt(),kullanici_adi)
        Toast.makeText(context,"yemeginiz sepete eklenmiştir.",Toast.LENGTH_LONG).show()
    }

}