package net.sytes.roneymaia.personalvr.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import net.sytes.roneymaia.personalvr.R


class FragmentCadastro : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var mainView: View = inflater.inflate(R.layout.fragment_fragment_cadastro, container, false)
        mainView.x = 0f
        mainView.y = resources!!.displayMetrics!!.heightPixels!!.toFloat()
        return mainView
    }

}
