package com.shelazh.pertemuan6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.shelazh.pertemuan6.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var expression: Expression
    var lastNumeric = false
    var stateError = false
    var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun onOperatorClick(view: View) {
        if (!stateError && lastNumeric) {
            binding.tvData.append((view as Button).text)
            lastDot = false
            lastNumeric = false
            onEqual()
        }
    }

    fun onAllClearClick(view: View) {
        binding.tvData.text = ""
        binding.tvResult.text = ""
        stateError = false
        lastDot = false
        lastNumeric = false
        binding.tvResult.visibility = View.GONE
    }

    fun onBackClick(view: View) {
        binding.tvData.text = binding.tvData.text.toString().dropLast(1)
        try {
            val lastChar = binding.tvData.text.toString().last()
            if (lastChar.isDigit()) {
                onEqual()
            }
        } catch (e: Exception) {
            binding.tvResult.text = ""
            binding.tvResult.visibility = View.GONE
            Log.e("last char error", e.toString())
        }
    }

    fun onEqualClick(view: View) {
        onEqual()
        binding.tvData.text = binding.tvResult.text.toString().drop(1)
    }

    fun onDigitClick(view: View) {
        if (stateError) {
            binding.tvData.text = (view as Button).text
            stateError = false
        } else {
            binding.tvData.append((view as Button).text)
        }
        lastNumeric = true
        onEqual()
    }

    fun onEqual() {
        if (lastNumeric && !stateError) {
            val txt = binding.tvData.text.toString()
            try {
                expression = ExpressionBuilder(txt).build()
                val result = expression.evaluate()
                binding.tvResult.visibility = View.VISIBLE
                binding.tvResult.text = "=" + result.toString()
            } catch (ex: ArithmeticException) {
                Log.e("evaluate error", ex.toString())
                binding.tvResult.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }
}