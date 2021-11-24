package com.example.mycal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

public class WebviewFragment4 extends Fragment {
    public WebviewFragment4(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.webview4_fragment, container, false);
        WebView webView = (WebView)view.findViewById(R.id.webView_menu4);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://doktersehat.com/makanan-penambah-berat-badan/");
        return view;
    }

    public interface OnFragmentInteractionListener {
    }
}