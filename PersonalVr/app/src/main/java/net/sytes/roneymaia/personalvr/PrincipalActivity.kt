package net.sytes.roneymaia.personalvr

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class PrincipalActivity : AppCompatActivity() {

    private var btnSignOut: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        btnSignOut = findViewById<View>(R.id.btnSignOut) as Button
        btnSignOut!!.setOnClickListener { _ ->
            // remove o login com facebook
            LoginManager.getInstance().logOut()
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
            // remove o login com google
            GoogleSignIn.getClient(this@PrincipalActivity, gso).signOut()
            // remove os logins
            FirebaseAuth.getInstance().signOut()
            finish()
        }
    }

    override fun onBackPressed() {

    }
}
