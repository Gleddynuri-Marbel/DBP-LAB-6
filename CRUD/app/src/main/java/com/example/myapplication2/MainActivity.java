package com.example.myapplication2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_codigo,et_descripcion,et_precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_codigo=(EditText)findViewById(R.id.txt_codigo);
        et_descripcion=(EditText)findViewById(R.id.txt_descripcion);
        et_precio=(EditText)findViewById(R.id.txt_precio);
    }

    public void Registrar(View view){
        AdminSQLiteOpenHelper admin=new AdminSQLiteOpenHelper(this,"administracion",null,1);

        SQLiteDatabase BaseDeDatos =admin.getWritableDatabase();

        String codigo=et_codigo.getText().toString();
        String descripcion=et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();

        if(!codigo.isEmpty()  &&!descripcion.isEmpty() && !precio.isEmpty()){
            ContentValues registro =new ContentValues();
            registro.put("codigo",codigo);
            registro.put("descripcion",descripcion);
            registro.put("precio",precio);

            BaseDeDatos.insert("articulos",null,registro);

            BaseDeDatos.close();
            et_codigo.setText("");
            et_descripcion.setText("");
            et_precio.setText("");

            Toast.makeText(this,"Registro Exitoso",Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this,"Debes llenar todos los campos",Toast.LENGTH_SHORT).show();
        }

    }

    public void Buscar(View view){
        AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this,"administracion",null,1);

        AdminSQLiteOpenHelper admi=new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase BaseDeDatabase=admi.getWritableDatabase();

        String codigo=et_codigo.getText().toString();

        if(!codigo.isEmpty()){
            Cursor fila=BaseDeDatabase.rawQuery("select descripcion,precio from articulos where codigo ="+codigo,null);
            if(fila.moveToFirst()){
                et_descripcion.setText(fila.getString(0));
                et_precio.setText(fila.getString(1));
                BaseDeDatabase.close();
            }else{
                Toast.makeText(this,"No existe el articulo",Toast.LENGTH_SHORT).show();
                BaseDeDatabase.close();
            }
        }else{
            Toast.makeText(this,"Debes introducir el articulo",Toast.LENGTH_SHORT).show();
        }

    }

    public void Eliminar(View view){
        AdminSQLiteOpenHelper admin=new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase BaseDatabase = admin.getWritableDatabase();

        String codigo=et_codigo.getText().toString();
        if(!codigo.isEmpty()){
            int cantidad=BaseDatabase.delete("articulos","codigo="+codigo,null);
            BaseDatabase.close();

            et_codigo.setText("");
            et_descripcion.setText("");
            et_precio.setText("");

            if(cantidad==1){
                Toast.makeText(this,"Articulo eliminado exitosamente",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Articulo no existe",Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this,"Debes introducir el codigo del articulo",Toast.LENGTH_LONG).show();
        }

    }

    public void Modificar(View view){
        AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase BaDatabase=admin.getWritableDatabase();

        String codigo=et_codigo.getText().toString();
        String descripcion=et_descripcion.getText().toString();
        String precio=et_precio.getText().toString();

        if(!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("codigo",codigo);
            registro.put("descripcion",descripcion);
            registro.put("precio",precio);

            int cantidad=BaDatabase.update("articulos",registro,"codigo="+codigo,null);
            BaDatabase.close();
            
            if(cantidad==1){
                Toast.makeText(this, "Articulo modificado correctamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Articulo no existe", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this,"Debes llenar los campos",Toast.LENGTH_SHORT).show();
        }

    }

}
