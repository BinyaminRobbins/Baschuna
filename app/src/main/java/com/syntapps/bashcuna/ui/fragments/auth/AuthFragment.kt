package com.syntapps.bashcuna.ui.fragments.auth

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.syntapps.bashcuna.R

class AuthFragment : Fragment(), View.OnClickListener {

    private lateinit var emailInput: EditText
    private lateinit var emailInputMessage: TextView
    private lateinit var passwordInput: EditText
    private lateinit var passwordInputMessage: TextView
    private lateinit var continueButton: Button
    private lateinit var googleButton: CardView
    private lateinit var facebookButton: CardView
    private lateinit var toggleMode: TextView

    private val MODE_LOGIN = 0
    private val MODE_SIGNUP = 1
    private var MODE = -1

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
        toggleMode = view.findViewById(R.id.toggleMode)
        continueButton.setOnClickListener(this)
        toggleMode.setOnClickListener(this)
        emailInput.addTextChangedListener {
            emailInputMessage.visibility =
                if (it.toString().trim().contains('@') && it.toString()
                        .isNotEmpty()
                ) View.GONE else View.VISIBLE
        }
        passwordInput.addTextChangedListener {
            passwordInputMessage.visibility =
                if (passwordInput.text.toString().trim().length < 6 || it.toString().isEmpty()
                ) View.VISIBLE else View.GONE
        }
        setModeLogin()

    }

    private fun fetchEmail() = emailInput.text.toString().trim().filter { !it.isWhitespace() }

    //"abc de@gmail.com  " -> "abcde@gmail.com"
    private fun fetchPassword() = passwordInput.text.toString().trim().filter { !it.isWhitespace() }

    private fun isEmailValid(): Boolean {
        val providedEmailString = fetchEmail()
        val result = (providedEmailString.isNotBlank()
                && providedEmailString.isNotEmpty()
                && providedEmailString.contains('@')
                && providedEmailString.contains('.'))
        if (!result) fillFieldsError()
        return result
    }

    private fun isPasswordValid(): Boolean {
        val providedPassword = fetchPassword()
        val result = (providedPassword.isNotBlank()
                && providedPassword.isNotEmpty()
                && providedPassword.length >= 6)
        if (!result) fillFieldsError()
        return result
    }

    private fun fillFieldsError() =
        Toast.makeText(context, getString(R.string.error_msg_fill_fields), Toast.LENGTH_SHORT)
            .show()

    override fun onClick(v: View?) {
        when (v?.id) {
            continueButton.id -> {
                if (isEmailValid() && isPasswordValid()) {
                    when (MODE) {
                        MODE_LOGIN -> {
                            moveToHome()

                        }
                        MODE_SIGNUP -> {
                            TODO("complete the procedures for signing up")

                        }
                    }

                }
            }
            toggleMode.id -> {
                when (MODE) {
                    MODE_LOGIN -> {
                        setModeSignup()
                    }
                    MODE_SIGNUP -> {
                        setModeLogin()
                    }
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

    private fun moveToHome() {
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_auth)
            .also {
                it.navigate(R.id.homeActivity)
                activity?.finish()
                it.popBackStack()
            }
    }


    private fun setModeLogin() {
        MODE = MODE_LOGIN
        toggleMode.text = Html.fromHtml("<u>${getString(R.string.to_login)}</u>", 0)
        clearText()
    }

    private fun setModeSignup() {
        MODE = MODE_SIGNUP
        toggleMode.text = Html.fromHtml("<u>${getString(R.string.to_sign_up)}</u>", 0)
        clearText()
    }

    private fun clearText() {
        emailInput.clearComposingText()
        passwordInput.clearComposingText()
    }

}