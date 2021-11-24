package com.example.mycal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

public class WebviewFragment3 extends Fragment {
    public WebviewFragment3(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.webview3_fragment, container, false);
        WebView webView = (WebView)view.findViewById(R.id.webView_menu3);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://halosehat.com/makanan/makanan-sehat/20-makanan-sehat-untuk-diet-menurunkan-berat-badan");
        return view;
    }

    public interface OnFragmentInteractionListener {
    }
}