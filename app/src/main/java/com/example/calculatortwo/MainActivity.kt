package com.example.calculatortwo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.calculatortwo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    var lastNumeric = false
    var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onDigit(view: View) {
        binding.resultTv.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }
    fun onClear(view: View)
    {
        binding.resultTv.text = ""
    }

    fun onDecimalPoint(view: View) {
        if(lastNumeric && !lastDot) {
            binding.resultTv.append(".")
            lastDot = true
            lastNumeric = false
        }
    }
    fun onOperator(view: View)
    {
        binding.resultTv.let {
            if(lastNumeric && !isOperatorAdded(it.toString())) {
                binding.resultTv.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }

    }
    fun onEqual(view: View) {
        if(lastNumeric) {
            var tvValue = binding.resultTv.text.toString()
            var prefix = ""

            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one += prefix
                    }

                    binding.resultTv.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                } else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one += prefix
                    }

                    binding.resultTv?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())

                } else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one += prefix
                    }

                    binding.resultTv.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())

                } else if (tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one += prefix
                    }

                    binding.resultTv.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }



            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String) : String {
        var value = result
        if(result.contains(".0"))
            value = result.substring(0, result.length - 2)

        return value
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if(value.startsWith("-")) {
             false
        } else {
            value.contains("/")
                    || value.contains("+")
                    || value.contains("*")
                    || value.contains("-")

        }

    }



}
