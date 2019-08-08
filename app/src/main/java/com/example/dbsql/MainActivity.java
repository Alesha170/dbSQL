package com.example.dbsql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    HashMap<String, String> articles = new HashMap<>();

    private final SQLiteOpenHelper helper = DatabaseHelper.getInstance();
    private ArticleListAdapter adapter;
    private RecyclerView articleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        articles.put("Weather", "Is not so bad as always"); //just some text
        articles.put("Politics", "This topic is forbidden");
        articles.put("Android", "Why is just \"Q\", is there no candies with first letter q?");

        articleList = findViewById(R.id.view_article_list);
        adapter = new ArticleListAdapter(this);
        articleList.setAdapter(adapter);
        articleList.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onInsertButtonClick(View view) {
        new InsertTask().execute();
    }

    public void onDeleteButtonClick(View view) {
        new DeleteTask().execute();
    }

    private class InsertTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            for (Map.Entry<String, String> pair : articles.entrySet()) {
                String articleName = pair.getKey();
                String articleText = pair.getValue();

                ContentValues contentValues = new ContentValues();
                contentValues.put(Article.COLUMN_ARTICLE_NAME, articleName);
                contentValues.put(Article.COLUMN_ARTICLE_TEXT, articleText);

                helper.getWritableDatabase().insert(Article.TABLE_NAME, null, contentValues);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            new SelectTask().execute();
        }
    }

    private class DeleteTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            helper.getWritableDatabase().delete(Article.TABLE_NAME, null, null);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            new SelectTask().execute();
        }
    }

    private class SelectTask extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... voids) {
            Cursor cursor = helper.getWritableDatabase().query(Article.TABLE_NAME, null, null, null, null, null, null);
            cursor.moveToFirst();
            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            adapter.setCursor(cursor);
        }
    }
}
