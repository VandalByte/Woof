package com.dev.woof;




import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;
public class PetsDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "MyPets.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "my_pets";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "pet_name";
    private static final String COLUMN_GENDER = "pet_gender";
    private static final String COLUMN_AGE = "pet_age";

    private static final String COLUMN_WEIGHT = "pet_weight";
    private static final String COLUMN_HEIGHT = "pet_height";
    private static final String COLUMN_BREED = "pet_breed";
    private static final String COLUMN_COLOR = "pet_color";

    public PetsDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_GENDER + " TEXT, " +
                COLUMN_AGE + " INTEGER, " +
                COLUMN_WEIGHT + " INTEGER, " +
                COLUMN_HEIGHT + " INTEGER, " +  // Add pet_height column
                COLUMN_BREED + " TEXT, " +
                COLUMN_COLOR + " TEXT)";
        db.execSQL(query);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addPet(String name, String gender, int age, String breed, int weight, int height, String color) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_GENDER, gender);
        cv.put(COLUMN_AGE, age);

        cv.put(COLUMN_WEIGHT, weight);
        cv.put(COLUMN_HEIGHT, height);
        cv.put(COLUMN_BREED, breed);
        cv.put(COLUMN_COLOR, color);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed to add pet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Pet added successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }
}
