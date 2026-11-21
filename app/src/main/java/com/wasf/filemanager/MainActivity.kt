package com.wasf.filemanager

import android.app.Activity
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*

class NoteHelper(ctx: Context) : SQLiteOpenHelper(ctx, "notes.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE notes (id INTEGER PRIMARY KEY, title TEXT, content TEXT)")
    }
    override fun onUpgrade(db: SQLiteDatabase, old: Int, new: Int) {}
}

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = ViewGroup.LayoutParams(-1, -1)
            setBackgroundColor(Color.parseColor("#F5F5F5"))
        }

        val header = TextView(this).apply {
            text = "منظم الملفات"
            textSize = 24f
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.parseColor("#B91C1C"))
            gravity = Gravity.CENTER
            setPadding(0, 50, 0, 50)
        }
        root.addView(header)

        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(30, 30, 30, 30)
        }
        root.addView(container)

        val btnAdd = Button(this).apply {
            text = "إضافة ملاحظة"
            setBackgroundColor(Color.parseColor("#EA580C"))
            setTextColor(Color.WHITE)
        }
        container.addView(btnAdd)

        val listLayout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        container.addView(listLayout)

        val db = NoteHelper(this).readableDatabase
        val cursor = db.rawQuery("SELECT * FROM notes", null)
        while(cursor.moveToNext()) {
            val note = TextView(this).apply {
                text = cursor.getString(1)
                val shape = GradientDrawable()
                shape.shape = GradientDrawable.RECTANGLE
                shape.cornerRadius = 16f
                shape.setColor(Color.WHITE)
                background = shape
                setPadding(20, 20, 20, 20)
            }
            listLayout.addView(note)
        }
        cursor.close()
        
        setContentView(root)
    }
}