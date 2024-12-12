package app.c14210290.room.database


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface daftarBelanjaDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(daftar: daftarBelanja)

    @Query("UPDATE daftarBelanja SET tanggal =:isi_tanggal, item =:isi_item, jumlah =:isi_jumlah, status=:isi_status " +
            "WHERE id=:pilihid")
    fun update(isi_tanggal:String ,isi_item: String , isi_jumlah: String, isi_status:Int,
               pilihid: Int)

    @Query("select * from daftarBelanja Where id=:isi_id")
    suspend fun getItem(isi_id : Int) : daftarBelanja


    @Delete
    fun delete(daftar:daftarBelanja)

    @Query("SELECT * FROM daftarBelanja Order by id asc")
    fun selectALL() : MutableList<daftarBelanja>
}