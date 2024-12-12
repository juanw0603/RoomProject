package app.c14210290.room

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.c14210290.room.database.daftarBelanja
import app.c14210290.room.database.daftarBelanjaDB
import app.c14210290.room.helper.DateHelper.getCurrentDate
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class TambahDaftar : AppCompatActivity() {
    private lateinit var DB :daftarBelanjaDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_daftar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var etItem = findViewById<EditText>(R.id.etItem)
        var etJumlah = findViewById<EditText>(R.id.etJumlah)
        var btnTambah = findViewById<Button>(R.id.button_tambah)
        var btnUpdate = findViewById<Button>(R.id.button_update)
        DB = daftarBelanjaDB.getDataBase(this)
        var tanggal = getCurrentDate()
        btnTambah.setOnClickListener{
            CoroutineScope(Dispatchers.IO).async {
                DB.fundaftarBelanjaDAO().insert(
                    daftarBelanja(
                        tanggal=tanggal,
                        item = etItem.text.toString(),
                        jumlah = etJumlah.text.toString()
                    )
                )
            }
        }
    }


}