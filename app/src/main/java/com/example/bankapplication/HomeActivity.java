package com.example.bankapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public String mName, mEmail;
    private DrawerLayout drawer;
    SessionManager sessionManager;

    Database database = new Database(this);
    Bank bank = Bank.getInstance();

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail(); // Selvitetään kirjautuneen käyttäjän etunimi ja sähköposti.
        mName = user.get(sessionManager.FIRST_NAME);
        mEmail = user.get(sessionManager.EMAIL);

        bank.clearArrayList();

        database.checkRegularAccountData(mEmail);
        database.checkCreditAccountData(mEmail);
        database.checkSavingsAccountData(mEmail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        ///
        Menu menu = navigationView.getMenu();
        MenuItem tittle1 = menu.findItem(R.id.navigation_home);
        tittle1.setTitle(getString(R.string.home));

        MenuItem tittle2 = menu.findItem(R.id.navigation_bank_actions);
        tittle2.setTitle(getString(R.string.transactions));

        MenuItem tittle3 = menu.findItem(R.id.navigation_account);
        tittle3.setTitle(getString(R.string.account));

        MenuItem tittle4 = menu.findItem(R.id.navigation_logout);
        tittle4.setTitle(getString(R.string.logOut));

        navigationView.setNavigationItemSelectedListener(this);
        ///
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            HomeFragment homeFragment = HomeFragment.newInstance(mName, mEmail); // Lähetetään HomeFragmenttiin kirjautuneen käyttäjän etunimi ja sukunimi.
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    homeFragment).commit();
            navigationView.setCheckedItem(R.id.navigation_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) { // Tässä metodissa on määritelty mitä tapahtuu kun painaa eri kohdista valikkoa.
        switch (item.getItemId()) {
            case R.id.navigation_home:
                HomeFragment homeFragment = HomeFragment.newInstance(mName, mEmail); // Lähetetään HomeFragmenttiin kirjautuneen käyttäjän etunimi ja sukunimi.
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        homeFragment).commit();
                break;
            case R.id.navigation_bank_actions:
                BankActionsFragment bankActionsFragment= BankActionsFragment.newInstance(mEmail);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        bankActionsFragment).commit();
                break;
            case R.id.navigation_account:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AccountFragment()).commit();
                break;
            case R.id.navigation_logout:
                bank.clearArrayList();
                sessionManager.logout();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



}
