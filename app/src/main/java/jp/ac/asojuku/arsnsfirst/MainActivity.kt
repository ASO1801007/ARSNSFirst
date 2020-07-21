package jp.ac.asojuku.arsnsfirst

import android.app.Activity
import android.content.Intent
import android.media.AudioManager
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import jp.ac.asojuku.arsnsfirst.R
import jp.ac.asojuku.arsnsfirst.AccountActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume(){
        super.onResume();

        //ログインボタン処理
        loginBtn.setOnClickListener {
            createSignInIntent()
        }
        //ログインボタン処理
        entryBtn.setOnClickListener {
            jumpEntry()
        }
    }

    private fun createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN)
        // [END auth_fui_create_intent]
    }

    // [START auth_fui_result]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                //インデント生成( どこからどこまで情報 )
                jumpAccount()
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
    // [END auth_fui_result]

    companion object {

        private const val RC_SIGN_IN = 123
    }

    private fun jumpAccount(){
        Handler().postDelayed(Runnable {
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
        }, 1000)
    }

    private fun jumpEntry(){
        Handler().postDelayed(Runnable {
            val intent = Intent(this, EntryActivity::class.java)
            startActivity(intent)
        }, 1000)
    }
}
