package com.touchpad

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var webSocket: WebSocket
    private lateinit var statusText: TextView
    private lateinit var touchpad: View
    private lateinit var ipInput: EditText
    
    private var lastX = 0f
    private var lastY = 0f
    private var connected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusText = findViewById(R.id.status)
        touchpad = findViewById(R.id.touchpad)
        ipInput = findViewById(R.id.ip_input)
        
        findViewById<Button>(R.id.connect_btn).setOnClickListener {
            connect()
        }
        
        findViewById<Button>(R.id.left_click).setOnClickListener {
            sendCommand("click", mapOf("button" to "left"))
        }
        
        findViewById<Button>(R.id.right_click).setOnClickListener {
            sendCommand("click", mapOf("button" to "right"))
        }

        touchpad.setOnTouchListener { _, event ->
            handleTouch(event)
            true
        }
    }

    private fun connect() {
        val ip = ipInput.text.toString().trim()
        if (ip.isEmpty()) {
            statusText.text = "请输入服务器IP"
            return
        }

        val client = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .build()
        
        val request = Request.Builder()
            .url("ws://$ip:8765")
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                runOnUiThread {
                    connected = true
                    statusText.text = "已连接"
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                runOnUiThread {
                    connected = false
                    statusText.text = "连接失败: ${t.message}"
                }
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                runOnUiThread {
                    connected = false
                    statusText.text = "连接断开"
                }
            }
        })
    }

    private fun handleTouch(event: MotionEvent) {
        if (!connected) return

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                lastY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = (event.x - lastX) * 2  // 灵敏度调节
                val dy = (event.y - lastY) * 2
                lastX = event.x
                lastY = event.y
                sendCommand("move", mapOf("dx" to dx, "dy" to dy))
            }
        }
    }

    private fun sendCommand(action: String, params: Map<String, Any> = emptyMap()) {
        if (!connected) return
        
        val json = JSONObject().apply {
            put("action", action)
            params.forEach { (k, v) -> put(k, v) }
        }
        webSocket.send(json.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::webSocket.isInitialized) {
            webSocket.close(1000, "App closed")
        }
    }
}
