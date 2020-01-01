package com.ertugrul.news.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ertugrul.news.DetailsActivity;
import com.ertugrul.news.Function;
import com.ertugrul.news.ListNewsAdapter;
import com.ertugrul.news.MainActivity;
import com.ertugrul.news.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyFragment extends Fragment {

  String API_KEY = "8617ae37f2ad47d7bdce6074b310d3be"; // ### YOUE NEWS API HERE ###
  String TECH_NEWS = "technology";
  ProgressBar loader;
  ListView listNews;


  ArrayList<HashMap<String, String>> dataList = new ArrayList<>();


  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {

    View root = inflater.inflate(R.layout.fragment_technology, container, false);

    listNews = root.findViewById(R.id.section_label);
    loader = root.findViewById(R.id.loader);
    listNews.setEmptyView(loader);


    if (Function.isNetworkAvailable(getActivity().getApplicationContext())) {
      DownloadNews newsTask = new DownloadNews();
      newsTask.execute();
    } else {
      Toast.makeText(getActivity().getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
    }
    return root;
  }

  class DownloadNews extends AsyncTask<String, Void, String> {
    @Override
    protected void onPreExecute() { super.onPreExecute(); }

    protected String doInBackground(String... args) {

      String xml = Function.excuteGet("https://newsapi.org/v2/top-headlines?country=tr&category="+TECH_NEWS+"&apiKey="+API_KEY);
      return xml;
    }

    @Override
    protected void onPostExecute(String xml) {

      if (xml.length() > 10) { // Just checking if not empty

        try {
          JSONObject jsonResponse = new JSONObject(xml);
          JSONArray jsonArray = jsonResponse.optJSONArray("articles");

          for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            HashMap<String, String> map = new HashMap<>();
            map.put(MainActivity.KEY_AUTHOR, jsonObject.optString(MainActivity.KEY_AUTHOR));
            map.put(MainActivity.KEY_TITLE, jsonObject.optString(MainActivity.KEY_TITLE));
            map.put(MainActivity.KEY_DESCRIPTION, jsonObject.optString(MainActivity.KEY_DESCRIPTION));
            map.put(MainActivity.KEY_URL, jsonObject.optString(MainActivity.KEY_URL));
            map.put(MainActivity.KEY_URLTOIMAGE, jsonObject.optString(MainActivity.KEY_URLTOIMAGE));
            map.put(MainActivity.KEY_PUBLISHEDAT, jsonObject.optString(MainActivity.KEY_PUBLISHEDAT));
            dataList.add(map);
          }
        } catch (JSONException e) {
          Toast.makeText(getActivity().getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
        }

        ListNewsAdapter adapter = new ListNewsAdapter(getActivity(), dataList);
        listNews.setAdapter(adapter);

        listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view,
                                  int position, long id) {
            Intent i = new Intent(getActivity(), DetailsActivity.class);
            i.putExtra("url", dataList.get(+position).get(MainActivity.KEY_URL));
            startActivity(i);
          }
        });

      } else {
        Toast.makeText(getActivity().getApplicationContext(), "No news found", Toast.LENGTH_SHORT).show();
      }
    }
  }

}


