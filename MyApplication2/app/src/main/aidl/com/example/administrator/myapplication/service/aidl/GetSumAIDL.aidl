// GetSumAIDL.aidl
package com.example.administrator.myapplication.service.aidl;

// Declare any non-default types here with import statements
import com.example.administrator.myapplication.service.aidl.OnGetSumListener;
interface GetSumAIDL {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void OnGetSumListener(com.example.administrator.myapplication.service.aidl.OnGetSumListener listener);
    void playMusic();
}
