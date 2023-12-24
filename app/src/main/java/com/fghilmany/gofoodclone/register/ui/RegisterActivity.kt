package com.fghilmany.gofoodclone.register.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.fghilamny.register.presentation.RegisterViewModel
import com.fghilmany.common.DataResult
import com.fghilmany.gofoodclone.databinding.ActivityRegisterBinding
import com.fghilmany.gofoodclone.home.ui.HomeActivity
import com.fghilmany.gofoodclone.main.factories.viewmodel.RegisterViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }

    private lateinit var viewModel: RegisterViewModel

    private var password = ""
    private var passwordConfirmation = ""
    private var address = ""
    private var phoneNumber = ""
    private var city = ""
    private var name = ""
    private var houseNumber = ""
    private var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, RegisterViewModelFactory.createRegisterViewModelFactory())[RegisterViewModel::class.java]

        clickEvent()
        observeData()


    }

    private fun observeData() {
        viewModel.register.observe(this) {
            when(it){
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

    private fun clickEvent() {
        with(binding){
            with(layoutRegister1){
                btNext.setOnClickListener {
                    name = etName.text.toString()
                    email = etEmail.text.toString()
                    password = etPassword.text.toString()
                    passwordConfirmation = etPassword.text.toString()
                    binding.layoutRegister1.root.visibility = View.GONE
                    binding.layoutRegister2.root.visibility = View.VISIBLE
                }
                ivBack.setOnClickListener {
                    finish()
                }
            }
            with(layoutRegister2){
                layoutRegister2.btRegister.setOnClickListener {
                    address = etAddress.text.toString()
                    phoneNumber = etPhoneNumber.text.toString()
                    houseNumber = etHouseNumber.text.toString()
                    city = spCity.text.toString()
                    viewModel.setRegisterBody(
                        password, passwordConfirmation, address, phoneNumber, city, name, houseNumber, email
                    )
                    viewModel.register()
                }
                layoutRegister2.ivBack.setOnClickListener {
                    binding.layoutRegister1.root.visibility = View.VISIBLE
                    binding.layoutRegister2.root.visibility = View.GONE
                }
            }
        }
    }
}