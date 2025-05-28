package com.sisterag.sisconven.ui

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.sisterag.sisconven.R
import com.sisterag.sisconven.databinding.ActivityMainBinding
import com.sisterag.sisconven.ui.compra.CompraFragment
import com.sisterag.sisconven.ui.compra.ProveeFragment
import com.sisterag.sisconven.ui.compra.XpagarFragment
import com.sisterag.sisconven.ui.home.HomeFragment
import com.sisterag.sisconven.ui.inventario.ArticuloFragment
import com.sisterag.sisconven.ui.inventario.CategoriaFragment
import com.sisterag.sisconven.ui.otros.IvaFragment
import com.sisterag.sisconven.ui.otros.OtroFragment
import com.sisterag.sisconven.ui.venta.ClienteFragment
import com.sisterag.sisconven.ui.venta.PedidoFragment
import com.sisterag.sisconven.ui.venta.VentaFragment
import com.sisterag.sisconven.ui.venta.XcobrarFragment
import com.sisterag.sisconven.utiles.EndPoint.verdad

class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

    lateinit var binding: ActivityMainBinding

    lateinit var curFragment: Fragment

    lateinit var  fm: FragmentManager
    lateinit var  ft: FragmentTransaction

    lateinit var dlayout: DrawerLayout
    lateinit var nvgView: NavigationView
    lateinit var tlBar: Toolbar

    lateinit var activity:Activity
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dlayout = binding.drawerLayout
        nvgView = binding.navView
        tlBar = findViewById(R.id.toolbar)
        val homeFragment: Fragment = HomeFragment()
        curFragment = homeFragment

        fm = supportFragmentManager
        fm.beginTransaction().add(R.id.nav_host, HomeFragment()).commit()

        setSupportActionBar(tlBar)

        toggle = ActionBarDrawerToggle(this, dlayout, tlBar, R.string.drawer_open, R.string.drawer_close)
        dlayout.addDrawerListener(toggle)

        nvgView.setNavigationItemSelectedListener(this)
        val resp = verdad(false)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem):Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        selectItem(item)
        return true
    }

    private fun selectItem(item: MenuItem) {
        fm = supportFragmentManager
        ft = fm.beginTransaction()

        val homeFragment: Fragment = HomeFragment()
        val cpraFragment: Fragment = CompraFragment()
        val provFragment: Fragment = ProveeFragment()
        val xpagFragment: Fragment = XpagarFragment()
        val vtaFragment: Fragment = VentaFragment()
        val cteFragment: Fragment = ClienteFragment()
        val xcobFragment: Fragment = XcobrarFragment()
        val ivaFragment: Fragment = IvaFragment()
        val pedFragment: Fragment = PedidoFragment()
        val cateFragment: Fragment = CategoriaFragment()
        val artiFragment: Fragment = ArticuloFragment()

        when(item.itemId) {
            R.id.navig_home -> {
                curFragment = homeFragment
            }
            R.id.navig_compra -> {
                curFragment = cpraFragment
            }
            R.id.navig_provee -> {
                curFragment = provFragment
            }
            R.id.navig_ctasxpag -> {
                curFragment = xpagFragment
            }
            R.id.navig_pedido -> {
                curFragment = pedFragment
            }
            R.id.navig_venta -> {
                curFragment = vtaFragment
            }
            R.id.navig_cliente -> {
                curFragment = cteFragment
            }
            R.id.navig_ctasxcob -> {
                curFragment = xcobFragment
            }
            R.id.navig_iva -> {
                curFragment = ivaFragment
            }
            R.id.navig_categoria -> {
                curFragment = cateFragment
            }
            R.id.navig_articulo -> {
                curFragment = artiFragment
            }
            R.id.navig_salir -> {
                finish()
            }
        }
        ft.replace(R.id.nav_host, curFragment).commit()
        setTitle(item.title)
        dlayout.closeDrawers()
    }

    override fun finish() {
        val intent = Intent(Intent.ACTION_MAIN)
        //finish()
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        System.exit(0)
    }
    /*private fun setupBottomVav() {
        val homeFragment: Fragment = HomeFragment()
        val cpraFragment: Fragment = CompraFragment()
        val vtaFragment: Fragment = VentaFragment()

        curFragment = homeFragment
        nxtFragment = cpraFragment

        with(supportFragmentManager) {
            beginTransaction()
                .add(R.id.nav_host, cpraFragment)
                .hide(cpraFragment)
                .commit()
            beginTransaction()
                .add(R.id.nav_host, vtaFragment)
                .hide(vtaFragment)
                .commit()
            beginTransaction()
                .add(R.id.nav_host, homeFragment)
                .commit()
            binding.navView.setOnItemSelectedListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.navig_home -> {
                        beginTransaction()
                            .hide(curFragment)
                            .show(homeFragment).commit()
                        curFragment = homeFragment
                    }
                    R.id.navig_compra -> {
                        beginTransaction()
                            .hide(curFragment)
                            .show(cpraFragment).commit()
                        curFragment = cpraFragment
                    }
                    R.id.navig_venta -> {
                        beginTransaction()
                            .hide(curFragment)
                            .show(vtaFragment).commit()
                        curFragment = vtaFragment
                    }
                }
                true
            }
        }

    }*/
}