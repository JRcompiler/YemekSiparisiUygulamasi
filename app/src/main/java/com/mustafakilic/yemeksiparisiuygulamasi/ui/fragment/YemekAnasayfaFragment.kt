package com.mustafakilic.yemeksiparisiuygulamasi.ui.fragment

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.mustafakilic.yemeksiparisiuygulamasi.R
import com.mustafakilic.yemeksiparisiuygulamasi.databinding.FragmentYemekAnasayfaBinding
import com.mustafakilic.yemeksiparisiuygulamasi.databinding.YemeklerTasarimBinding
import com.mustafakilic.yemeksiparisiuygulamasi.ui.adapter.YemeklerAdapter
import com.mustafakilic.yemeksiparisiuygulamasi.ui.viewmodel.YemekAnasayfaViewModel

class YemekAnasayfaFragment : Fragment() {

    private lateinit var binding: FragmentYemekAnasayfaBinding
    private lateinit var viewModel : YemekAnasayfaViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_yemek_anasayfa,container,false)
        binding.yemekAnasayfaFragment = this


        viewModel.yemeklerListesi.observe(viewLifecycleOwner){
            val adapter = YemeklerAdapter(requireContext(),it,viewModel)
            binding.yemeklerAdapter = adapter
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : YemekAnasayfaViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.yemekleriYukle()
    }

    fun sepeteGit(it : View){
        //val gecis = YemekAnasayfaFragmentDirections.anasayfaToDetay(yemek = yemek)
        val gecis = YemekAnasayfaFragmentDirections.anasayfaToSepet()
        Navigation.findNavController(it).navigate(gecis)
    }

}