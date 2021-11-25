package com.rolahsu.hashtagdemo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.text.style.ForegroundColorSpan

import android.text.SpannableString
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {
    lateinit var editText: EditText
    lateinit var output: TextView
    lateinit var button: Button
    lateinit var clearBtn: Button
    var hashtagList = HashSet<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.editText)
        output = findViewById(R.id.hashtagOutput)
        button = findViewById(R.id.button)
        clearBtn = findViewById(R.id.clearBtn)

        button.setOnClickListener {
            colorHashTag(editText.text.toString())
//            findHashTagUseRegex(editText.text.toString())
//            var outputString = ""
//            hashtagList.forEach {
//                outputString += it + "\n"
//            }
//            output.text = outputString
        }


        clearBtn.setOnClickListener {
            output.text = ""
            editText.text.clear()
            hashtagList.clear()
        }

    }

    fun findHashTagUseRegex(text: String) {
        val matcher = Pattern.compile("#([A-Za-z0-9_-]+)").matcher(text)
        while (matcher.find()) {
            hashtagList.add(text.subSequence(matcher.start(), matcher.end()).toString())
        }
    }

    fun colorHashTag(text: String) {
        val hashtagintitle = SpannableString(text)
        val matcher = Pattern.compile("#([A-Za-z0-9_-]+)").matcher(hashtagintitle)
        while (matcher.find()) {
            hashtagintitle.setSpan(
                ForegroundColorSpan(Color.BLUE),
                matcher.start(),
                matcher.end(),
                0
            )
        }
        output.text = hashtagintitle
    }

    fun findHashTag(text: String) {
        var newText = text
        while (newText.isNotEmpty()) {
            Log.d("xxx", "new text is = $newText")
            val hashtagStar = newText.lastIndexOf("#")
            val hashtagEnd = newText.lastIndexOf(" ")
            if (hashtagStar == -1) {
                Log.d("xxx", "沒有包含任何 hashtag")
                break
            } else {
                Log.d("xxx", "有 # 字號")
                if (hashtagStar < hashtagEnd){
                    Log.d("xxx", "且有空格\nhashtagStar = $hashtagStar\nhashtagEnd = $hashtagEnd")
                    val tag = newText.substring(hashtagStar, hashtagEnd)
                    Log.d("xxx", "tag = $tag")
                    hashtagList.add(tag)
                    if (hashtagStar == 0) {
                        Log.d("xxx", "無字串")
                        newText = ""
                    } else {
                        val newString = newText.subSequence(0, hashtagStar)
                        Log.d("xxx", "newString = $newString")
                        newText = newString.toString()
                    }
                } else {
                    Log.d("xxx", "沒有空格\nhashtagStar = $hashtagStar\nnewText.length = ${newText.length}")
                    val tag: String
                    val lineUpPosition = newText.lastIndexOf("\n")
                    Log.d("xxx", "lineUpPosition = $lineUpPosition, newText.length = ${newText.length}, hashtagStar = $hashtagStar")

                    if (lineUpPosition < newText.length - 1 && lineUpPosition > hashtagStar) {
                        Log.d("xxx", "沒有空格，有換行, lineUpPosition = $lineUpPosition")
                        tag = newText.substring(hashtagStar, lineUpPosition)
                    } else {
                        Log.d("xxx", "沒有空格，沒有換行")
                        tag = newText.substring(hashtagStar, newText.length)
                    }

                    hashtagList.add(tag)
                    Log.d("xxx", "tag = $tag")
                    val newString = newText.subSequence(0, hashtagStar)
                    Log.d("xxx", "newString = $newString")
                    newText = newString.toString()
                }
            }
        }
    }

}