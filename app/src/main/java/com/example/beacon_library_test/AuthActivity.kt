package com.example.beacon_library_test

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.beacon_library_test.core.app.App
import com.example.beacon_library_test.data.models.ApiAccess
import com.example.beacon_library_test.data.models.Resource
import com.example.beacon_library_test.data.models.Status
import com.example.beacon_library_test.databinding.AuthLayoutBinding
import com.example.beacon_library_test.presenter.viewModel.AuthViewModel
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {

    @Inject
    lateinit var authViewModel: AuthViewModel
    private lateinit var binding: AuthLayoutBinding

    private val isTokenExpiredObserver = Observer<Boolean> {
        if (it == null) return@Observer
        if (it == true) {
            setContentView(R.layout.auth_layout)
            setUI()
        } else {
            goToBLEActivity()
        }
    }

    private val authResultObserver = Observer<Resource<ApiAccess>> {
        if (it == null) return@Observer
        when (it.status) {
            Status.LOADING -> {

            }
            Status.SUCCESS -> {
                Toast.makeText(this, "Authentication is success", Toast.LENGTH_SHORT).show()
                goToBLEActivity()
            }
            Status.ERROR -> {
                Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val component by lazy { (application as App).appComponent }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        authViewModel.isTokenExpiredLiveData.observe(this, isTokenExpiredObserver)
        authViewModel.authResultLiveData.observe(this, authResultObserver)
        authViewModel.isTokenExpired()
        binding = AuthLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setUI() {
        binding.logInButton.setOnClickListener {
            val username = binding.usernameInput.text.toString()
            val password = binding.passwordInput.text.toString()
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, getString(R.string.auth_fill_both_error), Toast.LENGTH_SHORT)
                    .show()
            } else {
                authViewModel.authorize(username, password)
            }
        }
    }

    private fun goToBLEActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}