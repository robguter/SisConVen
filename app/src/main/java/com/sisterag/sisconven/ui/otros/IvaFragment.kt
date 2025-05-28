package com.sisterag.sisconven.ui.otros

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sisterag.sisconven.R

/**
 * A simple [Fragment] subclass.
 * Use the [IvaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IvaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_iva, container, false)
    }

}