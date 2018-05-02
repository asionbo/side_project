package club.biggerm.asionbo.wanandroid.model

import com.google.gson.annotations.SerializedName

/**
 * Created by asionbo on 2018/4/12.
 */
data class BaseResult<T>(@SerializedName("data") var data:T
                         ,@SerializedName("errorCode") var errorCode :Int
                         ,@SerializedName("errorMsg") var errorMsg:String)