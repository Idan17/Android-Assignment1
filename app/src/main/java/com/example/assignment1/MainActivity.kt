package com.example.assignment1

import android.os.Bundle
import android.widget.Button
//import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment1.R
import com.example.assignment1.R.*

class MainActivity : AppCompatActivity() {

    private lateinit var boardButtons: Array<Array<Button>>
    lateinit var resetButton: Button
    private lateinit var statusTextView: TextView

    private var currentPlayer = "X"
    private var board = Array(3) { arrayOfNulls<String>(3) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)


        // Initialize views
        boardButtons = arrayOf(
            arrayOf(findViewById(id.button00), findViewById(id.button01), findViewById(id.button02)),
            arrayOf(findViewById(id.button10), findViewById(id.button11), findViewById(id.button12)),
            arrayOf(findViewById(id.button20), findViewById(id.button21), findViewById(id.button22))
        )

        resetButton = findViewById(id.resetButton)
        statusTextView = findViewById(id.statusTextView)

        // Set click listeners for board buttons
        for (i in 0..2) {
            for (j in 0..2) {
                boardButtons[i][j].setOnClickListener { onBoardClick(i, j) }
            }
        }

        // Set click listener for reset button
        resetButton.setOnClickListener { resetGame() }

        updateStatus()
    }

    private fun onBoardClick(row: Int, col: Int) {
        if (board[row][col] == null) { // Check if the cell is empty
            board[row][col] = currentPlayer
            boardButtons[row][col].text = currentPlayer
            if (checkWin()) {
                statusTextView.text = "$currentPlayer wins!"
                disableBoard()
            } else if (isDraw()) {
                statusTextView.text = "It's a draw!"
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                updateStatus()
            }
        }
    }

    private fun checkWin(): Boolean {
        // Check rows and columns
        for (i in 0..2) {
            if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)
            ) {
                return true
            }
        }

        // Check diagonals
        if ((board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
            (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer)
        ) {
            return true
        }

        return false
    }

    private fun isDraw(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == null) {
                    return false
                }
            }
        }
        return true
    }

    private fun disableBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                boardButtons[i][j].isEnabled = false
            }
        }
    }

    private fun resetGame() {
        currentPlayer = "X"
        board = Array(3) { arrayOfNulls<String>(3) }
        for (i in 0..2) {
            for (j in 0..2) {
                boardButtons[i][j].text = ""
                boardButtons[i][j].isEnabled = true
            }
        }
        updateStatus()
    }

    private fun updateStatus() {
        statusTextView.text = "$currentPlayer's turn"
    }

}