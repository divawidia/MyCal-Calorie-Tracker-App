package com.example.mycal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

public class WebviewFragment2 extends Fragment {
    public WebviewFragment2(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.webview2_fragment, container, false);
        WebView webView = (WebView)view.findViewById(R.id.webView_menu2);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://halosehat.com/diet-fitness/tips-diet/makanan-yang-dilarang-saat-diet");
        return view;
    }

    public interface OnFragmentInteractionListener {
    }
}