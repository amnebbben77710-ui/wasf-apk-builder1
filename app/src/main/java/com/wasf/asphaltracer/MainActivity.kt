package com.wasf.asphaltracer
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.view.*
import android.widget.*
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = FrameLayout(this)
        val glView = GLSurfaceView(this)
        glView.setEGLContextClientVersion(2)
        glView.setRenderer(GameRenderer())
        root.addView(glView)
        val uiOverlay = RelativeLayout(this)
        val scoreText = TextView(this).apply { text = "Score: 0"; setTextColor(Color.WHITE); textSize = 24f }
        uiOverlay.addView(scoreText)
        root.addView(uiOverlay)
        setContentView(root)
    }
}

class GameRenderer : GLSurfaceView.Renderer {
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        gl?.glClearColor(0.2f, 0.2f, 0.2f, 1.0f)
    }
    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        gl?.glViewport(0, 0, width, height)
    }
    override fun onDrawFrame(gl: GL10?) {
        gl?.glClear(GL10.GL_COLOR_BUFFER_BIT)
    }
}