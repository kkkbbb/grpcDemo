package com.example.grpcdemo

import android.accounts.AccountManager
import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }

        val button = Button(this).apply {
            text = "ListTask"
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            setOnClickListener {
                ListTaskTest(this@MainActivity).greet();
            }
        }

        val buttonGetacc = Button(this).apply {
            text = "GetAccount"
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            setOnClickListener {
                val manager = AccountManager.get(context)
                val accounts = manager.getAccountsByType("com.google")
                Toast.makeText(context, accounts[0].name, Toast.LENGTH_SHORT).show()
            }
        }

        val buttonsyspk = Button(this).apply {
            text = "check system app"
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

            setOnClickListener {
                val pm = context.packageManager
                val ai = pm.getApplicationInfo("com.google.android.googlequicksearchbox", 0)
                val isSystemApp = ai.flags and ApplicationInfo.FLAG_SYSTEM != 0
                if(isSystemApp){
                    Toast.makeText(context,"是系统应用",Toast.LENGTH_SHORT).show()
                }
            }

        }

        val buttongetinstaller = Button(this).apply {
            text = "getinstaller"
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

            setOnClickListener {
                val pm = context.packageManager
                val res = pm.getInstallerPackageName("com.whatsapp.w4b");
                Toast.makeText(context,res,Toast.LENGTH_SHORT).show()
            }

        }

        // 将Button添加到LinearLayout中
        layout.addView(button)
        layout.addView(buttonGetacc)
        layout.addView(buttonsyspk)
        layout.addView(buttongetinstaller)

        // 将LinearLayout设置为activity的根布局
        setContentView(layout)
    }
}