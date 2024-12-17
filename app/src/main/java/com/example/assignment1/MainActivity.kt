package com.example.assignment1

import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Array<Button>>
    private var currentPlayer = "X" // X always starts
    private lateinit var board: Array<Array<String>>
    private lateinit var statusTextView: TextView
    private lateinit var resetButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusTextView = findViewById(R.id.statusTextView)
        resetButton = findViewById(R.id.resetButton)

        // Initialize the buttons and board
        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
        buttons = Array(3) { Array(3) { Button(this) } }
        board = Array(3) { Array(3) { "" } } // Empty string represents an empty cell

        for (i in 0..2) {
            val row = tableLayout.getChildAt(i) as TableRow
            for (j in 0..2) {
                val button = row.getChildAt(j) as Button
                buttons[i][j] = button
                button.setOnClickListener { onCellClick(button, i, j) }
            }
        }

        // Set reset button listener
        resetButton.setOnClickListener {
            resetGame()
        }

        statusTextView.text = "$currentPlayer's turn"
    }

    private fun onCellClick(button: Button, row: Int, col: Int) {
        if (board[row][col] == "") {
            board[row][col] = currentPlayer
            button.text = currentPlayer

            // Check for a winner after each move
            if (checkWin()) {
                statusTextView.text = "$currentPlayer wins!"
                disableButtons()
                return
            }

            // Check for a draw (if all cells are filled and no one has won)
            if (checkDraw()) {
                statusTextView.text = "It's a draw!"
                return
            }

            // Switch player
            currentPlayer = if (currentPlayer == "X") "O" else "X"
            statusTextView.text = "$currentPlayer's turn"
        }
    }

    private fun checkWin(): Boolean {
        // Check rows, columns, and diagonals for a win
        for (i in 0..2) {
            // Check rows
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) {
                return true
            }
            // Check columns
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) {
                return true
            }
        }

        // Check diagonals
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            return true
        }
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            return true
        }

        return false
    }

    private fun checkDraw(): Boolean {
        // If all cells are filled and no one has won
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == "") {
                    return false
                }
            }
        }
        return true
    }

    private fun disableButtons() {
        // Disable all buttons when the game is over
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].isEnabled = false
            }
        }
    }

    private fun resetGame() {
        currentPlayer = "X"
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].text = ""
                buttons[i][j].isEnabled = true
            }
        }
        updateStatus()
    }

    private fun updateStatus() {
        statusTextView.text = "$currentPlayer's turn"
    }

}
