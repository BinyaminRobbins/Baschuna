package com.syntapps.bashcuna.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.syntapps.bashcuna.R

class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var continueButton: Button
    private lateinit var googleButton: CardView
    private lateinit var facebookButton: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailInput = view.findViewById(R.id.emailInput)
        passwordInput = view.findViewById(R.id.passwordInput)
        continueButton = view.findViewById(R.id.continueButton)
        googleButton = view.findViewById(R.id.googleButton)
        facebookButton = view.findViewById(R.id.facebookButton)


    }

    private fun fetchEmail() = emailInput.text.toString().trim().filter { !it.isWhitespace() }
    //"abc de@gmail.com  " -> "abcde@gmail.com"

    private fun isEmailValid(): Boolean {
        val providedEmailString = fetchEmail()
        return (providedEmailString.isNotBlank()
                && providedEmailString.isNotEmpty()
                && providedEmailString.contains('@')
                && providedEmailString.contains('.'))
    }

    private fun fetchPassword() = passwordInput.text.toString().trim().filter { !it.isWhitespace() }

    private fun isPasswordValid(): Boolean {
        val providedPassword = fetchPassword()
        return (providedPassword.isNotBlank()
                && providedPassword.isNotEmpty()
                && providedPassword.length < 5)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            continueButton.id -> {
                if(isEmailValid() && isPasswordValid()) {
                    TODO("continue button clicked - validate and move to next screen")
                }
            }
            googleButton.id -> {
                TODO("setup google login")
            }
            facebookButton.id -> {
                TODO("setup facebook login")
            }
        }
    }

}