package net.sytes.roneymaia.personalvr

import android.animation.ValueAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast

import android.util.Log
import android.view.View
import com.facebook.CallbackManager
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.FacebookCallback
import com.facebook.login.widget.LoginButton
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FacebookAuthProvider
import com.facebook.AccessToken
import com.google.android.gms.common.SignInButton
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.*
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MainActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var loginButton: LoginButton? = null
    private var mCallbackManager: CallbackManager? = null
    private var customFbButton: Button? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var googleButton: SignInButton? = null
    private var customGoogleButton: Button? = null
    private var viewCanvas: View? = null
    private var btnEntrar: Button? = null
    private var txtEmail: EditText? = null
    private var txtSenha: EditText? = null
    private var animation: ValueAnimator? = null
    private var metricsMain: DisplayMetrics? = null
    private var viewFrag: View? = null
    private var btnCadastrar: Button? = null

    companion object {
        const val PVR_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        metricsMain = resources.displayMetrics

        SingletonControlCanvas.bitmapCv = BitmapFactory.decodeResource(resources, R.drawable.googleicon)
        viewCanvas = findViewById<CustomViewCanvas>(R.id.viewCanvas) // canvas view customizada

        mAuth = FirebaseAuth.getInstance() // obtem a instancia de autenticacao

        // ##### Firebase AUTH #####

        mCallbackManager = CallbackManager.Factory.create()

        // ##### LoginButton Facebook #####
        loginButton = findViewById<View>(R.id.facebookLoginButton) as LoginButton
        loginButton!!.setReadPermissions("email")

        // Callback registration
        loginButton!!.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
                signInFacebook(loginResult.accessToken)
            }

            override fun onCancel() {
                Toast.makeText(applicationContext, "Cancel", Toast.LENGTH_SHORT).show()
            }

            override fun onError(exception: FacebookException) {
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
            }
        })

        // ##### Google AUTH #####
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this@MainActivity, gso)

        googleButton = findViewById<View>(R.id.googleLoginButton) as SignInButton
        googleButton!!.setOnClickListener { _ ->
            val signInIntent = this@MainActivity.mGoogleSignInClient!!.signInIntent
            startActivityForResult(signInIntent, PVR_CODE) }

        // ##### CustomFacebook button login #####
        customFbButton = findViewById<View>(R.id.customFacebookButton) as Button
        customFbButton!!.setOnClickListener { _ ->
            this@MainActivity.loginButton!!.performClick() }

        // ##### CustomGoogleButton #####
        customGoogleButton = findViewById<View>(R.id.customGoogleButton) as Button
        customGoogleButton!!.setOnClickListener { _ ->
            for (i: Int in 0..googleButton!!.childCount){
                var view: View = this@MainActivity.googleButton!!.getChildAt(i)

                if(view is Button){
                    view.performClick()
                    return@setOnClickListener
                }
            }

        }

        txtEmail = findViewById<View>(R.id.txtEmail) as EditText
        txtSenha = findViewById<View>(R.id.txtSenha) as EditText

        // Evento de click no botão de entrar
        btnEntrar = findViewById<View>(R.id.btnEntrar) as Button
        btnEntrar!!.setOnClickListener { _ ->
            val email = this@MainActivity.txtEmail!!.text!!.toString()
            val senha = this@MainActivity.txtSenha!!.text!!.toString()

            if(!(isNullOrEmpty(email) || isNullOrEmpty(senha))){
                signInEmailAndPassword(email, senha)
            }

        }

        btnCadastrar = findViewById<View>(R.id.buttonCadastrar) as Button
        btnCadastrar!!.setOnClickListener { _ ->

            this@MainActivity.viewFrag = supportFragmentManager!!.findFragmentById(R.id.mainFragment).view
            this@MainActivity.animation!!.start()
        }

        // Objeto de animação
        animation = ValueAnimator.ofFloat(metricsMain!!.heightPixels.toFloat(), 0f)
        animation!!.duration = 2000
        animation!!.addUpdateListener { animation: ValueAnimator? ->
            this@MainActivity.viewFrag!!.translationY = animation!!.animatedValue as Float
        }

    }

    private fun createUserEmail(email: String, password: String ): Boolean{

        var success = false

        this@MainActivity.mAuth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener(this@MainActivity) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("UserCreate", "createUserWithEmail:success")
                success = true
            } else {
                // If sign in fails, display a message to the user.
                Log.w("UserCreate", "createUserWithEmail:failure", task.exception)
                Toast.makeText(this@MainActivity, "falha ao criar usuario", Toast.LENGTH_SHORT).show()
                success = false
            }
        }
        return success
    }

    private fun signInEmailAndPassword(email: String, password: String) : Boolean{

        var success = false

        this@MainActivity.mAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener(this@MainActivity) { task ->
            if (task.isSuccessful) {
                Log.d("UserLogin", "signInWithEmail:success")
                success = true
            } else {
                Log.w("UserLogin", "signInWithEmail:failure", task.exception)
                Toast.makeText(this@MainActivity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                success = false
            }
        }
        return success
    }

    private fun signInFacebook(token: AccessToken) {

        Log.d("AcessToken", "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        this@MainActivity.mAuth?.signInWithCredential(credential)?.addOnCompleteListener(this@MainActivity) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this@MainActivity, "Auth Firebase Facebook", Toast.LENGTH_SHORT).show()
                val user: FirebaseUser = mAuth!!.currentUser!!
            } else {
                Log.d("FacebookLogin", "signInWithEmail:failure")
                Toast.makeText(this@MainActivity, "Authentication Facebook FireBase failed.",
                        Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        this@MainActivity.mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@MainActivity, "Authentication Google Success.",
                                Toast.LENGTH_SHORT).show()
                        val user: FirebaseUser? = mAuth!!.getCurrentUser()
                    } else {
                        Toast.makeText(this@MainActivity, "Authentication Google Firebase failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === MainActivity.PVR_CODE) {
             val result: GoogleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess()) {
                val account: GoogleSignInAccount = result.getSignInAccount()!!
                firebaseAuthWithGoogle(account)
            } else {

            }
        }else{
            this@MainActivity.mCallbackManager!!.onActivityResult(requestCode, resultCode, data)
        }

     }

    fun isNullOrEmpty(str: String?): Boolean{
        if(str != null && !str.isEmpty()){
            return false
        }
        return true
    }

}
