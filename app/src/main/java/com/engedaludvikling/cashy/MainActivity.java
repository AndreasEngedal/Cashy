package com.engedaludvikling.cashy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.engedaludvikling.cashy.utils.CoinMarketRestClientUsage;

import org.json.JSONException;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    TextView tvCaption;
    TextView tvUSD;
    TextView tvAndersValueTotal;
    TextView tvAndreasValueTotal;
    TextView tvMartinValueTotal;
    TextView tvAndersValuePlus;
    TextView tvAndreasValuePlus;
    TextView tvMartinValuePlus;
    TextView tvTotalValue;

    Button btnDKK;
    Button btnDollars;

    private boolean isPriceInDKK = false;
    private double mCurrentPrice;
    private DecimalFormat mFormatterShort;
    private DecimalFormat mFormatterLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFormatterShort = new DecimalFormat("0.00");
        mFormatterLong = new DecimalFormat("0.0000");

        tvCaption = findViewById(R.id.tv_caption);
        tvUSD = findViewById(R.id.tv_usd);;
        tvAndersValueTotal = findViewById(R.id.tv_anders_value_total);;
        tvAndreasValueTotal = findViewById(R.id.tv_andreas_value_total);;
        tvMartinValueTotal = findViewById(R.id.tv_martin_value_total);;
        tvAndersValuePlus = findViewById(R.id.tv_anders_value_plus);;
        tvAndreasValuePlus = findViewById(R.id.tv_andreas_value_plus);;
        tvMartinValuePlus = findViewById(R.id.tv_martin_value_plus);;
        tvTotalValue = findViewById(R.id.tv_total_value);
        btnDKK = findViewById(R.id.btn_dkk);
        btnDollars = findViewById(R.id.btn_dollars);

        startHttpRequest();
    }

    private void startHttpRequest() {
        CoinMarketRestClientUsage clientUsage = new CoinMarketRestClientUsage(this);
        try {
            clientUsage.getPriceInUSD();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateValues(double usdPrice) {
        if (isPriceInDKK) {
            tvUSD.setText(getString(R.string.dkk, String.valueOf(mFormatterLong.format(usdPrice * Constants.USDToDKK))));
            tvAndersValueTotal.setText(getString(R.string.dkk, String.valueOf(mFormatterShort.format((Constants.FirstCoinDeposit * usdPrice) * Constants.USDToDKK))));
            tvAndreasValueTotal.setText(getString(R.string.dkk, String.valueOf(mFormatterShort.format(((Constants.FirstCoinDeposit + Constants.SecondCoinDeposit) * usdPrice) * Constants.USDToDKK))));
            tvMartinValueTotal.setText(getString(R.string.dkk, String.valueOf(mFormatterShort.format(((Constants.FirstCoinDeposit + Constants.SecondCoinDeposit) * usdPrice) * Constants.USDToDKK))));

            double andersDifference = Constants.FirstCoinDeposit * usdPrice - Constants.FirstCoinDeposit * Constants.FirstPrice;
            double othersDifference = (Constants.FirstCoinDeposit + Constants.SecondCoinDeposit) * usdPrice - Constants.FirstCoinDeposit * Constants.FirstPrice - Constants.SecondCoinDeposit * Constants.SecondPrice;

            tvAndersValuePlus.setText(getString(R.string.dkk, String.valueOf(mFormatterShort.format(andersDifference * Constants.USDToDKK))));
            tvAndreasValuePlus.setText(getString(R.string.dkk, String.valueOf(mFormatterShort.format(othersDifference * Constants.USDToDKK))));
            tvMartinValuePlus.setText(getString(R.string.dkk, String.valueOf(mFormatterShort.format(othersDifference * Constants.USDToDKK))));
            tvTotalValue.setText(getString(R.string.dkk, String.valueOf(mFormatterShort.format(((Constants.FirstCoinDeposit + Constants.FirstCoinDeposit + Constants.SecondCoinDeposit + Constants.FirstCoinDeposit + Constants.SecondCoinDeposit) * usdPrice) * Constants.USDToDKK))));
        } else {
            mCurrentPrice = usdPrice;
            tvUSD.setText(getString(R.string.dollars, String.valueOf(mFormatterLong.format(usdPrice))));
            tvAndersValueTotal.setText(getString(R.string.dollars, String.valueOf(mFormatterShort.format(Constants.FirstCoinDeposit * usdPrice))));
            tvAndreasValueTotal.setText(getString(R.string.dollars, String.valueOf(mFormatterShort.format((Constants.FirstCoinDeposit + Constants.SecondCoinDeposit) * usdPrice))));
            tvMartinValueTotal.setText(getString(R.string.dollars, String.valueOf(mFormatterShort.format((Constants.FirstCoinDeposit + Constants.SecondCoinDeposit) * usdPrice))));

            double andersDifference = Constants.FirstCoinDeposit * usdPrice - Constants.FirstCoinDeposit * Constants.FirstPrice;
            double othersDifference = (Constants.FirstCoinDeposit + Constants.SecondCoinDeposit) * usdPrice - Constants.FirstCoinDeposit * Constants.FirstPrice - Constants.SecondCoinDeposit * Constants.SecondPrice;

            tvAndersValuePlus.setText(getString(R.string.dollars, String.valueOf(mFormatterShort.format(andersDifference))));
            tvAndreasValuePlus.setText(getString(R.string.dollars, String.valueOf(mFormatterShort.format(othersDifference))));
            tvMartinValuePlus.setText(getString(R.string.dollars, String.valueOf(mFormatterShort.format(othersDifference))));
            tvTotalValue.setText(getString(R.string.dollars, String.valueOf(mFormatterShort.format((Constants.FirstCoinDeposit + Constants.FirstCoinDeposit + Constants.SecondCoinDeposit + Constants.FirstCoinDeposit + Constants.SecondCoinDeposit) * usdPrice))));
        }
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_dkk:
                btnDKK.setEnabled(false);
                btnDollars.setEnabled(true);
                isPriceInDKK = true;
                updateValues(mCurrentPrice);
                break;
            case R.id.btn_dollars:
                btnDKK.setEnabled(true);
                btnDollars.setEnabled(false);
                isPriceInDKK = false;
                updateValues(mCurrentPrice);
                break;
        }
    }
}

class Constants {

    public static double FirstCoinDeposit = 13.7866;
    public static double SecondCoinDeposit = 26.0976;

    public static double FirstDollarDeposit = 36;
    public static double SecondDollarDeposit = 79.75;

    public static double FirstPrice = 2.36;
    public static double SecondPrice = 2.70;

    public static double USDToDKK = 6.27;

    public static double getAndersCoinCount() {
        return FirstCoinDeposit;
    }

    public static double getAndreasCoinCount() {
        return FirstCoinDeposit + SecondCoinDeposit;
    }

    public static double getMartinCoinCount() {
        return FirstCoinDeposit + SecondCoinDeposit;
    }

    public static double getAndersDeposit() {
        return FirstDollarDeposit;
    }

    public static double getAndreasDeposit() {
        return FirstDollarDeposit + SecondDollarDeposit;
    }

    public static double getMartinDeposit() {
        return FirstDollarDeposit + SecondDollarDeposit;
    }
}
