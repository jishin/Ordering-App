/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.jishinpjijy.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    boolean hasWhippedCream, hasChocolate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.checkBoxwhip);
        hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox =  (CheckBox) findViewById(R.id.checkBoxchoco);
        hasChocolate = chocolateCheckBox.isChecked();
        EditText nameBox = (EditText) findViewById(R.id.editText);
        Editable NamePerson = nameBox.getText();
        String summary = createOrderSummary(quantity, hasWhippedCream, hasChocolate, NamePerson);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " +  NamePerson);
        intent.putExtra(Intent.EXTRA_TEXT,summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private String createOrderSummary(int quantity, boolean hasWhippedCream1, boolean hasChocolate1, Editable NamePerson1){
        int cost = calculatePrice();
        String summary = "Name : " + NamePerson1 + "\nWhipped Cream added : " + hasWhippedCream1 + "\nChoclolate added : " + hasChocolate1 + "\nNo. of coffes = " + quantity  + "\n cost = " + cost + "\nThank You";
        Toast.makeText(this,"Thank You",Toast.LENGTH_SHORT).show();
        return summary;

    }

    /**
     * Calculates the price of the order.
     *
     */
    private int calculatePrice() {
        int price = 5;
        if(hasWhippedCream){
            price = price + 1;
        }
        if(hasChocolate){
            price = price + 2;
        }

        price = quantity * price;
        return price;
    }

    public void increment(View view) {
        if(quantity <= 99) {
            quantity = quantity + 1;
        } else {
            Toast.makeText(this,"Maximum order limit is 100",Toast.LENGTH_SHORT).show();
            return;
        }
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if(quantity >= 2) {
            quantity = quantity - 1;
        } else {
            Toast.makeText(this,"Atleast one cup is requiered" ,Toast.LENGTH_SHORT).show();
            return;
        }
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}