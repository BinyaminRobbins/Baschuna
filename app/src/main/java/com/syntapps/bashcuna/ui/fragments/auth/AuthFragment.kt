package com.syntapps.bashcuna.ui.fragments.auth

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.ui.viewmodels.AuthViewModel


class AuthFragment : Fragment(), View.OnClickListener {

    private val viewModel: AuthViewModel by activityViewModels()

    private lateinit var googleButton: CardView
    private lateinit var facebookButton: CardView

    private lateinit var gso: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    private var googleAuthActivityResult: ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(result.data)
                task.addOnSuccessListener {
                    if (task.result.idToken != null) {
                        viewModel.doAuthWithGoogle(task.result.idToken.toString())
                    }
                }.addOnFailureListener {
                    Toast.makeText(
                        context,
                        "something went wrong - try restarting the app",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        googleButton = view.findViewById(R.id.googleButton)
        googleButton.setOnClickListener(this)
        facebookButton = view.findViewById(R.id.facebookButton)
        facebookButton.setOnClickListener(this)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        googleSignInClient.revokeAccess()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            googleButton.id -> {
                googleAuthActivityResult.launch(googleSignInClient.signInIntent)
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

}
