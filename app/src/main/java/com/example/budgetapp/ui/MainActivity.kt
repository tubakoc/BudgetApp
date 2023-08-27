package com.example.budgetapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.budgetapp.R
import com.example.budgetapp.common.viewBinding
import com.example.budgetapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding (ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding)
        {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            NavigationUI.setupWithNavController(bottomNavigationView,navHostFragment.navController)

            navHostFragment.navController.addOnDestinationChangedListener{ _, destination, _ ->
                if (destination.id == R.id.signInFragment || destination.id == R.id.signUpFragment )
                {
                    bottomNavigationView.visibility = View.GONE
                   // floatingActionButton.visibility = View.GONE
                }
                else
                {
                    bottomNavigationView.visibility = View.VISIBLE
                   // floatingActionButton.visibility = View.VISIBLE
                }

            }
        }
    }
}