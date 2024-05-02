package com.oybekdev.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.oybekdev.domain.model.User
import com.oybekdev.presentation.screens.phone.PhoneViewModel
import io.reactivex.rxjava3.core.Observable

abstract class BaseFragment<VB:ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB
) :Fragment(){

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding =null
    }

    fun <T : Any, R : Any> Observable<T>.observe(observer: (R) -> Unit, mapper: (T) -> R){
        map(mapper).distinctUntilChanged().doOnNext(observer)
    }
}