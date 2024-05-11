package com.example.librarymgtapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;

class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "BookLibrary.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_library";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "book_title";
    private static final String COLUMN_AUTHOR = "book_author";
    private static final String COLUMN_PAGES = "book_pages";


    private static final String TABLE_NAME_PUBLISHER = "publishers";
    private static final String COLUMN_PUBLISHER_NAME = "publisher_name";
    private static final String COLUMN_PUBLISHER_ADDRESS = "publisher_address";
    private static final String COLUMN_PUBLISHER_PHONE = "publisher_phone";


    private static final String TABLE_MEMBERS = "members";
    private static final String COLUMN_MEMBER_CARD_NUMBER = "CardNumber";
    private static final String COLUMN_MEMBER_NAME = "Name";
    private static final String COLUMN_MEMBER_ADDRESS = "Address";
    private static final String COLUMN_MEMBER_PHONE = "Phone";
    private static final String COLUMN_MEMBER_DUES = "Dues";

    public static final String TABLE_NAME_BRANCH = "branches";
    public static final String COL_1 = "BranchID";
    public static final String COL_2 = "BranchName";
    public static final String COL_3 = "BranchAddress";

    private static final String TABLE_BOOK_COPY = "bookcopy";
    private static final String COLUMN_BOOK_ID = "book_id";
    private static final String COLUMN_BRANCH_ID = "branch_id";
    private static final String COLUMN_ACCESS_NO = "access_no";

    / Table name
    private static final String TABLE_AUTHORS = "authors";

    // Column names


    private static final String COLUMN_AUTHOR_NAME = "author_name";


    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_AUTHOR + " TEXT, " +
                COLUMN_PAGES + " INTEGER);";
        db.execSQL(query);

        String createTableStatement = "CREATE TABLE " + TABLE_NAME_PUBLISHER + " (" +
                COLUMN_PUBLISHER_NAME + " TEXT PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PUBLISHER_ADDRESS + " TEXT, " +
                COLUMN_PUBLISHER_PHONE + " TEXT)";
        db.execSQL(createTableStatement);



        // SQL statement to create members table
        String CREATE_TABLE_MEMBERS = "CREATE TABLE " + TABLE_MEMBERS + "("
                + COLUMN_MEMBER_CARD_NUMBER + " INTEGER PRIMARY KEY,"
                + COLUMN_MEMBER_NAME + " TEXT,"
                + COLUMN_MEMBER_ADDRESS + " TEXT,"
                + COLUMN_MEMBER_PHONE + " TEXT,"
                + COLUMN_MEMBER_DUES + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE_MEMBERS);
        onCreate(db);

        String createMembersTable = "CREATE TABLE members (" +
                "CardNumber TEXT PRIMARY KEY," +
                "Name TEXT," +
                "Address TEXT," +
                "Phone TEXT," +
                "Dues TEXT)";
        db.execSQL(createMembersTable);

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT, " +
                COL_3 + " TEXT)");

        String createBookCopyTable = "CREATE TABLE " + TABLE_BOOK_COPY + " ("

                + COLUMN_BOOK_ID + " TEXT, "
                + COLUMN_BRANCH_ID + " TEXT PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ACCESS_NO + " TEXT PRIMARY KEY AUTOINCREMENT)";

        db.execSQL(createBookCopyTable);

        String createAuthorsTable = "CREATE TABLE " + TABLE_AUTHORS + "("

                + COLUMN_BOOK_ID + " TEXT PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_AUTHOR_NAME + " TEXT PRIMARY KEY AUTOINCREMENT"
                + ")";
        db.execSQL(createAuthorsTable);


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PUBLISHER);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS members");
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS members");
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK_COPY);
        onCreate(db);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTHORS);
        onCreate(db);
    }

    void addBook(String title, String author, int pages){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_PAGES, pages);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String title, String author, String pages){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_PAGES, pages);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public boolean insertPublisher(String name, String address, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PUBLISHER_NAME, name);
        contentValues.put(COLUMN_PUBLISHER_ADDRESS, address);
        contentValues.put(COLUMN_PUBLISHER_PHONE, phone);

        long result = db.insert(TABLE_NAME_PUBLISHER, null, contentValues);
        return result != -1;
    }

    public boolean updatePublisher(String name, String address, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PUBLISHER_NAME, name);
        contentValues.put(COLUMN_PUBLISHER_ADDRESS, address);
        contentValues.put(COLUMN_PUBLISHER_PHONE, phone);

        long result = db.update(TABLE_NAME_PUBLISHER, contentValues, COLUMN_PUBLISHER_NAME + "=?", new String[]{name});
        return result != -1;
    }

    public boolean deletePublisher(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME_PUBLISHER, COLUMN_PUBLISHER_NAME + "=?", new String[]{name});
        return result != -1;
    }

    public Cursor getAllPublishers() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME_PUBLISHER, null);
    }

    public boolean insertMember(String cardNumber, String name, String address, String phone, String dues) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MEMBER_CARD_NUMBER, cardNumber);
        contentValues.put(COLUMN_MEMBER_NAME, name);
        contentValues.put(COLUMN_MEMBER_ADDRESS, address);
        contentValues.put(COLUMN_MEMBER_PHONE, phone);
        contentValues.put(COLUMN_MEMBER_DUES, dues);
        long result = db.insert(TABLE_MEMBERS, null, contentValues);
        // if data is inserted incorrectly it will return -1
        return result != -1;
    }

    public int updateMember(String cardNumber, String name, String address, String phone, String dues) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Address", address);
        values.put("Phone", phone);
        values.put("Dues", dues);
        return db.update("members", values, "CardNumber=?", new String[]{cardNumber});
    }

    public int deleteMember(String cardNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("members", "CardNumber=?", new String[]{cardNumber});
    }

    public boolean insertBranch(String branchId, String branchName, String branchAddress) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("branch_id", branchId);
        contentValues.put("branch_name", branchName);
        contentValues.put("branch_address", branchAddress);

        long result = db.insert("branches", null, contentValues);
        return result != -1; // Returns true if insertion is successful, false otherwise
    }
    public boolean updateBranch(String branchId, String newBranchName, String newBranchAddress) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("branch_name", newBranchName);
        contentValues.put("branch_address", newBranchAddress);

        int affectedRows = db.update("branches", contentValues, "branch_id = ?", new String[]{branchId});
        return affectedRows > 0; // Returns true if at least one row was affected, indicating successful update
    }

    public boolean deleteBranch(String branchId) {
        SQLiteDatabase db = this.getWritableDatabase();

        int affectedRows = db.delete("branches", "branch_id = ?", new String[]{branchId});
        return affectedRows > 0; // Returns true if at least one row was affected, indicating successful deletion
    }

    public boolean insertBookCopy(String bookId, String branchId, String accessNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BOOK_ID, bookId);
        contentValues.put(COLUMN_BRANCH_ID, branchId);
        contentValues.put(COLUMN_ACCESS_NO, accessNo);

        long result = db.insert(TABLE_BOOK_COPY, null, contentValues);
        return result != -1;
    }

    // Update an existing book copy
    public boolean updateBookCopy(String bookId, String branchId, String accessNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BOOK_ID, bookId);
        contentValues.put(COLUMN_BRANCH_ID, branchId);
        contentValues.put(COLUMN_ACCESS_NO, accessNo);

        int result = db.update(TABLE_BOOK_COPY, contentValues, COLUMN_ID + "=?", new String[]{accessNo});
        return result > 0;
    }

    // Delete a book copy
    public boolean deleteBookCopy(String bookId, String branchId, String accessNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_BOOK_COPY, COLUMN_ID + "=?", new String[]{accessNo});
        return result > 0;
    }

    public boolean insertAuthor(String bookId, String authorName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_ID, bookId);
        values.put(COLUMN_AUTHOR_NAME, authorName);

        // Insert the new row, returning the primary key value of the new row
        long result = db.insert(TABLE_AUTHORS, null, values);
        db.close();
        return result != -1; // If result == -1, insertion failed
    }

    public boolean updateAuthor(String bookId, String authorName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_ID, bookId);
        values.put(COLUMN_AUTHOR_NAME, authorName);

        // Update the row with the specified id
       long result = db.update(TABLE_AUTHORS, values, COLUMN_AUTHOR_NAME + " = ?", new String[]{authorName});

       return result != -1;
    }

    public boolean deleteAuthor(String authorName) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete the row with the specified id
       long result = db.delete(TABLE_AUTHORS, COLUMN_AUTHOR_NAME + " = ?", new String[]{authorName});

        return result != -1;
    }


}