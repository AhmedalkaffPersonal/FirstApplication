package com.pioneersacademy.alkaff.firstapplication.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.pioneersacademy.alkaff.firstapplication.databinding.ActivityCalculatorBinding
import java.lang.Exception


class CalculatorActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCalculatorBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    var op="*"
    var oldNumber=""
    var isNewOp=true

    fun buNumberEvent(view: View){
        if(isNewOp){
            binding.etShowNumber.setText("")
        }
        isNewOp=false

        val buSelect= view as Button
        var buClickValue:String= binding.etShowNumber.text.toString()
        when(buSelect.id){
            binding.bu0.id->{
                buClickValue+="0"
            }
            binding.bu1.id->{
                buClickValue+="1"
            }
            binding.bu2.id->{
                buClickValue+="2"
            }
            binding.bu3.id->{
                buClickValue+="3"
            }
            binding.bu4.id->{
                buClickValue+="4"
            }
            binding.bu5.id->{
                buClickValue+="5"
            }
            binding.bu6.id->{
                buClickValue+="6"
            }
            binding.bu7.id->{
                buClickValue+="7"
            }
            binding.bu8.id->{
                buClickValue+="8"
            }
            binding.bu9.id->{
                buClickValue+="9"
            }
            binding.buDot.id->{
                //TODO: prevent adding morve than 1 dot
                buClickValue+="."
            }
            binding.buPlusMins.id->{
                buClickValue= "-"+ buClickValue
            }
        }
        binding.etShowNumber.setText(buClickValue)
    }



    fun buOpEevent(view:View){

        val buSelect= view as Button
        when(buSelect.id) {
            binding.buMul.id -> {
                op="*"
            }
            binding.buDiv.id -> {

                op="/"
            }
            binding.buSub.id -> {


                op="-"
            }
            binding.buSum.id -> {

                op="+"
            }

        }
        oldNumber= binding.etShowNumber.text.toString()
        isNewOp=true
    }

    fun buEqualEvent(view:View){
        val newNumber= binding.etShowNumber.text.toString()
        var finalNumber:Double?=null
        when(op){

            "*"->{
                finalNumber=  oldNumber.toDouble() *newNumber.toDouble()
            }
            "/"->{
                finalNumber=  oldNumber.toDouble() /newNumber.toDouble()
            }
            "+"->{
                finalNumber=  oldNumber.toDouble()+newNumber.toDouble()
            }
            "-"->{
                finalNumber=  oldNumber.toDouble() *newNumber.toDouble()
            }
        }
        binding.etShowNumber.setText(finalNumber.toString())
        isNewOp=true
    }

    fun buPercent(view:View){
        try {
            val number:Double= binding.etShowNumber.text.toString().toDouble()/100

            binding.etShowNumber.setText(number.toString())
            isNewOp=true
        }catch (e:Exception){

        }


    }

    fun buClean(view:View){
        binding.etShowNumber.setText("0")
        isNewOp=true
    }
}