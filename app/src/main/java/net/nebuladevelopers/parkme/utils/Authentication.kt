package net.nebuladevelopers.parkme.utils

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import net.nebuladevelopers.parkme.Parkme
import java.lang.Exception

/**
 * Created by calebmacdonaldblack on 6/10/17.
 */

object Authentication {
    fun signUp(parkme: Parkme, email: String, password: String, success: (user: FirebaseUser) -> Unit, failure: (e: Exception) -> Unit) {
        val mAuth = FirebaseAuth.getInstance()
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful()) {
                        success(mAuth.currentUser!!)
                    } else {
                        failure(task.exception!!)
                    }
                }
    }

    fun signIn(parkme: Parkme, email: String, password: String, success: (user: FirebaseUser) -> Unit, failure: (e: Exception) -> Unit) {
        val mAuth = FirebaseAuth.getInstance()
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful()) {
                        success(mAuth.currentUser!!)
                    } else {
                        failure(task.exception!!)
                    }
                }
    }
}