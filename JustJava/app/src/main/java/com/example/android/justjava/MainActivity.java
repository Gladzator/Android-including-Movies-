package com.example.android.justjava; /**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 *
 */


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
    **Check checkbox is clicked
     */
    int n = 0;
    boolean whipedcream, chocolatecream;

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        /*
        ** checkbox
         */
        CheckBox whipcream = (CheckBox) findViewById(R.id.whipped);
        whipedcream = (whipcream.isChecked());

        CheckBox choccream = (CheckBox) findViewById(R.id.chocolate);
        chocolatecream = (choccream.isChecked());
        /*
        **  name
         */
        EditText name = (EditText) findViewById(R.id.name);
        String names = name.getText().toString();
        /*
        ** price
         */
        int price = calculatePrice();
        String pricemessage = createOrderSummary(price, whipedcream, chocolatecream, names);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, " Just Java order for " + names);
        intent.putExtra(Intent.EXTRA_TEXT, pricemessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /*
    **Calaculate price
     */
    private int calculatePrice() {
        int price = 0;
        if (whipedcream == true && chocolatecream == true)
            price = ((n * 5) + (1 * n) + (2 * n));
        else if (whipedcream == true)
            price = ((n * 5) + (1 * n));
        else if (chocolatecream == true)
            price = ((n * 5) + (2 * n));
        else
            price = n * 5;
        return price;
    }

    /*
    ** + -
     */
    public void submit1(View view) {
        n = n + 1;
        d1(n);
    }

    public void submit2(View view) {
        if (n > 0)
            n = n - 1;
        else
            n = 0;
        d1(n);
    }

    /*
    **string formation
     */
    private String createOrderSummary(int price, boolean addwhipcream, boolean chocolatecream, String name) {
        String pricemessage = getString(R.string.order_summary_name,name);
        pricemessage = pricemessage + "\n" + getString(R.string.whipcream) + addwhipcream;
        pricemessage = pricemessage + "\n" + getString(R.string.choc) + chocolatecream;
        pricemessage = pricemessage + "\n" + getString(R.string.quantity_text_view) + n;
        pricemessage = pricemessage + "\nTotal: $" + (price);
        pricemessage = pricemessage + "\n" + getString(R.string.thank_you);
        return pricemessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void d1(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.number);
        quantityTextView.setText("" + number);
    }
}
