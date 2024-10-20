package com.umutgultekin.satelliteapp.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umutgultekin.satelliteapp.base.BaseDialogFragment
import com.umutgultekin.satelliteapp.databinding.DialogLoadingBinding

class LoadingDialog :
    BaseDialogFragment<DialogLoadingBinding>(DialogLoadingBinding::inflate) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        dialog?.setCancelable(false)
        return view
    }

    companion object {
        fun newInstance(): LoadingDialog {
            return LoadingDialog()
        }
    }
}