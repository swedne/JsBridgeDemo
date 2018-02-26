package cn.edu.zafu.myjsbridge;

import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author lizhangqu
 * @since 2016-02-27 22:11
 */
public class BridgeImpl implements IBridge {
    /**
     * @param webView
     * @param param    js传来的方法调用所需的参数，是一个json对象
     * @param callback 方法的执行结果通过callback传递回去
     */
    public static void showToast(WebView webView, JSONObject param, final Callback callback) {
        String message = param.optString("msg");
        Toast.makeText(webView.getContext(), message, Toast.LENGTH_SHORT).show();
        Log.e("flag", message);
        if (null != callback) {
            try {
                JSONObject object = new JSONObject();
                object.put("key", "value");
                object.put("key1", "value1");
                callback.apply(getJSONObject(0, "ok", object));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void testThread(WebView webView, JSONObject param, final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    JSONObject object = new JSONObject();
                    object.put("key", "value");
                    callback.apply(getJSONObject(0, "ok", object));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static JSONObject getJSONObject(int code, String msg, JSONObject result) {
        JSONObject object = new JSONObject();
        try {
            object.put("code", code);
            object.put("msg", msg);
            object.putOpt("result", result);
            return object;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
