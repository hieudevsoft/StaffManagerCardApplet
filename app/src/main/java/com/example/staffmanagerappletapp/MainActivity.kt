package com.example.staffmanagerappletapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.staffmanagerappletapp.databinding.ActivityMainBinding
import io.github.muddz.styleabletoast.StyleableToast

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var handler: Handler
    private val codeCard = "909080807070"
    private val codePin = "123456"
    private val sharedPreferenceHelper:SharedPreferenceHelper by lazy { SharedPreferenceHelper(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handler = Handler(mainLooper)
        setupInit()
        setupAnimation()
        handler.postDelayed({
            binding.apply {
                containerIntroduce.startAnimation(AnimationUtils.loadAnimation(this@MainActivity,R.anim.top_up_300))
                containerIntroduce.toGone()
                handler.postDelayed({
                    containerMain.toVisible()
                    setupControl()
                },800)
            }
        },2500)

        binding.apply {
            cardChangePin.setOnClickListener {
                Intent(this@MainActivity,ChangePinActivity::class.java).also {
                    startActivity(it)
                    overridePendingTransition(R.anim.slide_in_right_200,R.anim.slide_out_right_300)
                }
            }

            cardRecharge.setOnClickListener {
                Intent(this@MainActivity,RechargeActivity::class.java).also {
                    startActivity(it)
                    overridePendingTransition(R.anim.slide_in_right_200,R.anim.slide_out_right_300)
                }
            }

            cardProfile.setOnClickListener {
                Intent(this@MainActivity,ProfileActivity::class.java).also {
                    startActivity(it)
                    overridePendingTransition(R.anim.slide_in_right_200,R.anim.slide_out_right_300)
                }
            }

            if(sharedPreferenceHelper.getStatusLockCard()){
                cardUnlockCard.isEnabled = true
                cardTemporatilyCard.setCardBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.colorRed))
                tvLockCard.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.colorRed))
            } else{
                cardUnlockCard.isEnabled = false
            }

            cardTemporatilyCard.setOnClickListener {
                if(sharedPreferenceHelper.getStatusLockCard()) return@setOnClickListener
                cardUnlockCard.isEnabled = true
                showToastSuccess("Khóa thẻ thành công")
                sharedPreferenceHelper.setStatusLockCard(true)
                cardTemporatilyCard.setCardBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.colorRed))
                tvLockCard.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.white))
            }

            cardUnlockCard.setOnClickListener {
                if(!sharedPreferenceHelper.getStatusLockCard()) return@setOnClickListener
                sharedPreferenceHelper.setStatusLockCard(false)
                showToastSuccess("Mở khóa thẻ thành công")
                cardTemporatilyCard.setCardBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.colorBackground_Day))
                tvLockCard.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.colorText_Day))
            }

        }
    }

    private fun setupControl() {
        binding.apply {
            imgTitle.startAnimation(AnimationUtils.loadAnimation(this@MainActivity,R.anim.slide_in_right_200))
            cardInput.startAnimation(AnimationUtils.loadAnimation(this@MainActivity,R.anim.slide_in_left_300))
            btnNext.setOnClickListener {
                if(edtPin.visibility == View.GONE){
                    if(codeCard==edtNumberCard.text.toString().trim()){
                        if(sharedPreferenceHelper.getNumberValid(codeCard)==0){
                            showToastError("Thẻ bị khóa do nhập sai mã pin quá 3 lần")
                            return@setOnClickListener
                        }
                        edtPin.toVisible()
                        tvTitle.setText("Nhập mã pin")
                    } else{
                        edtPin.toGone()
                        tvTitle.setText("Nhập số thẻ")
                        showToastError("Số thẻ không hợp lệ")
                    }
                } else{
                    if(sharedPreferenceHelper.getNumberValid(codeCard)==0){
                        showToastError("Thẻ bị khóa do nhập sai mã pin quá 3 lần")
                        return@setOnClickListener
                    }
                    if(codePin==edtPin.text.toString().trim()){
                        sharedPreferenceHelper.setNumberValid(3,codeCard)
                        showToastSuccess("Truy cập thành công")
                        handler.postDelayed({
                            lyFeature.toVisible()
                            lyFeature.startAnimation(AnimationUtils.loadAnimation(this@MainActivity,R.anim.top_down_300))
                            lyHeader.toGone()
                        },1000)
                    } else{
                        sharedPreferenceHelper.setNumberValid(sharedPreferenceHelper.getNumberValid(codeCard)-1,codeCard)
                        if(sharedPreferenceHelper.getNumberValid(codeCard)==0){
                            showToastError("Bạn nhập sai quá nhiều, thẻ bị khóa")
                        }else{
                            showToastError("Mã pin sai vui lòng nhập lại")
                        }
                    }
                }
            }
        }
    }

    private fun setupInit() {
        binding.containerMain.toGone()
        binding.containerIntroduce.toVisible()
        binding.tvNameTeam.translationY = -500f
        binding.tvSlogan.translationX = -1000f
        binding.lottieAnimation.alpha = 0f
        binding.tvCopyRight.alpha = 0f
        binding.tvDescription.translationX = 1000f
    }

    private fun setupAnimation() {
        binding.tvNameTeam.animate().apply {
            duration = 1500
            translationY(0f)
            startDelay = 500L
            interpolator = AccelerateDecelerateInterpolator()
        }.start()

        binding.tvSlogan.animate().apply {
            duration = 1500
            translationX(0f)
            startDelay = 500L
            interpolator = AccelerateDecelerateInterpolator()
        }.start()

        binding.tvDescription.animate().apply {
            duration = 1500
            translationX(0f)
            startDelay = 500L
            interpolator = AccelerateDecelerateInterpolator()
        }.start()

        binding.tvCopyRight.animate().apply {
            duration = 1500
            alpha(1f)
            startDelay = 500L
        }.start()


        binding.lottieAnimation.animate().apply {
            duration = 200
            startDelay = 100
            alpha(1f)
        }.start()
    }
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toVisible(){
    this.visibility = View.VISIBLE
}

fun Context.showToastNoInternet() = StyleableToast.makeText(this,getString(R.string.no_internet),R.style.toast_internet).show()
fun Context.showToastSuccess(msg:String) = StyleableToast.makeText(this,msg,R.style.toast_success).show()
fun Context.showToastError(msg:String) = StyleableToast.makeText(this,msg,R.style.toast_error).show()