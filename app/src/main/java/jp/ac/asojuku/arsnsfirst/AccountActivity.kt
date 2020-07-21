package jp.ac.asojuku.arsnsfirst


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import kotlinx.android.synthetic.main.activity_account.*
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import jp.ac.asojuku.arsnsfirst.R


class AccountActivity : AppCompatActivity() {

    private val TAG = "Show Message"
    var name:String? ="";
    var email:String? = "";
    var photoUrl:String? = "";
    var emailVerified:String? = "";
    var uid:String? = "";


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        getUserProfile()
        textMyName.text = name
        textMyEmail.text = email
    }

    override fun onResume(){
        super.onResume();

        //ログアウトボタン処理
        logoutBtn.setOnClickListener {
            signOut()
        }


    }

    private fun signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // ...
            }
        // [END auth_fui_signout]
        jumpAccount()
    }

    private fun getUserProfile() {
        // [START get_user_profile]
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            // Name, email address, and profile photo Url
            this.name = user.displayName
            this.email = user.email
            this.photoUrl = user.photoUrl.toString()

            // Check if user's email is verified
            this.emailVerified = user.isEmailVerified.toString()

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            this.uid = user.uid
        }
        // [END get_user_profile]
    }

    private fun jumpMain() {
        //インデント生成( どこからどこまで情報 )
        Handler().postDelayed(Runnable {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, 1000)
    }

    private fun jumpAccount(){
        Handler().postDelayed(Runnable {
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
        }, 1000)
    }

    private fun deleteUser() {
        // [START delete_user]
        val user = FirebaseAuth.getInstance().currentUser

        user?.delete()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User account deleted.")
                }
            }
        // [END delete_user]
        jumpAccount()
    }
}