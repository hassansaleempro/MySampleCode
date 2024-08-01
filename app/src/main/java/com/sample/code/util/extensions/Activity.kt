package com.casttypes.app.utils.extensions

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.WindowManager
import android.widget.Toast


fun Activity.openWebBrowser(url: String) {
    startActivity(
        Intent(
            Intent.ACTION_VIEW, Uri.parse(url)
        )
    )
}

fun Activity.openActivityWithExist(clazz: Class<out Activity>) {
    startActivity(Intent(this, clazz))
    this.finish()
}


fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.turnScreenOnAndKeyguardOff() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
        setShowWhenLocked(true)
        setTurnScreenOn(true)
    } else {
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
        )
    }

    with(getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requestDismissKeyguard(this@turnScreenOnAndKeyguardOff, null)
        }
    }
}

fun Activity.turnScreenOffAndKeyguardOn() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
        setShowWhenLocked(false)
        setTurnScreenOn(false)
    } else {
        window.clearFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
        )
    }
}
