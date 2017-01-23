package io.pello.android.androidsyncadapter;

/**
 * Created by PELLO_ALTADILL on 13/12/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * DBAdapter
 * Esta es una clase intermediaria entre nuestro Activity y
 * la BBDD. Aquí meteremos todas las operaciones CRUD sobre
 * los datos
 * @author Pello Altadill
 *
 */
public class DbAdapter {

    // Este objeto nos permite meterle mano a SQLite
    private SQLiteDatabase db;

    // Aquí tenemos nuestro SqliteHelper que se encarga de crear y actualizar
    private SQLiteHelper dbHelper;

    // El contexto nos servirá para referirnos a la Activity en la que estamos
    private final Context contexto;

    /**
     * DbAdapter
     * Constructor de la clase
     * @param contexto Será la activity que usa esta clase
     */
    public DbAdapter(Context contexto) {
        this.contexto = contexto;
    }


    /**
     * open
     * Usa el SqLiteHelper para encargase de abrir la conexión.
     * El SqLiteHelper lo primero que hará es crear la BD si no existía.
     * @return Devuelve un objeto de clase SQLiteDatabase para gestionar la BD
     * @throws SQLException
     */
    public SQLiteDatabase open() throws SQLException {
        // Crea un objeto asistente de base de datos de clase SqLiteHelper.
        dbHelper = new SQLiteHelper(contexto);

        // Abre la base de datos en modo escritura (lectura permitida).
        db = dbHelper.getWritableDatabase();

        Log.d("DEBUG","BD obtenida: " + db.toString());

        // Devuelve el objeto de tipo SQLiteDatabase.
        return db;
    }

    /**
     * close
     * Cierra la base de datos mediante el dbHelper
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * insertarTarea
     * Inserta un registro con los campos titulo y cuerpo en la base de datos.
     *
     * @param task
     * @return Devuelve el número de registro insertado 0 -1 en caso de error
     */
    public long insertarTarea(String task, int backendId) {
        // Creamos un registro
        ContentValues registro = new ContentValues();
        Log.d("PELLODEBUG","DbAdapter> Insert: " + task + " id: " + backendId);
        // Agrega los datos.
        registro.put("task", task);
        registro.put("id_backend", backendId);

        // Inserta el registro y devuelve el resultado.
        return db.insert("tasks", null, registro);
    }

    /**
     * borrarTarea
     * Borra el registro que tiene el id especificado.
     *
     * @param idRegistro id del registro a borrar
     * @return Devuelve el nº de registros afectados.
     */
    public int borrarTarea(long idRegistro) {
        return db.delete("tasks",  "_id = "
                + idRegistro, null);
    }

    /**
     * obtenerTareas
     * Obtiene todos los registros de la tabla todo.
     *
     * @return Cursor Devuelve un cursor con los registros obtenidos.
     */
    public Cursor obtenerTareas() {
        return db.query("tasks", new String[] {"_id","task","id_backend","is_read"}, null, null, null, null, null);
    }

    /**
     * obtenerTarea
     * Obtiene el registro que tiene el id especificado.
     *
     * @param idRegistro id del registro que se quiere obtener.
     * @return Cursor un cursor con el registro obtenido.
     * @throws SQLException
     */
    public Cursor obtenerTarea (long idRegistro) throws SQLException {
        Cursor registro = db.query(true, "tasks",new String[] { "_id","task","id_backend","is_read"},
                "_id =" + idRegistro, null, null, null, null, null);

        // Si lo ha encontrado, apunta al inicio del cursor.
        if (registro != null) {
            registro.moveToFirst();
        }
        return registro;
    }

    /**
     * obtenerUltimaTareaBackend
     * Obtiene el último registro descargado del servidor
     *
     * @return último id recibido del servidor
     * @throws SQLException
     */
    public Cursor obtenerUltimaTareaLocal() throws SQLException {
        int result = 0;
        Cursor registro = db.query(true, "tasks",new String[] { "_id","task","id_backend","is_read"},
                null, null, null, null, "_id ASC","1");
        // Si lo ha encontrado, apunta al inicio del cursor.
        if (registro != null) {
            registro.moveToFirst();
        }
        return registro;
    }

    /**
     * obtenerUltimaTareaBackend
     * Obtiene el último registro descargado del servidor
     *
     * @return último id recibido del servidor
     * @throws SQLException
     */
    public Cursor obtenerUltimaTareaBackend () throws SQLException {
        int result = 0;
        Cursor registro = db.query(true, "tasks",new String[] { "_id","task","id_backend","is_read"},
                null, null, null, null, "id_backend DESC"," 1");
        // Si lo ha encontrado, apunta al inicio del cursor.
        if (registro != null) {
            System.out.println("Register exists!");
            registro.moveToFirst();
        }
        return registro;
    }
    /**
     * actualizarTarea
     * Hace un UPDATE de los valores del registro cuyo id es idRegistro.
     *
     * @param  idRegistro id del registro que se quiere modificar.
     * @param  task
     * @return int cantidad registros han sido afectados.
     */
    public int actualizarTarea(long idRegistro, String task) {
        // Creamos un registro
        ContentValues registro = new ContentValues();

        // Agrega los datos.
        registro.put("task", task);

        // Inserta el registro y devuelve el resultado.
        return db.update("tasks", registro,
                "_id=" + idRegistro, null);
    }

}
