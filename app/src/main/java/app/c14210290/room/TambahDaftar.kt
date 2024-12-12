package app.c14210290.room

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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

class       TambahDaftar : AppCompatActivity() {
    private lateinit var DB :daftarBelanjaDB

    override fun onCreate(savedInstanceState: Bundle?) {
        var iId : Int = 0
        var iAddEdit : Int = 0

        iId = intent.getIntExtra("id",0)
        iAddEdit = intent.getIntExtra("addEdit",0)



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

        if(iAddEdit == 1){
            btnTambah.visibility = View.GONE
            btnUpdate.visibility = View.VISIBLE
            etItem.isEnabled = false


            CoroutineScope(Dispatchers.IO).async {
                val item = DB.fundaftarBelanjaDAO().getItem(iId)
                etItem.setHint(item.item)
                etJumlah.setHint(item.jumlah)
            }
        }else{
            btnTambah.visibility = View.GONE
            btnUpdate.visibility = View.VISIBLE
            etItem.isEnabled = true
        }


        btnTambah.setOnClickListener{

            if (etItem.text.isEmpty() || etJumlah.text.isEmpty()){
                Toast.makeText(this@TambahDaftar,"input tidak boleh kosong", Toast.LENGTH_LONG).show()

            }else{
                CoroutineScope(Dispatchers.IO).async {
                    DB.fundaftarBelanjaDAO().insert(
                        daftarBelanja(
                            tanggal=tanggal,
                            item = etItem.text.toString(),
                            jumlah = etJumlah.text.toString()
                        )
                    )
                }
                startActivity(Intent(this@TambahDaftar,MainActivity::class.java))
            }

        }


        btnUpdate.setOnClickListener{
            if (etItem.text.isEmpty() || etJumlah.text.isEmpty()){
                Toast.makeText(this@TambahDaftar,"input tidak boleh kosong", Toast.LENGTH_LONG).show()

            }else{
                CoroutineScope(Dispatchers.IO).async {
                    DB.fundaftarBelanjaDAO().update(
                       isi_tanggal = tanggal,
                        isi_item = etItem.text.toString(),
                        isi_jumlah = etJumlah.text.toString(),
                        pilihid = iId
                    )
                }
                startActivity(Intent(this@TambahDaftar,MainActivity::class.java))
            }
        }
    }


}