package com.suwiki.remote

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResultCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): CallAdapter<*, *>? {
        val rawType = getRawType(returnType)

        if (returnType !is ParameterizedType) {
            val name = parseTypeName(returnType)
            throw IllegalArgumentException("Return 타입은 $name<Foo> 또는 $name<out Foo>로 정의되어야 합니다.")
        }

        return when (rawType) {
            Call::class.java -> apiResultAdapter(returnType)
            else -> null
        }
    }

    private fun apiResultAdapter(
        returnType: ParameterizedType,
    ): CallAdapter<Type, out Call<out Any>>? {
        val wrapperType = getParameterUpperBound(0, returnType)
        return when (getRawType(wrapperType)) {
            ApiResult::class.java -> {
                val bodyType = extractReturnType(wrapperType, returnType)
                ApiResultCallAdapter(bodyType)
            }

            else -> null
        }
    }

    private fun extractReturnType(
        wrapperType: Type,
        returnType: ParameterizedType,
    ): Type {
        if (wrapperType !is ParameterizedType) {
            val name = parseTypeName(returnType)
            throw IllegalArgumentException(
                "Return 타입은 $name<ResponseBody>로 정의되어야 합니다.",
            )
        }
        return getParameterUpperBound(0, wrapperType)
    }
}

private fun parseTypeName(type: Type) = type.toString().split('.').last()
