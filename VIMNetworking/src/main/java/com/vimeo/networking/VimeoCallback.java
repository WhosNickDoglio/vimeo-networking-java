/*
 * Copyright (c) 2015 Vimeo (https://vimeo.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.vimeo.networking;

import com.vimeo.networking.model.error.VimeoError;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zetterstromk on 5/27/15.
 * <p/>
 * This class allows intercepting and analyzing of the responses for errors and invalid tokens
 */
public abstract class VimeoCallback<T> implements Callback<T> {

    public abstract void success(T t, VimeoResponse response);

    public abstract void failure(VimeoError error);

    @Override
    public void success(T t, Response response) {
        success(t, new VimeoResponse(response));
    }

    @Override
    public void failure(RetrofitError error) {
        VimeoError vimeoError = null;
        try {
            vimeoError = (VimeoError) error.getBodyAs(VimeoError.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (vimeoError == null) {
            vimeoError = new VimeoError();
        }
        vimeoError.setRetrofitError(error);
        failure(vimeoError);
    }
}
