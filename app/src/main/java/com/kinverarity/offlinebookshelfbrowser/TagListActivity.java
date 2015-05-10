package com.kinverarity.offlinebookshelfbrowser;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kinverarity.offlinebookshelfbrowser.R;

public class TagListActivity extends Activity {
    String TAG = "TagListActivity";
    SharedPreferences sharedPref;
    LogHandler logger;
    
    ArrayList<String> tags;
    ListView listView;
    
    SearchHandler searchHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String METHOD = ".onCreate()";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_list);
        
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        logger = new LogHandler(sharedPref);
        logger.log(TAG + METHOD, "Start");

        listView = (ListView) findViewById(R.id.tagListView);
        
        Intent intent = getIntent();
        searchHandler = new SearchHandler(this);
        searchHandler.setIds(intent.getStringExtra("ids"));
        tags = searchHandler.getCommaSeparatedItemsFromColumn("tags");
        Collections.sort(tags, String.CASE_INSENSITIVE_ORDER);
        String tagsString = "";
        for (String tag : tags) {
            tagsString += ',' + tag;
            
        }
        logger.log(TAG + METHOD, "tags=" + tagsString);
        SectionIndexingArrayAdapter<String> adapter = new SectionIndexingArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tags);

        listView.setAdapter(adapter);
        setTitle(getString(R.string.title_activity_tag_list) + " (" + tags.size() + "):");
        listView.setFastScrollEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                String tag = tags.get(position);
                Intent intent = new Intent(parent.getContext(), BookListActivity.class);
                intent.putExtra("tagName", tag);
                
                // The following MUST be made subject to a preference for exclusive/inclusive/ask tag/collection handling
                intent.putExtra("ids", getIds());
                
                startActivity(intent);
            }
        });

    }
    
    protected String getIds() {
        return searchHandler.getString();
    }
}
