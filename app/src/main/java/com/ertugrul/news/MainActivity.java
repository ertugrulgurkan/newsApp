package com.ertugrul.news;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public class MainActivity extends AppCompatActivity {
  public static final String KEY_AUTHOR = "author";
  public static final String KEY_TITLE = "title";
  public static final String KEY_DESCRIPTION = "description";
  public static final String KEY_URL = "url";
  public static final String KEY_URLTOIMAGE = "urlToImage";
  public static final String KEY_PUBLISHEDAT = "publishedAt";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    BottomNavigationView navView = findViewById(R.id.nav_view);
    AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
      R.id.navigation_science, R.id.navigation_sports, R.id.navigation_technology)
      .build();
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    NavigationUI.setupWithNavController(navView, navController);
  }

}
