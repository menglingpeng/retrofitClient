package com.menglingpeng.httputil.utils;

import android.content.Context;

import com.google.gson.JsonParseException;
import com.menglingpeng.httputil.R;

import org.json.JSONException;

import java.text.ParseException;

import retrofit2.HttpException;

/**
 * Created by mengdroid on 2018/3/26.
 */

public class ExceptionUtil {

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponseThrowable geException(Context context, Throwable e){
        ResponseThrowable re = null;
        if(e instanceof HttpException) {
            re = new ResponseThrowable(e, ERROR.HTTP_ERROR);
            HttpException httpException = (HttpException) e;
            switch (httpException.code()){
                case UNAUTHORIZED:
                    break;
                case FORBIDDEN:
                    break;
                case NOT_FOUND:
                    break;
                case REQUEST_TIMEOUT:
                    break;
                case INTERNAL_SERVER_ERROR:
                    break;
                case BAD_GATEWAY:
                    break;
                case SERVICE_UNAVAILABLE:
                    break;
                case GATEWAY_TIMEOUT:
                    break;
                default:
                    re.message = context.getString(R.string.exception_http_error);
                    break;
            }
        }else if(e instanceof ServerException){
            ServerException resultException = (ServerException) e;
            re = new ResponseThrowable(resultException, resultException.code);
            re.message = resultException.message;
        }else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            re = new ResponseThrowable(e, ERROR.PARSE_ERROR);
            re.message = context.getString(R.string.exception_parse_error);
        }else {
            re = new ResponseThrowable(e, ERROR.UNKNOWN);
            re.message = context.getString(R.string.exception_unknown_error);
        }
        return re;
    }

    /**
     * 约定异常类型
     */
    public class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORK_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;
    }

    public static class ResponseThrowable extends Exception {
        public int code;
        public String message;

        public ResponseThrowable(Throwable throwable, int code) {
            super(throwable);
            this.code = code;
        }
    }

    public class ServerException extends RuntimeException {
        public int code;
        public String message;
    }
}
