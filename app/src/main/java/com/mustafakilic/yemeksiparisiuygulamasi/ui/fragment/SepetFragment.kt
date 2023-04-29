package com.mustafakilic.yemeksiparisiuygulamasi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mustafakilic.yemeksiparisiuygulamasi.R
import com.mustafakilic.yemeksiparisiuygulamasi.data.entity.SepetYemekler
import com.mustafakilic.yemeksiparisiuygulamasi.databinding.FragmentSepetBinding
import com.mustafakilic.yemeksiparisiuygulamasi.ui.adapter.SepetYemeklerAdapter
import com.mustafakilic.yemeksiparisiuygulamasi.ui.viewmodel.SepetViewModel
import com.mustafakilic.yemeksiparisiuygulamasi.ui.viewmodel.YemekDetayViewModel

class SepetFragment : Fragment() {

    private lateinit var binding : FragmentSepetBinding
    private lateinit var viewModel: SepetViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sepet,container,false)
        binding.sepetFragment = this

        viewModel.sepetYemekListesi.observe(viewLifecycleOwner){


            var toplamFiyat = 0
            it.forEach { toplamFiyat += it.yemek_fiyat*it.yemek_siparis_adet }
            binding.textViewSepetTutarFiyat.text = toplamFiyat.toString()

            val adapter = SepetYemeklerAdapter(requireContext(),it,viewModel)
            binding.sepetYemeklerAdapter = adapter
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: SepetViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        viewModel.sepettekiYemekleriYukle()
        super.onResume()
    }

    fun anasayfaGecis(fab:View){
        val gecis = SepetFragmentDirections.sepetToAnasayfa()
        Navigation.findNavController(fab).navigate(gecis)
    }

    fun sepettekilerinHepsiniSil(btn:View){
        viewModel.sepettekilerinHepsiniSil(btn)
    }

}