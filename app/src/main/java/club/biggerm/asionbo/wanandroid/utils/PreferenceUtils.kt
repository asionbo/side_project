package club.biggerm.asionbo.wanandroid.utils

import android.content.Context


/**
 * Created by asionbo on 2018/7/24.
 */
object PreferenceUtils{
    /**
     * 保存在手机里面的文件名
     */
    private val FILE_NAME = "wan_share_prefs"


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * @param context
     * @param key
     * @param object
     */
    fun setParam(context: Context, key: String, any: Any) {

        val type = any.javaClass.simpleName
        val sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        when(type){
            "String" -> editor.putString(key,any as String)
            "Integer" -> editor.putInt(key, any as Int)
            "Boolean" -> editor.putBoolean(key, any as Boolean)
            "Float" -> editor.putFloat(key, any as Float)
            "Long" -> editor.putLong(key, any as Long)
            "HashSet<String>" -> editor.putStringSet(key,any as HashSet<String>)
        }
        editor.commit()
    }


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    fun getParam(context: Context, key: String, any: Any): Any? {
        val type = any.javaClass.simpleName
        val sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

        when(type){
            "String" -> return sp.getString(key,any as String)
            "Integer" -> return sp.getInt(key, any as Int)
            "Boolean" -> return sp.getBoolean(key, any as Boolean)
            "Float" -> return sp.getFloat(key, any as Float)
            "Long" ->return sp.getLong(key, any as Long)
            "HashSet<String>" -> return sp.getStringSet(key,any as HashSet<String>)
        }

        return null
    }

    /**
     * 清除所有数据
     * @param context
     */
    fun clearAll(context: Context) {
        val sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.clear().commit()
    }

    /**
     * 清除指定数据
     * @param context
     */
    fun clear(context: Context,key: String) {
        val sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.remove(key)
        editor.commit()
    }
}