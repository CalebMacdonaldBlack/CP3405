package net.nebuladevelopers.parkme.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import net.nebuladevelopers.parkme.Parkme
import net.nebuladevelopers.parkme.R
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager


class LoginActivity : AppCompatActivity() {
    private lateinit var parkme: Parkme
    private lateinit var mAuth: FirebaseAuth
    private val emailRegex = Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+\$")
    private val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        parkme = (application as Parkme)
        FirebaseApp.initializeApp(this)

        mAuth = FirebaseAuth.getInstance()
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
    }
}

//FONTSji
//Microsoft Yi Baiti
//for Drive park learn
//and Eras Medium ITC and Eras Light for other things

