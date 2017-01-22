package io.pello.android.androidsyncadapter;

/**
 * Created by PELLO_ALTADILL on 13/12/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * SqLiteHelper
 * La clase se encarga de inicializar la BBDD -si no existe-, de actualizarla
 *
 * @author pello xabier altadill
 *
 */
class SQLiteHelper extends SQLiteOpenHelper {

    public static final String NOMBRE_BD = "tasklist.db";
    public static final int VERSION_BD = 1;
    public static final String SQLCREAR = "create table tasks "+
            " (_id integer primary key autoincrement, " +
            " task text not null," +
            " id_backend integer not null default 0," +
            " is_read integer not null default 0);" +
            " insert into tasks (task) values('EAT');";


    /**
     * Constructor
     * llama al constructor padre
     * Le pasa el contexto, que se refiere al activity actual
     * @param contexto
     */
    SQLiteHelper(Context contexto) {
        super(contexto, NOMBRE_BD, null, VERSION_BD);
    }

    /**
     * onCreate
     * Se ejecuta en caso de que la BD no exista
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Se ejecuta la sentencia de creación de la tabla notas.
        db.execSQL("DROP TABLE IF EXISTS tasks");
        db.execSQL(SQLCREAR);
        Log.d("DEBUG","Ok, BD creada");
    }

    /**
     * onUpgrade
     * Se ejecuta de forma automáticamente en caso de que
     * estemos en una nueva versión.
     *
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Sacamos un log para contar lo que ha pasado
        Log.w("SqLiteHelper", "Actualizando desde versión " + oldVersion
                + " a " + newVersion + ", los datos será eliminados");

        // En este caso en el upgrade realmente
        // lo que hacemos es cargarnos lo que hay...
        db.execSQL("DROP TABLE IF EXISTS tasks");

        // ... y lo volvemos a generar
        onCreate(db);
        Log.d("DEBUG","Ok, BD regenerada");
    }

}
