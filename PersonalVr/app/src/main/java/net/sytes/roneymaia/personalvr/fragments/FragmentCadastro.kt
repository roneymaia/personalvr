package net.sytes.roneymaia.personalvr.fragments

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.*
import net.sytes.roneymaia.personalvr.MainActivity

import net.sytes.roneymaia.personalvr.R


class FragmentCadastro : Fragment() {

    private var animation: ValueAnimator? = null
    private var arrowFrag: ImageView? = null
    private var mAuth: FirebaseAuth? = null
    private var contextAct: Activity? = null
    private var edtEmail: EditText? = null
    private var edtSenha: EditText? = null
    private var btnCadastrar: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        var mainView: View = inflater.inflate(R.layout.fragment_fragment_cadastro, container, false)

        contextAct = this@FragmentCadastro.activity as Activity

        edtEmail = mainView!!.findViewById(R.id.edtEmailFrag) as EditText
        edtSenha = mainView!!.findViewById(R.id.edtSenhaFrag) as EditText

        btnCadastrar = mainView!!.findViewById(R.id.btnCadastroFrag) as Button
        btnCadastrar!!.setOnClickListener { _ ->
            val email: String = this@FragmentCadastro.edtEmail!!.text.toString()
            val senha: String = this@FragmentCadastro.edtSenha!!.text.toString()

            if(!(MainActivity.isNullOrEmpty(email) || MainActivity.isNullOrEmpty(senha))){
                createUserEmail(email, senha)
            }
        }

        // Objeto autenticador do firebase
        mAuth = FirebaseAuth.getInstance() // obtem a instancia de autenticacao

        // Objeto de animaçãotr
        animation = ValueAnimator.ofFloat( 0f, resources!!.displayMetrics!!.heightPixels!!.toFloat())
        animation!!.duration = 1500
        animation!!.addUpdateListener { animation: ValueAnimator? ->
            mainView!!.translationY = animation!!.animatedValue as Float
            if((animation!!.animatedValue as Float) == resources!!.displayMetrics!!.heightPixels!!.toFloat()) {
                arrowFrag!!.rotation = 0f
            }
        }

        arrowFrag = mainView!!.findViewById(R.id.arrowFrag) as ImageView
        arrowFrag!!.setOnClickListener { _ ->
            this@FragmentCadastro.animation!!.start()
        }

        mainView.x = 0f
        mainView.y = resources!!.displayMetrics!!.heightPixels!!.toFloat()
        return mainView


    }

    private fun createUserEmail(email: String, password: String ){

        this@FragmentCadastro.mAuth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener(this@FragmentCadastro.contextAct!!) { task ->
            if (task!!.isSuccessful) {
                Toast.makeText(this@FragmentCadastro.contextAct, "Usuário cadastrado com sucesso.", Toast.LENGTH_SHORT).show()
                this@FragmentCadastro.animation!!.start()
            } else {

                try{
                    throw task.exception!!
                }catch (e: FirebaseAuthWeakPasswordException){
                    Toast.makeText(this@FragmentCadastro.contextAct, "A senha informada deve ter pelo menos 6 caracteres.", Toast.LENGTH_SHORT).show()
                }catch (e: FirebaseAuthEmailException){
                    Toast.makeText(this@FragmentCadastro.contextAct, e!!.message, Toast.LENGTH_SHORT).show()
                }catch (e: FirebaseAuthUserCollisionException){
                    Toast.makeText(this@FragmentCadastro.contextAct, "O email informado já existe.", Toast.LENGTH_SHORT).show()
                }catch (e: FirebaseAuthInvalidCredentialsException){
                    Toast.makeText(this@FragmentCadastro.contextAct, "Formato de email inválido.", Toast.LENGTH_SHORT).show()
                }catch (e: Exception){
                    Toast.makeText(this@FragmentCadastro.contextAct, "Falha ao criar usuário. Verifique o e-mail e senha. " + task.exception!!.message, Toast.LENGTH_SHORT).show()
                }


            }
        }

    }

}
