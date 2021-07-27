package com.sun.springbootdemo.lua;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * <p>  </p>
 *
 * @author Sundz
 * @date 2021/7/17 18:03
 */
public class Lua2Java {

    public static void main(String[] args) throws Exception {
        String luaStr = "print('Hello World!')";
        String script = "E:\\idea_workplace\\personal\\springbootdemo\\src\\main\\resources\\lua\\FirstLua.lua";
        Globals globals = JsePlatform.standardGlobals();
        /*LuaValue chunk = globals.loadfile(script);
        chunk.call(LuaValue.valueOf(script));*/
        LuaValue chunk = globals.load(new InputStreamReader(new FileInputStream(script)), "chunkname").call();
        /*// 调用Lua函数
        LuaValue fun = globals.get(LuaValue.valueOf("hi"));
        fun.invoke(LuaValue.valueOf("Hello World!!"));*/
       /* // 调用Lua中的数组
        LuaValue hTable = globals.get(LuaValue.valueOf("a"));*/
        // 调用java中的静态方法
        LuaValue fun2 = globals.get(LuaValue.valueOf("javaObj"));
        fun2.invoke();
    }
}
