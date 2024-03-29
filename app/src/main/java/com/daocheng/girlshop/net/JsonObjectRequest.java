package com.daocheng.girlshop.net;

import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetworkResponse;
import com.duowan.mobile.netroid.ParseError;
import com.duowan.mobile.netroid.Response;
import com.duowan.mobile.netroid.request.JsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * A request for retrieving a {@link JSONObject} response body at a given URL, allowing for an
 * optional {@link JSONObject} to be passed in as part of the request body.
 */
public class  JsonObjectRequest extends JsonRequest<JSONObject> {
    /**
     * Creates a new request.
     * @param method the HTTP method to use
     * @param url URL to fetch the JSON from
     * @param jsonRequest A {@link JSONObject} to post with the request. Null is allowed and
     *   indicates no parameters will be posted along with request.
     * @param listener Listener to receive the JSON response or error message
     */
    public JsonObjectRequest(int method, String url, JSONObject jsonRequest, Listener<JSONObject> listener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener);
    }

    /**
     * Constructor which defaults to <code>GET</code> if <code>jsonRequest</code> is
     * <code>null</code>, <code>POST</code> otherwise.
     *
     * @see #JsonObjectRequest(int, String, JSONObject, Listener)
     */
    public JsonObjectRequest(String url, JSONObject jsonRequest, Listener<JSONObject> listener) {
        this(jsonRequest == null ? Method.GET : Method.POST, url, jsonRequest, listener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, response.charset);
            return Response.success(new JSONObject(jsonString.substring(1,jsonString.length()-1)), response);
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}
