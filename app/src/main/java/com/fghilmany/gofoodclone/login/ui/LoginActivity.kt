package com.fghilmany.gofoodclone.login.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.fghilmany.common.DataResult
import com.fghilmany.gofoodclone.databinding.ActivityLoginBinding
import com.fghilmany.gofoodclone.home.ui.HomeActivity
import com.fghilmany.gofoodclone.login.presentation.LoginViewModel
import com.fghilmany.gofoodclone.register.ui.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, LoginViewModel.FACTORY)[LoginViewModel::class.java]

        clickEvent()
        observeData()

    }

    private fun clickEvent() {
        with(binding){
            btLogin.setOnClickListener {
                viewModel.setLoginBody(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString(),
                )
                viewModel.login()
            }
            btRegister.setOnClickListener {
                Intent(this@LoginActivity, RegisterActivity::class.java).apply{
                    startActivity(this)
                }
            }
        }
    }

    private fun observeData() {
        viewModel.login.observe(this) {
            when (it) {
                is DataResult.Success -> {
                    Intent(this, HomeActivity::class.java).apply {
                        startActivity(this)
                    }
                }

                is DataResult.Failure -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}