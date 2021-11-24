package com.example.mycal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

public class WebviewFragment1 extends Fragment {
    public WebviewFragment1(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.webview1_fragment, container, false);
        WebView webView = (WebView)view.findViewById(R.id.webView_menu1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://hellosehat.com/nutrisi/berat-badan-turun/makanan-penurun-berat-badan-2/");
        return view;
    }

    public interface OnFragmentInteractionListener {
    }
}