package org.wit.animarker.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import org.wit.animarker.R
import timber.log.Timber.i
import org.wit.animarker.databinding.ActivityLoginBinding
import org.wit.animarker.main.MainApp
import org.wit.animarker.models.UserModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    var user = UserModel()

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        app = application as MainApp
        i("Login Activity Started...")

        binding.btnSubmit.setOnClickListener() {
            var email = binding.userEmail.text.toString()
            var password = binding.userPassword.text.toString()
            if (app.users.login(email, password)) {
                val launcherIntent = Intent(this,AnimarkerListActivity::class.java)
                startActivityForResult(launcherIntent, 0)
            } else {
                val msg = "Invalid login details, try again"
                Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_LONG).show()
            }
        }
        binding.btnSignup.setOnClickListener() {
            user.firstName = binding.firstName.text.toString()
            user.lastName = binding.lastName.text.toString()
            user.userEmail = binding.newUserEmail.text.toString()
            user.userPassword = binding.newUserPassword.text.toString()

            if (user.firstName.isNotEmpty() && user.lastName.isNotEmpty()) {
                app.users.create(user.copy())
                i("sign up Button Pressed: $user")
                setResult(RESULT_OK)
                finish()
            }
            val launcherIntent = Intent(this, AnimarkerListActivity::class.java)
            startActivityForResult(launcherIntent, 0)
        }
    }
}