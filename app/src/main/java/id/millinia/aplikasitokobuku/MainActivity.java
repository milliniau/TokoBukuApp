package id.millinia.aplikasitokobuku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import id.millinia.aplikasitokobuku.DatabaseHelper;
import id.millinia.aplikasitokobuku.R;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText editJudul, editPenulis, editStok, editId;
    Button buttonAdd;
    Button buttonView;
    Button buttonUpdate;
    Button buttonDelete;

    private Bundle savedInstanceState;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this);

        editJudul = (EditText)findViewById(R.id.editText_judul);
        editPenulis = (EditText)findViewById(R.id.editText_penulis);
        editStok = (EditText)findViewById(R.id.editText_stok);
        editId = (EditText)findViewById(R.id.editText_id);
        buttonAdd = (Button) findViewById(R.id.button_add);
        buttonView = (Button) findViewById(R.id.button_view);
        buttonUpdate = (Button) findViewById(R.id.button_update);
        buttonDelete = (Button) findViewById(R.id.button_delete);

        AddData();
        ViewAll();
        UpdateData();
        DeleteData();
    }

    public void DeleteData(){
        buttonDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDB.deleteData(editId.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this, "Data Berhasil Dihapus",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Gagal Menghapus Data",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void UpdateData(){
        buttonUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDB.updateData(editId.getText().toString(), editJudul.getText().toString(),
                                editPenulis.getText().toString(),editStok.getText().toString());
                        if (isUpdate == true)
                            Toast.makeText(MainActivity.this, "Data Berhasil Diubah!",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Gagal Mengubah Data",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void AddData (){
        buttonAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDB.insertData(editJudul.getText().toString(),
                                editPenulis.getText().toString(),
                                editStok.getText().toString() );
                        if (isInserted == true)
                            Toast.makeText(MainActivity.this, "Data Berhasil Disimpan",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Gagal Menyimpan Data",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void ViewAll(){
        buttonView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDB.getAllData();
                        if(res.getCount()== 0) {
                            //show message
                            showMessage("Error", "Tidak ada data");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("Id : " +res.getString(0)+"\n");
                            buffer.append("Judul : " +res.getString(1)+"\n");
                            buffer.append("Penulis : " +res.getString(2)+"\n");
                            buffer.append("Stok : " +res.getString(3)+"\n\n");
                        }

                        //Show all data
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}

