package com.example.bizzfloww;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private MaterialToolbar toolbar;
    private BottomNavigationView bottomNavigation;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupToolbarAndDrawer();
        setupBottomNavigation();
        setupBackPressedDispatcher();

        // Load default DashboardFragment on launch
        if (savedInstanceState == null) {
            navigateToFragment(new DashboardFragment(), R.id.nav_dashboard);
        }
    }

    private void initViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        navigationView = findViewById(R.id.nav_view);
    }

    private void setupToolbarAndDrawer() {
        // Task 1: Toolbar Setup & Menu Listeners
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("BizFlow ERP");
        }

        // Task 8: Navigation Drawer & Toggle Setup
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.back, R.string.back
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupBottomNavigation() {
        // Task 7: Bottom Navigation Listener
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_dashboard) {
                replaceFragment(new DashboardFragment());
                updateDrawerCheckedItem(R.id.drawer_dashboard);
                return true;
            } else if (itemId == R.id.nav_inventory) {
                replaceFragment(new InventoryFragment());
                updateDrawerCheckedItem(R.id.drawer_inventory);
                return true;
            } else if (itemId == R.id.nav_production) {
                replaceFragment(new ProductionFragment());
                updateDrawerCheckedItem(R.id.drawer_production);
                return true;
            } else if (itemId == R.id.nav_sales) {
                replaceFragment(new SalesFragment());
                updateDrawerCheckedItem(R.id.drawer_sales);
                return true;
            }
            return false;
        });
    }

    private void setupBackPressedDispatcher() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });
    }

    // Task 1: Options Menu Creation
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_options_menu, menu);
        return true;
    }

    // Task 1: Options Menu Click Listeners
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_home) {
            navigateToFragment(new DashboardFragment(), R.id.nav_dashboard);
            return true;
        } else if (id == R.id.action_inventory) {
            navigateToFragment(new InventoryFragment(), R.id.nav_inventory);
            return true;
        } else if (id == R.id.action_production) {
            navigateToFragment(new ProductionFragment(), R.id.nav_production);
            return true;
        } else if (id == R.id.action_sales) {
            navigateToFragment(new SalesFragment(), R.id.nav_sales);
            return true;
        } else if (id == R.id.action_settings) {
            replaceFragment(new SettingsFragment());
            return true;
        } else if (id == R.id.action_logout) {
            performLogout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Task 8: Navigation Drawer Menu Item Selection
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.drawer_dashboard) {
            bottomNavigation.setSelectedItemId(R.id.nav_dashboard);
        } else if (id == R.id.drawer_inventory) {
            bottomNavigation.setSelectedItemId(R.id.nav_inventory);
        } else if (id == R.id.drawer_purchase) {
            replaceFragment(new PurchaseFragment());
        } else if (id == R.id.drawer_production) {
            bottomNavigation.setSelectedItemId(R.id.nav_production);
        } else if (id == R.id.drawer_sales) {
            bottomNavigation.setSelectedItemId(R.id.nav_sales);
        } else if (id == R.id.drawer_warehouse) {
            replaceFragment(new WarehouseFragment());
        } else if (id == R.id.drawer_reports) {
            replaceFragment(new ReportsFragment());
        } else if (id == R.id.drawer_settings) {
            replaceFragment(new SettingsFragment());
        } else if (id == R.id.drawer_logout) {
            performLogout();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void navigateToFragment(Fragment fragment, int bottomNavItemId) {
        bottomNavigation.setSelectedItemId(bottomNavItemId);
        replaceFragment(fragment);
    }

    // Task 2 & Task 6: Fragment Transaction with Slide/Fade Animations
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.fade_in);
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    private void updateDrawerCheckedItem(int drawerItemId) {
        MenuItem item = navigationView.getMenu().findItem(drawerItemId);
        if (item != null) {
            item.setChecked(true);
        }
    }

    private void performLogout() {
        new AlertDialog.Builder(this)
                .setTitle("BizFlow Logout")
                .setMessage("Are you sure you want to log out of BizFlow ERP?")
                .setIcon(R.drawable.ic_logout)
                .setPositiveButton("Logout", (dialog, which) -> {
                    Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
