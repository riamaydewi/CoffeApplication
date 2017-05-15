package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava; 
 */



/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void increment(View view) {
        if (quantity >= 8) {
            Toast.makeText(getApplicationContext(), "Stok hanya 8", Toast.LENGTH_SHORT).show();
        } else {
            quantity = quantity + 1;
            displayQuantity(quantity);
        }
    }

    public void decrement(View view) {
        if (quantity != 0) {
            quantity = quantity - 1;
            displayQuantity(quantity);
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Stok tidak bisa negative").setTitle("Warning!!").create().show();
        }
    }

    /**
     * Menampilkan hasil ke text box summary
     * @param summary
     */
    private void showSummary (String summary) {
        TextView tv_summary = (TextView) findViewById(R.id.summary_text_view);
        tv_summary.setText(summary);
    }

    /**
     * Mengecek apakah check box whipped cream dipilih atau tidak
     * @return
     */
    private boolean getWhippedCreamStatus() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.whipped_cream);
        Log.v("MainAcivity", "Add whipped cream? + " + checkBox.isChecked() + "");
        return checkBox.isChecked();
    }

    /**
     * Mengecek apakah check box chocolate dipilih atau tidak
     */
    private boolean getChocolateStatus() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.chocolate);
        Log.v("MainActivity", "Add Chocolate?" + checkBox.isChecked() + "");
        return checkBox.isChecked();
    }

    /**
     * Mengambil nama yang diisi pada edit text
     * @return
     */
    private String getNama() {
        EditText etNama = (EditText) findViewById(R.id.nama);
        return  etNama.getText().toString();
    }

    /**
     * Menampilkan hasil summary dari pemesanan
     * @param number
     * @return
     */
    private String summary(int number) {
        String summary = "Nama : " + getNama();
        summary += "\nAdd Whipped Cream? " + getWhippedCreamStatus();
        summary += "\nAdd Chocolate? " + getChocolateStatus();
        summary += "\nQuantity : " + quantity + "\n";
        summary += NumberFormat.getCurrencyInstance().format(number) + "\n";
        summary += "Thank You!\n";

        return summary;
    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        /*int price = quantity * 5;
        return price;*/
        int basePrice = 5;

        if (addWhippedCream) {
            basePrice += 1;
        }

        if (addChocolate) {
            basePrice += 2;
        }

        return basePrice * quantity;
    }



    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_value);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_value);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_value);
        orderSummaryTextView.setText(message);
    }

    public void submitOrder(View view) {
        displayPrice(calculatePrice(getWhippedCreamStatus(), getChocolateStatus()));
        showSummary(summary(calculatePrice(getWhippedCreamStatus(), getChocolateStatus())));
    }

    public void email(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for " + getNama() + " ");
        intent.putExtra(Intent.EXTRA_TEXT, summary(calculatePrice(getWhippedCreamStatus(), getChocolateStatus())));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}