package com.example.joinbtnenabled

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        focusChangeTextView()
        //test 주석
    }

    override fun onResume() {
        super.onResume()


        join_btn.setOnClickListener {
            Prefs.cnt++
            if(Prefs.cnt == 0) {
                click(emailLayout)
            }else if(Prefs.cnt == 1) {
                click(nameLayout)
            }

            if(phone_number_text.text.toString() != ""&& name_edit.text.toString()!="" && email_edit.text.toString() != "") {
                activation()
                var intent = Intent(this, TestActivity::class.java)
                startActivity(intent)
            }
        }
    }
    private fun click(view: ConstraintLayout) {
        view.visibility = View.VISIBLE
        if(view == emailLayout) {
            disabled()
            openKeyboard()
            email_edit.requestFocus()
            email_edit.addTextChangedListener(object : TextWatcher{
                // 입력끝났을경우
                override fun afterTextChanged(s: Editable?) {
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                        errorEmail.visibility = View.VISIBLE
                        disabled()
                    }else {
                        errorEmail.visibility = View.GONE
                        activation()
                    }
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                        errorEmail.visibility = View.VISIBLE
                    }else {
                        errorEmail.visibility = View.GONE
                    }
                }
            })
        }else if(view == nameLayout) {
            disabled()
            openKeyboard()
            email_edit.clearFocus()
            name_edit.requestFocus()
            name_edit.addTextChangedListener(object : TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    nameCheck()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    nameCheck()
                }
            })

        }
    }
    private fun nameCheck() {
        if(name_edit.text.toString() != "") {
            activation()
            join_btn.text = "완료"
        }else {
            disabled()
            join_btn.text = "완료"
        }
    }
    private fun activation() {
        join_btn.isEnabled = true
        join_btn.setBackgroundColor(Color.BLUE)
    }
    private fun disabled() {
        join_btn.isEnabled = false
        join_btn.setBackgroundColor(Color.GRAY)
    }
    private fun focusChangeTextView() {

        name_edit.onFocusChangeListener = View.OnFocusChangeListener { p0, p1 ->
            if(p1) {
                inputTextView.text = "이름 입력"
            }
        }
        email_edit.onFocusChangeListener = View.OnFocusChangeListener { p0, p1 ->
            if(p1) {
                inputTextView.text = "이메일 입력"
            }
        }
    }
    fun openKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }
    override fun onDestroy() {
        super.onDestroy()
        Prefs.cnt = -1
        join_btn.isEnabled = true
    }
}
