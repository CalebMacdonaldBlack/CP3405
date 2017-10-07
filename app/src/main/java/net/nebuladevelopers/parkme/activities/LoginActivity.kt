package net.nebuladevelopers.parkme.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import net.nebuladevelopers.parkme.Parkme
import net.nebuladevelopers.parkme.R
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import net.nebuladevelopers.parkme.utils.Authentication


class LoginActivity : AppCompatActivity() {
    private lateinit var parkme: Parkme
    private val emailRegex = Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+\$")
    private val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        parkme = (application as Parkme)
    }

    private fun disableLoginScreen(){
        email_edit_text.isEnabled = false
        email_edit_text.alpha = 0.4f
        password_edit_text.isEnabled = false
        password_edit_text.alpha = 0.4f
        sign_up_button.isEnabled = false
        sign_up_button.alpha = 0.4f
        sign_in_button.isEnabled = false
        sign_in_button.alpha = 0.4f
    }

    private fun enableLoginScreen(){
        email_edit_text.isEnabled = true
        email_edit_text.alpha = 1f
        password_edit_text.isEnabled = true
        password_edit_text.alpha = 1f
        sign_up_button.isEnabled = true
        sign_up_button.alpha = 1f
        sign_in_button.isEnabled = true
        sign_in_button.alpha = 1f
    }

    override fun onStart() {
        super.onStart()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        email_edit_text.setOnEditorActionListener { editText, actionId, event ->
            if (email_edit_text.text.matches(emailRegex)) {
                email_error_message_text_view.visibility = View.GONE
                parkme.email = email_edit_text.text.toString()
                false
            } else {
                email_error_message_text_view.visibility = View.VISIBLE
                true
            }
        }
        password_edit_text.setOnEditorActionListener { editText, actionId, event ->
            if (password_edit_text.text.matches(passwordRegex)) {
                password_error_message_text_view.visibility = View.GONE
                parkme.password = email_edit_text.text.toString()
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                true
            } else {
                password_error_message_text_view.visibility = View.VISIBLE
                true
            }
        }
        sign_up_button.setOnClickListener {
            disableLoginScreen()
            println("sign up clicked")
            val email = email_edit_text.text
            val password = password_edit_text.text
            if (email.matches(emailRegex) && password.matches(passwordRegex)) {
                Authentication.signUp(
                        parkme, email.toString(),
                        password.toString(),
                        {
                            startActivity(Intent(applicationContext, HomeActivity::class.java))
                        },
                        { e ->
                            println(e)
                            enableLoginScreen()
                            if (e is FirebaseAuthUserCollisionException) {
                                authentication_error_text_view.text = applicationContext.getString(R.string.account_already_exists)
                                authentication_error_text_view.visibility = View.VISIBLE
                            }
                        })
            }
        }
        sign_in_button.setOnClickListener {
            disableLoginScreen()
            println("sign in clicked")
            val email = email_edit_text.text
            val password = password_edit_text.text
            if (email.matches(emailRegex) && password.matches(passwordRegex)) {
                Authentication.signIn(
                        parkme, email.toString(),
                        password.toString(),
                        {
                            startActivity(Intent(applicationContext, HomeActivity::class.java))
                        },
                        { e ->
                            enableLoginScreen()
                            println(e)
                            if(e is FirebaseAuthInvalidUserException) {
                                authentication_error_text_view.text = applicationContext.getString(R.string.account_doesnt_exist_error_message)
                                authentication_error_text_view.visibility = View.VISIBLE
                            } else if (e is FirebaseAuthInvalidCredentialsException) {
                                authentication_error_text_view.visibility = View.VISIBLE
                                authentication_error_text_view.text = applicationContext.getString(R.string.invalid_credentials)
                            }
                        })
            }
        }
    }
}

//FONTSji
//Microsoft Yi Baiti
//for Drive park learn
//and Eras Medium ITC and Eras Light for other things

