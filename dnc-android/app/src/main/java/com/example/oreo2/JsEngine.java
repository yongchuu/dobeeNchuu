package com.example.oreo2;

import android.util.Log;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

public class JsEngine {
    private ContextFactory f = new ContextFactory();
    private Context rhino = f.enterContext();

    private Scriptable _scope = rhino.initStandardObjects();
    private StringBuilder jsCode = new StringBuilder();

    public JsEngine()
    {
        rhino.setOptimizationLevel(-1);
    }

    public void addFunction(String code){
        jsCode.append(code);
        rhino.evaluateString(_scope, jsCode.toString(), "ScriptAPI", 1, null);
    }

    public void clearCode(String code){
        jsCode = new StringBuilder();
    }

    public String getCode(){
        return jsCode.toString();
    }

    public void callFunction(String funcName, Object[] p)
    {
        try {
            Object obj = (Function) _scope.get(funcName, _scope);
            if (obj instanceof Function) {
                Function func = (Function) obj;
                Object jsResult = func.call(rhino, _scope, _scope, p);
                Log.d("Tag", jsResult.toString());
            }
            else{
                Log.d("Tag", funcName +"is not function");
            }
        }
        catch (Exception e) {
            Log.e("exc",e.toString());
        }
    }
}
