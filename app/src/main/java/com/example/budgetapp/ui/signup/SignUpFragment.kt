package com.example.budgetapp.ui.signup


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.budgetapp.R
import com.example.budgetapp.common.viewBinding
import com.example.budgetapp.databinding.FragmentSignUpBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding (FragmentSignUpBinding::bind)

    private lateinit var auth: FirebaseAuth


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        auth.currentUser?.let {
            findNavController().navigate(R.id.signinToSummary)
        }

        with(binding)
        {
            btnSignUp.setOnClickListener {
                val name = etName.text.toString()
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                if(name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty())
                {
                    signUp(name,email,password)
                }
            }

            tvAlreadyHaveAnAccount.setOnClickListener {
                findNavController().navigate(R.id.signupToSignin)
            }
        }
    }

    private fun signUp(name: String?, email: String?, password: String?) {


        if (email != null && password != null) {
            auth.createUserWithEmailAndPassword( email, password).addOnSuccessListener {

                findNavController().navigate(R.id.sigupToSummary)
            }.addOnFailureListener {
                Snackbar.make(requireView(), it.message.orEmpty(), 1000).show()
                //show snackbar (it.message.orEmpty())
            }
        }



    }

}