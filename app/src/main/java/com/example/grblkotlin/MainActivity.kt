package com.example.grblkotlin

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.net.Socket
import android.net.wifi.p2p.WifiP2pManager
import androidx.annotation.RequiresPermission
import java.util.UUID


class MainActivity : AppCompatActivity() {

    // Cargar la librería nativa
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    // Declarar la función nativa
    external fun stringFromJNI(): String

    // Variables para Bluetooth y USB
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var usbManager: UsbManager

    // Variables para conexión TCP
    private val puerto = 12345  // Define el puerto que usarás para la conexión TCP
    private val ipServidor = "192.168.1.100" // Define la IP del servidor al que te conectarás

    // Declara el UUID para el socket Bluetooth
    private val MY_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")  // UUID estándar para RFCOMM

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Mostrar el resultado de la función nativa
        val sampleText: TextView = findViewById(R.id.sample_text)
        sampleText.text = stringFromJNI()

        // Configurar Bluetooth
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        // Si el dispositivo no soporta Bluetooth
        if (bluetoothAdapter == null) {
            sampleText.text = "El dispositivo no soporta Bluetooth."
            return
        }

        // Habilitar Bluetooth si está desactivado
        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, 1)
        }

        // Configurar USB (OTG)
        usbManager = getSystemService(USB_SERVICE) as UsbManager
        val deviceList: Map<String, UsbDevice> = usbManager.deviceList
        if (deviceList.isEmpty()) {
            sampleText.text = "No se encontraron dispositivos USB conectados."
        } else {
            // Aquí puedes manejar los dispositivos USB conectados
            for (device in deviceList.values) {
                sampleText.text = "Dispositivo USB encontrado: ${device.deviceName}"
            }
        }

        // Configuración de conexión Bluetooth (ejemplo)
        val address = "00:11:22:33:44:55"  // Dirección MAC del dispositivo Bluetooth
        val device: BluetoothDevice = bluetoothAdapter.getRemoteDevice(address)

        val socketBluetooth = device.createRfcommSocketToServiceRecord(MY_UUID)
        socketBluetooth.connect()

        // Comunicación TCP/IP por Wi-Fi (configuración para Wi-Fi P2P o TCP)
        try {
            val socketTCP = Socket(ipServidor, puerto)  // Conexión TCP a la IP y el puerto especificado
            val outputStream = socketTCP.getOutputStream()
            val inputStream = socketTCP.getInputStream()

            // Enviar y recibir datos
            val message = "Hola servidor"
            outputStream.write(message.toByteArray())

            // Leer respuesta
            val buffer = ByteArray(1024)
            val bytesRead = inputStream.read(buffer)
            val response = String(buffer, 0, bytesRead)
            sampleText.text = "Respuesta del servidor: $response"
        } catch (e: Exception) {
            sampleText.text = "Error en la conexión TCP: ${e.message}"
        }
    }
}
