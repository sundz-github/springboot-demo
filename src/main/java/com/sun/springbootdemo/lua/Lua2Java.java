package com.sun.springbootdemo.lua;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2021/7/17 18:03
 */
public class Lua2Java {

    public static void main(String[] args) {
        String luaStr = "print('Hello World!')";
        Globals globals = JsePlatform.standardGlobals();
        LuaValue chunk = globals.load(luaStr);
        chunk.call();
    }
}
