package vn.edu.hust.studentman

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.content.Intent
import android.view.ContextMenu
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    private val students = mutableListOf(
        StudentModel(0, "Nguyễn Văn An", "SV001"),
        StudentModel(1, "Trần Thị Bảo", "SV002"),
        StudentModel(2, "Lê Hoàng Cường", "SV003"),
        StudentModel(3, "Phạm Thị Dung", "SV004"),
        StudentModel(4, "Đỗ Minh Đức", "SV005"),
        StudentModel(5, "Vũ Thị Hoa", "SV006"),
        StudentModel(6, "Hoàng Văn Hải", "SV007"),
        StudentModel(7, "Bùi Thị Hạnh", "SV008"),
        StudentModel(8, "Đinh Văn Hùng", "SV009"),
        StudentModel(9, "Nguyễn Thị Linh", "SV010"),
        StudentModel(10, "Phạm Văn Long", "SV011"),
        StudentModel(11, "Trần Thị Mai", "SV012"),
        StudentModel(12, "Lê Thị Ngọc", "SV013"),
        StudentModel(13, "Vũ Văn Nam", "SV014"),
        StudentModel(14, "Hoàng Thị Phương", "SV015"),
        StudentModel(15, "Đỗ Văn Quân", "SV016"),
        StudentModel(16, "Nguyễn Thị Thu", "SV017"),
        StudentModel(17, "Trần Văn Tài", "SV018"),
        StudentModel(18, "Phạm Thị Tuyết", "SV019"),
        StudentModel(19, "Lê Văn Vũ", "SV020")
    )
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentAdapter = StudentAdapter(students)
        findViewById<ListView>(R.id.studentListView).apply {
            adapter = studentAdapter
            registerForContextMenu(this)
        }

        setSupportActionBar(findViewById(R.id.optionMenu))

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val data = it.data ?: return@registerForActivityResult
                val studentName = data.getStringExtra("studentName")
                val studentId = data.getStringExtra("studentId")
                val index = data.getIntExtra("index", -1)
                if (studentName.isNullOrEmpty() || studentId.isNullOrEmpty()) {
                    Toast.makeText(this, "Invalid student data", Toast.LENGTH_SHORT).show()
                    return@registerForActivityResult
                } else {
                    if (index != -1) {
                        if (index in students.indices) {
                            students[index] = StudentModel(index, studentName, studentId)
                        } else {
                            Toast.makeText(this, "Invalid student index", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        students.add(StudentModel(students.size, studentName, studentId))
                    }
                    studentAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.addNew) {
            launcher.launch(Intent(this, AddStudentActivity::class.java))
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position
        when (item.itemId) {
            R.id.edit -> {
                Intent(this, AddStudentActivity::class.java).apply {
                    putExtra("index", students[position].index)
                    putExtra("studentName", students[position].studentName)
                    putExtra("studentId", students[position].studentId)
                    launcher.launch(this)
                }
            }

            R.id.remove -> {
                students.removeAt(position)
                studentAdapter.notifyDataSetChanged()
            }
        }
        return super.onContextItemSelected(item)
    }
}