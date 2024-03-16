package com.app.loyality.features.barScanner;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.app.loyality.R;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.Result;

public class BarcodeReaderActivity extends AppCompatActivity {

    CodeScanner mCodeScanner;
    private CodeScannerView scannerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_reader);

        scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);

        showQrScanner();
    }

    private void showQrScanner()
    {
        RelativeLayout rl_bar_scanner = findViewById(R.id.rl_bar_scanner);
        TextView closeTv = findViewById(R.id.closeTv);

        mCodeScanner.setDecodeCallback(result -> runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                prodCodeTil.getEditText().setText(result.getText());
//                productCode = result.getText();
                Toast.makeText(BarcodeReaderActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content),
                        "Coming Soon", Snackbar.LENGTH_LONG);
                snackBar.show();
                mCodeScanner.releaseResources();
                rl_bar_scanner.setVisibility(View.GONE);
            }
        }));

        mCodeScanner.startPreview();

        closeTv.setOnClickListener((View.OnClickListener) v ->{
            finish();
            mCodeScanner.releaseResources();
        });
    }
}
