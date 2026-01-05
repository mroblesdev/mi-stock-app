package com.codigosdeprogramacion.mistock

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.codigosdeprogramacion.mistock.data.Product
import com.codigosdeprogramacion.mistock.data.remote.ApiClient
import com.codigosdeprogramacion.mistock.databinding.ActivityMainBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var scanLauncher: ActivityResultLauncher<ScanOptions>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // Configura el launcher para escaneo
        scanLauncher = registerForActivityResult(ScanContract()) { result ->
            if (result.contents != null) {
                searchProduct(result.contents)
            } else {
                clearFields()
            }
        }

        binding.txtCodigo.keyListener = null
        binding.txtCodigo.isFocusable = true
        binding.txtCodigo.isFocusableInTouchMode = false
        binding.txtNombre.keyListener = null
        binding.txtNombre.isFocusable = true
        binding.txtNombre.isFocusableInTouchMode = false
        binding.txtStock.keyListener = null
        binding.txtStock.isFocusable = true
        binding.txtStock.isFocusableInTouchMode = false
        binding.txtPrecio.keyListener = null
        binding.txtPrecio.isFocusable = true
        binding.txtPrecio.isFocusableInTouchMode = false


        binding.btnScan.setOnClickListener {
            startScanner()
        }
    }

    private fun startScanner() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
        options.setPrompt("Escanea un código")
        options.setBeepEnabled(true)
        options.setCaptureActivity(CaptureActivityAnyOrientation::class.java)
        scanLauncher.launch(options)
    }

    private fun searchProduct(code: String) {
        ApiClient.getService(this)
            .getProduct(code)
            .enqueue(object : Callback<Product> {

                override fun onResponse(call: Call<Product>, response: Response<Product>) {
                    val product = response.body()
                    if (product?.exists == true) {
                        showProduct(product)
                    } else {
                        clearFields()
                        showMessage("Producto no encontrado")
                    }
                }

                override fun onFailure(call: Call<Product>, t: Throwable) {
                    showMessage("Error de conexión")
                }
            })
    }

    private fun showProduct(product: Product) {
        binding.txtCodigo.text = product.code
        binding.txtNombre.text = product.name
        binding.txtPrecio.text = product.price.toString()
        binding.txtStock.text = product.stock.toString()
    }

    private fun clearFields() {
        binding.txtCodigo.text = ""
        binding.txtNombre.text = ""
        binding.txtPrecio.text = ""
        binding.txtStock.text = ""
    }

    private fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.item_config -> {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            true
        }

        else -> super.onOptionsItemSelected(item)
    }
}