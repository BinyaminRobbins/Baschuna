package com.syntapps.bashcuna.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.syntapps.bashcuna.R

class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var emailInput: EditText
    private lateinit var emailInputMessage: TextView
    private lateinit var passwordInput: EditText
    private lateinit var passwordInputMessage: TextView
    private lateinit var continueButton: Button
    private lateinit var googleButton: CardView
    private lateinit var facebookButton: CardView
    private lateinit var toSignupFragment: TextView

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
        emailInputMessage = view.findViewById(R.id.emailInputMessage)
        passwordInput = view.findViewById(R.id.passwordInput)
        passwordInputMessage = view.findViewById(R.id.passwordInputMessage)
        continueButton = view.findViewById(R.id.continueButton)
        googleButton = view.findViewById(R.id.googleButton)
        facebookButton = view.findViewById(R.id.facebookButton)
        toSignupFragment = view.findViewById(R.id.toSignupFragment)
        continueButton.setOnClickListener(this)
        emailInput.addTextChangedListener {
            emailInputMessage.visibility =
                if (it.toString().trim().contains('@')) View.GONE else View.VISIBLE
        }

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
        passwordInputMessage.visibility =
            if (providedPassword.length < 6) View.VISIBLE else View.GONE
        return (providedPassword.isNotBlank()
                && providedPassword.isNotEmpty())
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            continueButton.id -> {
                if (isEmailValid() && isPasswordValid()) {
                    TODO("continue button clicked - validate and move to next screen")
                }
            }
            toSignupFragment.id -> {
                TODO("navigate using the nav graph to the SignupFragment")
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