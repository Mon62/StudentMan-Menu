package vn.edu.hust.studentman

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val nameEditText: EditText = findViewById(R.id.nameEditText)
        val idEditText: EditText = findViewById(R.id.idEditText)
        val titleTextView: TextView = findViewById(R.id.titleTextView)
        val confirmButton: Button = findViewById(R.id.confirmButton)

        intent.getIntExtra("index", -1).takeIf { it != -1 }?.let {
            nameEditText.setText(intent.getStringExtra("studentName"))
            idEditText.setText(intent.getStringExtra("studentId"))
            titleTextView.text = "Edit student information"
        }

        confirmButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val id = idEditText.text.toString()
            if (name.isEmpty() || id.isEmpty()) {
                setResult(RESULT_CANCELED)
            } else {
                intent.putExtra("studentName", name)
                intent.putExtra("studentId", id)
                setResult(RESULT_OK, intent)
            }
            finish()
        }
    }
}