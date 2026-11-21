package com.wasf.mygame
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gameView = PuzzleGameView(this)
        setContentView(gameView)
    }
}

class PuzzleGameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback, Runnable {
    private var thread: Thread? = null
    private var running = false
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var currentLevel = 1
    private val puzzles = listOf("ما هو الشيء الذي له أسنان ولا يعض؟ (مشط)", "ما هو الشيء الذي يكتب ولا يقرأ؟ (قلم)", "ماهو الشيء الذي يمتلك عين واحدة ولا يرى؟ (إبرة)")

    init {
        holder.addCallback(this)
    }

    override fun run() {
        while (running) {
            val canvas = holder.lockCanvas()
            if (canvas != null) {
                drawGame(canvas)
                holder.unlockCanvasAndPost(canvas)
            }
            Thread.sleep(16)
        }
    }

    private fun drawGame(canvas: Canvas) {
        canvas.drawColor(Color.parseColor("#0F766E"))
        paint.color = Color.WHITE
        paint.textSize = 60f
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText("لعبة الألغاز", width / 2f, 150f, paint)
        
        paint.textSize = 40f
        canvas.drawText(puzzles[(currentLevel - 1) % puzzles.size], width / 2f, height / 2f, paint)
        
        paint.color = Color.parseColor("#059669")
        val rect = RectF(width / 2f - 200, height - 300f, width / 2f + 200, height - 150f)
        canvas.drawRoundRect(rect, 30f, 30f, paint)
        
        paint.color = Color.WHITE
        canvas.drawText("التالي", width / 2f, height - 220f, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (event.y > height - 300 && event.y < height - 150) {
                currentLevel++
            }
        }
        return true
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        running = true
        thread = Thread(this)
        thread?.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
    override fun surfaceDestroyed(holder: SurfaceHolder) {
        running = false
        thread?.join()
    }
}