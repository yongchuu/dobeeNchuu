package com.example.oreo2;

import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import java.io.*;

public class JsEngine {
    private ContextFactory f = new ContextFactory();
    private Context rhino = f.enterContext();

    private Scriptable _scope = rhino.initStandardObjects();
    private StringBuilder jsCode = new StringBuilder();


    public JsEngine(String EnvJs) {
        rhino.setOptimizationLevel(-1);
        try {
            rhino.evaluateString(_scope, EnvJs, "ScriptAPI", 1, null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            addEnvJs();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

//    private void addEnvJs() throws IOException {
//
//        AssetManager am = getResources().getAssets() ;
//        InputStream is = null ;
//
//        try {
//            is = am.open("FILE") ;
//
//            // TODO : use is(InputStream).
//
//        } catch (Exception e) {
//            e.printStackTrace() ;
//        }
//
//        if (is != null) {
//            try {
//                is.close() ;
//            } catch (Exception e) {
//                e.printStackTrace() ;
//            }
//        }
//        AssetManager assetManager = getAssets();
//        InputStream ims = assetManager.open("helloworld.txt");
//        String s = Environment.DIRECTORY_DOCUMENTS;
//        rhino.evaluateReader(_scope, new java.io.FileReader("app/assets/env.rhino.js"), "", 1, null);
//    }
    public void addFunction(String code){
        try {
            //rhino.
            jsCode.append(code);
            rhino.evaluateString(_scope, jsCode.toString(), "ScriptAPI", 1, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearCode(String code){
        jsCode = new StringBuilder();
    }

    public String getCode(){
        return jsCode.toString();
    }

    public Object GetValue(String jsVarName){
        return _scope.get(jsVarName, _scope);
    }


    public void callFunction(String funcName, Object[] p) {
        try {
            Object obj = _scope.get(funcName, _scope);

            if (obj instanceof Function) {
                Function func = (Function) obj;
                Object jsResult = func.call(rhino, _scope, _scope, p);
                Log.d("Tag", jsResult.toString());
            }
            else{
                Log.d("Tag", funcName +" is not function");
            }
        }
        catch (Exception e) {
            Log.e("exc",e.toString());
        }
    }
}
