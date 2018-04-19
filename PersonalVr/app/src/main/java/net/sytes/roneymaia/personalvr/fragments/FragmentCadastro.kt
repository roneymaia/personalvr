package net.sytes.roneymaia.personalvr.fragments

import android.animation.ValueAnimator
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView

import net.sytes.roneymaia.personalvr.R


class FragmentCadastro : Fragment() {

    private var animation: ValueAnimator? = null
    private var arrowFrag: ImageView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var mainView: View = inflater.inflate(R.layout.fragment_fragment_cadastro, container, false)

        // Objeto de animaçãotr
        animation = ValueAnimator.ofFloat( 0f, resources!!.displayMetrics!!.heightPixels!!.toFloat())
        animation!!.duration = 1500
        animation!!.addUpdateListener { animation: ValueAnimator? ->
            mainView!!.translationY = animation!!.animatedValue as Float
        }

        arrowFrag = mainView!!.findViewById(R.id.arrowFrag) as ImageView
        arrowFrag!!.setOnClickListener { _ ->
            this@FragmentCadastro.animation!!.start()
        }


        mainView.x = 0f
        mainView.y = resources!!.displayMetrics!!.heightPixels!!.toFloat()
        return mainView
    }

}
