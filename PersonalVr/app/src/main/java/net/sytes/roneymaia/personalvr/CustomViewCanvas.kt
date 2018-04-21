package net.sytes.roneymaia.personalvr

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import java.io.IOException
import java.io.InputStream

class CustomViewCanvas @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var canvasWidth: Int? = 0
    private var canvasHeigth: Int? = 0
    private var frameToDraw: Rect? = null
    private var whereToDraw: Rect? = null

    override fun onDraw(canvas: Canvas?) {

        val imagensgif = SingletonControlCanvas.imagensgif
        val imageNumber = SingletonControlCanvas.imageNumber
        val assetsmanager = SingletonControlCanvas.assets

        if (imagensgif != null && assetsmanager != null) {
            var istr: InputStream? = null
            var imagemDraw: Bitmap? = null

            try{
                istr = assetsmanager!!.open("imagensloginScreen/" + imagensgif[imageNumber!!]) as InputStream
                imagemDraw = BitmapFactory.decodeStream(istr)
            }catch(e: IOException){
                super.onDraw(canvas)
                return
            }
            if(imagemDraw == null){
                super.onDraw(canvas)
                return
            }

            this@CustomViewCanvas.frameToDraw = Rect(0, 0, imagemDraw!!.width, imagemDraw!!.height)
            this@CustomViewCanvas.whereToDraw = Rect(0, 0, this@CustomViewCanvas.width, this@CustomViewCanvas.height)

            canvas!!.drawBitmap(imagemDraw, this@CustomViewCanvas.frameToDraw, this@CustomViewCanvas.whereToDraw, null)


        }else{
            super.onDraw(canvas)
        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        this@CustomViewCanvas.canvasWidth = w
        this@CustomViewCanvas.canvasHeigth = h
        super.onSizeChanged(w, h, oldw, oldh)
    }

}
