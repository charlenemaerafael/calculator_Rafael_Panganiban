package com.example.calculator_panganiban_rafael

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var resultTextView: EditText

    private var operator: String? = null
    private var firstValue: Double = 0.0
    private var isOperatorClicked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize EditTexts
        editText = findViewById(R.id.editText)
        resultTextView = findViewById(R.id.resultTextView)

        // Initialize Buttons
        val button0: Button = findViewById(R.id.button0)
        val button1: Button = findViewById(R.id.button1)
        val button2: Button = findViewById(R.id.button2)
        val button3: Button = findViewById(R.id.button3)
        val button4: Button = findViewById(R.id.button4)
        val button5: Button = findViewById(R.id.button5)
        val button6: Button = findViewById(R.id.button6)
        val button7: Button = findViewById(R.id.button7)
        val button8: Button = findViewById(R.id.button8)
        val button9: Button = findViewById(R.id.button9)
        val buttonDot: Button = findViewById(R.id.buttonDot)
        val buttonAdd: Button = findViewById(R.id.buttonAdd)
        val buttonSubtract: Button = findViewById(R.id.buttonSubtract)
        val buttonMultiply: Button = findViewById(R.id.buttonMultiply)
        val buttonDivide: Button = findViewById(R.id.buttonDivide)
        val buttonEquals: Button = findViewById(R.id.buttonEquals)
        val buttonAC: Button = findViewById(R.id.AC)
        val backspaceButton: Button = findViewById(R.id.backspace)

        // Set button texts from resources
        button0.text = getString(R.string.button0)
        button1.text = getString(R.string.button1)
        button2.text = getString(R.string.button2)
        button3.text = getString(R.string.button3)
        button4.text = getString(R.string.button4)
        button5.text = getString(R.string.button5)
        button6.text = getString(R.string.button6)
        button7.text = getString(R.string.button7)
        button8.text = getString(R.string.button8)
        button9.text = getString(R.string.button9)
        buttonDot.text = getString(R.string.buttonDot)
        buttonAdd.text = getString(R.string.buttonAdd)
        buttonSubtract.text = getString(R.string.buttonSubtract)
        buttonMultiply.text = getString(R.string.buttonMultiply)
        buttonDivide.text = getString(R.string.buttonDivide)
        buttonEquals.text = getString(R.string.buttonEquals)
        buttonAC.text = getString(R.string.buttonAC)
        backspaceButton.text = getString(R.string.buttonBackspace)

        // Set hint texts from resources
        editText.hint = getString(R.string.hint_edit_text)
        resultTextView.hint = getString(R.string.hint_result)

        // Set up button click listeners
        val numberButtons = listOf(button0, button1, button2, button3, button4, button5, button6, button7, button8, button9)
        numberButtons.forEach { button ->
            button.setOnClickListener {
                onNumberClick(button.text.toString())
            }
        }

        buttonDot.setOnClickListener {
            onDotClick()
        }

        // Operator buttons
        buttonAdd.setOnClickListener { onOperatorClick("+") }
        buttonSubtract.setOnClickListener { onOperatorClick("-") }
        buttonMultiply.setOnClickListener { onOperatorClick("*") }
        buttonDivide.setOnClickListener { onOperatorClick("/") }

        buttonEquals.setOnClickListener {
            onEqualsClick()
        }

        buttonAC.setOnClickListener {
            onACClick()
        }

        backspaceButton.setOnClickListener {
            onBackspaceClick()
        }
    }

    private fun onNumberClick(number: String) {
        if (isOperatorClicked) {
            editText.text.clear()
            isOperatorClicked = false
        }
        editText.append(number)
    }

    private fun onDotClick() {
        if (isOperatorClicked) {
            editText.text.clear()
            isOperatorClicked = false
        }
        if (!editText.text.contains(".")) {
            editText.append(".")
        }
    }

    private fun onOperatorClick(op: String) {
        val currentText = editText.text.toString()
        if (currentText.isNotEmpty()) {
            firstValue = currentText.toDoubleOrNull() ?: 0.0
            operator = op
            isOperatorClicked = true
        }
    }

    private fun onEqualsClick() {
        val secondValue = editText.text.toString().toDoubleOrNull()
        if (secondValue == null) {
            resultTextView.setText("Error")
            return
        }

        val result = when (operator) {
            "+" -> firstValue + secondValue
            "-" -> firstValue - secondValue
            "*" -> firstValue * secondValue
            "/" -> if (secondValue != 0.0) firstValue / secondValue else {
                resultTextView.setText("Error")
                return
            }
            else -> {
                resultTextView.setText("Error")
                return
            }
        }

        // Format result to show integer if possible
        val formattedResult = if (result % 1.0 == 0.0) {
            // If result is an integer, format without decimal places
            result.toInt().toString()
        } else {
            // Otherwise, format with up to 2 decimal places
            val decimalFormat = DecimalFormat("#.##")
            decimalFormat.format(result)
        }

        // Display the result in resultTextView
        resultTextView.setText(formattedResult)
        editText.text.clear()
        isOperatorClicked = true
    }


    private fun onACClick() {
        editText.text.clear()
        resultTextView.text.clear()
        firstValue = 0.0
        operator = null
        isOperatorClicked = false
    }

    private fun onBackspaceClick() {
        val currentText = editText.text
        if (currentText.isNotEmpty()) {
            editText.text.delete(currentText.length - 1, currentText.length)
        }
    }
}